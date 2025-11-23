package com.example.project10;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByCategory(String category);
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    List<Book> findTopByOrderByRatingDesc(Pageable pageable);
    List<Book> findTopByOrderByCreatedAtDesc(Pageable pageable);

    List<Book> findTopByCategoryOrderByRatingDesc(String category, Pageable pageable);
    List<Book> findTopByCategoryOrderByCreatedAtDesc(String category, Pageable pageable);
    List<Book> findAllByOrderByLoanCountDesc(Pageable pageable);
    Optional<Book> findByTitle(String title);
}

