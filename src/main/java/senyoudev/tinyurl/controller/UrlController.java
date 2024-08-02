package senyoudev.tinyurl.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senyoudev.tinyurl.service.UrlService;

import java.io.IOException;

@RestController
@RequestMapping("/api/url")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectUrl(
            @PathVariable String shortUrl,
            HttpServletResponse response
    ) throws IOException {
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
