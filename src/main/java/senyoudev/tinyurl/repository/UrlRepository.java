package senyoudev.tinyurl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senyoudev.tinyurl.entity.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByShortUrl(String shortUrl);
    Url findByLongUrl(String longUrl);
}
