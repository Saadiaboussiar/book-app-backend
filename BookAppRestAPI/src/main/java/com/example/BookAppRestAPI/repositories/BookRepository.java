package com.example.BookAppRestAPI.repositories;

import com.example.BookAppRestAPI.entites.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long>{
    boolean existsByTitleAndAuthor(String title, String author);

}
