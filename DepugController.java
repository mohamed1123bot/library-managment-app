package com.example.project10;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepugController {
    @GetMapping("/")
    public String index() {
        return "✅ API is running. Try /trending-books/most-loaned";
    }
}

