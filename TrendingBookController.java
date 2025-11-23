package com.example.project10;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trending-books")
@CrossOrigin(origins = "*")
public class TrendingBookController {

    private final TrendingBookService trendingBookService;

    public TrendingBookController(TrendingBookService trendingBookService) {
        this.trendingBookService = trendingBookService;
    }

    @GetMapping("/most-loaned")
    public ResponseEntity<List<TrendingBookDTO>> getMostLoanedBooks(
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(trendingBookService.getMostLoanedBooks(limit));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<TrendingBookDTO>> getTopRatedBooks(
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(trendingBookService.getTopRatedBooks(limit));
    }

    @GetMapping("/newest")
    public ResponseEntity<List<TrendingBookDTO>> getNewestBooks(
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(trendingBookService.getNewestBooks(limit));
    }

    @GetMapping("/top-category")
    public ResponseEntity<List<TrendingBookDTO>> getTopBooksByCategory(
            @RequestParam String category,
            @RequestParam(defaultValue = "5") int limit)
    {
        return ResponseEntity.ok(trendingBookService.getTopBooksByCategory(category, limit));
    }


}


