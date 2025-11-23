package com.example.project10;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.project10.TrendingBookDTO;

import java.util.List;
import java.util.stream.Collectors;



@Slf4j
@Service
public class TrendingBookService {

    private final BookRepository bookRepository;

    public TrendingBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<TrendingBookDTO> getTopRatedBooks(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return bookRepository.findTopByOrderByRatingDesc(pageable)
                .stream()
                .map(this::mapToTrendingDTO)
                .collect(Collectors.toList());
    }
    public List<TrendingBookDTO> getMostLoanedBooks(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return bookRepository.findAllByOrderByLoanCountDesc(pageable)
                .stream()
                .map(this::mapToTrendingDTO)
                .collect(Collectors.toList());
    }
    // 📅 الأحدث حسب تاريخ الإضافة
    public List<TrendingBookDTO> getNewestBooks(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return bookRepository.findTopByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(this::mapToTrendingDTO)
                .collect(Collectors.toList());
    }


    public List<TrendingBookDTO> getTopBooksByCategory(String category, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return bookRepository.findTopByCategoryOrderByCreatedAtDesc(category, pageable)
                .stream()
                .map(this::mapToTrendingDTO)
                .collect(Collectors.toList());
    }


    private TrendingBookDTO mapToTrendingDTO(Book book) {
        TrendingBookDTO dto = new TrendingBookDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle() != null ? book.getTitle() : "java");
        dto.setRating(book.getRating() != null ? book.getRating() : 0.0f);
        dto.setCreatedAt(book.getCreatedAt() != null ? book.getCreatedAt().toLocalDate() : null);
        dto.setCoverImageUrl(book.getCoverImageUrl());
        dto.setLoanCount(book.getLoanCount());

        // ✅ عدد المراجعات من العلاقة الجديدة
        dto.setReviewCount(book.getReviews() != null ? book.getReviews().size() : 0);

        // ✅ التصنيف والسعر
        dto.setCategory(book.getCategory());
        dto.setRentalPrice(book.getPrice());
        dto.setPurchasePrice(book.getPurchasePrice());

        // ✅ تحويل المؤلفين إلى قائمة أسماء
        dto.setAuthors(
                book.getAuthors() != null
                        ? book.getAuthors().stream()
                        .map(Author::getName)
                        .collect(Collectors.toList())
                        : List.of()
        );

        return dto;
    }



}
