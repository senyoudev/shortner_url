package senyoudev.tinyurl.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senyoudev.tinyurl.dto.ShortUrlResponse;
import senyoudev.tinyurl.dto.UrlRequest;
import senyoudev.tinyurl.service.UrlService;

import java.io.IOException;

@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlResponse> shortenUrl(
            @RequestBody UrlRequest request
    ) {
        logger.debug("Received request to shorten URL: {}", request.originalUrl());
        String shortUrl = urlService.shortenUrl(request.originalUrl());
        logger.info("Shortened URL: {} -> {}", request.originalUrl(), shortUrl);
        return ResponseEntity.ok(new ShortUrlResponse(shortUrl));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectUrl(
            @PathVariable String shortUrl,
            HttpServletResponse response
    ) throws IOException {
        logger.debug("Received request to redirect to short URL: {}", shortUrl);
        // This should come from the service layer
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        // Check the response
        if(originalUrl == null) {
            logger.warn("URL mapping not found for short URL: {}", shortUrl);
            return ResponseEntity.notFound().build();
        }
        response.sendRedirect(originalUrl);
        logger.info("Redirected to original URL: {} -> {}", shortUrl, originalUrl);
        return ResponseEntity.ok().build();
    }

}
