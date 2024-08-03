package senyoudev.tinyurl.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senyoudev.tinyurl.dto.ShortUrlResponse;
import senyoudev.tinyurl.service.UrlService;

import java.io.IOException;

@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlResponse> shortenUrl(
            @RequestParam String originalUrl
    ) {
        String shortUrl = urlService.shortenUrl(originalUrl);
        return ResponseEntity.ok(new ShortUrlResponse(shortUrl));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectUrl(
            @PathVariable String shortUrl,
            HttpServletResponse response
    ) throws IOException {
        System.out.println("shortUrl = " + shortUrl);
        // This should come from the service layer
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        // Check the response
        if(originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        response.sendRedirect(originalUrl);
        return ResponseEntity.ok().build();
    }

}
