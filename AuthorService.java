package com.example.project10;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }
    public AuthorDTO getAuthorWithBooks(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author with id " + id + " not found"));
        return new AuthorDTO(author);
    }

    public Author updateAuthor(Long id, Author updatedAuthor)
    {
        Author author = authorRepository.findById(id).orElseThrow();
        author.setName(updatedAuthor.getName());
        author.setBio(updatedAuthor.getBio());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> searchAuthors(String keyword) {
        return authorRepository.findByNameContainingIgnoreCase(keyword);
  }
}