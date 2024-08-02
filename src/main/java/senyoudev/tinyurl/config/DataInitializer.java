package senyoudev.tinyurl.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import senyoudev.tinyurl.entity.Url;
import senyoudev.tinyurl.repository.UrlRepository;

import java.util.Date;

@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(UrlRepository urlRepository) {
        return args -> {
            if(urlRepository.count() == 0) {
                urlRepository.save(
                        Url.builder()
                                .longUrl("https://www.google.com")
                                .shortUrl("google")
                                .createdAt(Date.from(new Date().toInstant()))
                                .hitCount(0L)
                                .build()
                );
                urlRepository.save(
                        Url.builder()
                                .longUrl("https://www.facebook.com")
                                .shortUrl("facebook")
                                .createdAt(Date.from(new Date().toInstant()))
                                .hitCount(0L)
                                .build()
                );
                urlRepository.save(
                        Url.builder()
                                .longUrl("https://www.twitter.com")
                                .shortUrl("twitter")
                                .createdAt(Date.from(new Date().toInstant()))
                                .hitCount(0L)
                                .build()
                );

            }
        };
    }
}
