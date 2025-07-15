package com.example.BookAppRestAPI.repositories;

import com.example.BookAppRestAPI.entites.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
