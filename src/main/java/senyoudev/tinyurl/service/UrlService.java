package senyoudev.tinyurl.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import senyoudev.tinyurl.repository.UrlRepository;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final RedisTemplate<String, String> redisTemplate;

    private final UrlRepository urlRepository;

    public String getOriginalUrl(String shortUrl) {
        String originalUrl = redisTemplate.opsForValue().get(shortUrl);
        if(originalUrl == null) {
            originalUrl = urlRepository.findByShortUrl(shortUrl).getLongUrl();
            redisTemplate.opsForValue().set(shortUrl, originalUrl);
        }
        return originalUrl;
    }

}
