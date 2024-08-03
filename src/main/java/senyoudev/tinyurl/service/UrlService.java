package senyoudev.tinyurl.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import senyoudev.tinyurl.entity.Url;
import senyoudev.tinyurl.repository.UrlRepository;
import senyoudev.tinyurl.utils.Base62Encoder;

import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;

@Service
@RequiredArgsConstructor
public class UrlService {

    private static final Logger logger = LoggerFactory.getLogger(UrlService.class);

    private final RedisTemplate<String, String> redisTemplate;

    private final UrlRepository urlRepository;

    public String shortenUrl(String originalUrl) {
        logger.debug("Shortening the Url: {}", originalUrl);
        // Encode the original Url
        long uniqueId = generateUniqueId(originalUrl);
        String shortUrl = Base62Encoder.encode(uniqueId);
        logger.debug("Generated short URL: {}", shortUrl);
        // Save the original and short Url to the database
        Url url = Url.builder()
                .longUrl(originalUrl)
                .shortUrl(shortUrl)
                .createdAt(Date.from(new Date().toInstant()))
                .hitCount(0L)
                .build();
        urlRepository.save(url);
        logger.info("Saved URL mapping to database: {}", url);
        // Save the original and short Url to the cache
        redisTemplate.opsForValue().set(shortUrl, originalUrl);
        logger.info("Saved URL mapping to cache: {} -> {}", shortUrl, originalUrl);
        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        logger.debug("Getting the original URL for: {}", shortUrl);
        String originalUrl = redisTemplate.opsForValue().get(shortUrl);
        if(originalUrl == null) {
            originalUrl = urlRepository.findByShortUrl(shortUrl).getLongUrl();
            if( originalUrl == null) {
                logger.warn("URL mapping not found for short URL: {}", shortUrl);
                return null;
            }
            logger.info("Retrieved URL mapping from database: {} -> {}", shortUrl, originalUrl);
            redisTemplate.opsForValue().set(shortUrl, originalUrl);
            logger.info("Saved URL mapping to cache: {} -> {}", shortUrl, originalUrl);
        }
        incrementHitCount(urlRepository.findByShortUrl(shortUrl));
        return originalUrl;
    }

    private void incrementHitCount(Url url) {
        if(url != null) {
            logger.debug("Incrementing hit count for URL: {}", url);
            url.setHitCount(url.getHitCount() + 1);
            urlRepository.save(url);
        }
    }

    private long generateUniqueId(String originalUrl) {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

}
