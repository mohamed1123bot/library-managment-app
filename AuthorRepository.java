package com.example.project10;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>
{
    List<Author> findByNameContainingIgnoreCase(String keyword);
    Optional<Author> findByName(String name);

}