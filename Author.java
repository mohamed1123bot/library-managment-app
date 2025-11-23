package com.example.project10;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@Entity(name = "author")
    public class Author {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

    @Column(length = 1000)
    private String bio;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        }
        if (!book.getAuthors().contains(this)) {
            book.getAuthors().add(this);
        }
    }

        public Author() {}


    }

