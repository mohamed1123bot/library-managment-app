package com.example.project10;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int year;
    private String category;
    private int totalCopies;
    private Float rating;
    private LocalDateTime createdAt;
    private String coverImageUrl;
    private int loanCount;
    private String description;
    private Double price;
    private double purchasePrice;




    @Enumerated(EnumType.STRING)
    private SaleType saleType = SaleType.BOTH;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private List<Author> authors = new ArrayList<>();
    public Book() {}

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void incrementLoanCount() {
        this.loanCount++;
    }

    public void decrementLoanCount() {
        if (this.loanCount > 0) this.loanCount--;
    }

    // تحقق إذا كان ممكن الشراء
    public boolean canBuy() {
        return this.saleType == SaleType.BUY || this.saleType == SaleType.BOTH && this.totalCopies >= 5;
    }

    // تحقق إذا كان ممكن الإعارة
    public boolean canRent() {
        return this.saleType == SaleType.RENT || this.saleType == SaleType.BOTH && this.totalCopies > 0;
    }

    public enum SaleType {
        RENT, BUY, BOTH
    }
}