package com.example.project10;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter

public class TrendingBookDTO {
    private Long id;
    private String title;
    private List<String> authors;

    private String coverImageUrl;
    private int loanCount;
    private float rating;
    private int reviewCount;
    private LocalDate createdAt;
    private String category;
    private double rentalPrice;
    private double purchasePrice;




    public TrendingBookDTO() {}


}
