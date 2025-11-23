package com.example.project10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    public Review addReview(Review review) {
        if (review.getMember() == null || review.getMember().getId() == null) {
            throw new IllegalArgumentException("Member ID is required");
        }
        if (review.getBook() == null || review.getBook().getId() == null) {
            throw new IllegalArgumentException("Book ID is required");
        }
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Long memberId = review.getMember().getId();
        Long bookId = review.getBook().getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        review.setMember(member);
        review.setBook(book);

        Review savedReview = reviewRepository.save(review);

        Double avgRating = reviewRepository.findAverageRatingByBookId(bookId);
        if (avgRating == null) avgRating = 0.0;

        book.setRating(avgRating.floatValue());
        bookRepository.save(book);

        return savedReview;
    }

    public List<Review> getReviewsByBook(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    public List<Review> getReviewsByMember(Long memberId) {
        return reviewRepository.findByMemberId(memberId);
    }

    public Double getAverageRating(Long bookId) {
        return reviewRepository.getAverageRatingByBook(bookId);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
  }
}