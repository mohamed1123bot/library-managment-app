package com.example.project10;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorDTO {
    private Long id;
    private String name;
    private String bio;
    private List<String> books;

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.bio = author.getBio();
        this.books = author.getBooks()
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getBio() { return bio; }
    public List<String> getBooks() { return books; }
}