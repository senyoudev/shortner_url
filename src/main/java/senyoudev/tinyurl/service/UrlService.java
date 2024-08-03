package senyoudev.tinyurl.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import senyoudev.tinyurl.entity.Url;
import senyoudev.tinyurl.repository.UrlRepository;
import senyoudev.tinyurl.utils.Base62Encoder;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final RedisTemplate<String, String> redisTemplate;

    private final UrlRepository urlRepository;

    public String shortenUrl(String originalUrl) {
        // Encode the original Url
        long uniqueId = generateUniqueId(originalUrl);
        String shortUrl = Base62Encoder.encode(uniqueId);

        // Save the original and short Url to the database
        urlRepository.save(
                Url.builder()
                        .longUrl(originalUrl)
                        .shortUrl(shortUrl)
                        .createdAt(Date.from(new Date().toInstant()))
                        .hitCount(0L)
                        .build()
        );
        // Save the original and short Url to the cache
        redisTemplate.opsForValue().set(shortUrl, originalUrl);
        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        String originalUrl = redisTemplate.opsForValue().get(shortUrl);
        if(originalUrl == null) {
            originalUrl = urlRepository.findByShortUrl(shortUrl).getLongUrl();
            if( originalUrl == null) {
                return null;
            }
            redisTemplate.opsForValue().set(shortUrl, originalUrl);
        }
        return originalUrl;
    }

    private long generateUniqueId(String originalUrl) {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

}
