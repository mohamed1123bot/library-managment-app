package com.example.project10;

import com.example.project10.AuthorDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BookDTO {
    private Long id;
    private String title;
    private int year;
    private String category;
    private int totalCopies;
    private float rating;
    private LocalDateTime createdAt;
    private String coverImageUrl;
    private int loanCount;
    private String description;
    private double price;
    private double purchasePrice;
    private String saleType; // BUY, RENT, BOTH
    private List<AuthorDTO> authors;
    private boolean canBuy;
    private boolean canRent;
}