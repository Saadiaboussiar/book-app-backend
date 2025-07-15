package com.example.BookAppRestAPI.entites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.*;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor @ToString @Builder  // @Builder is a constructor in
// which we can pass values via the name of the attribute
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String category;
    private String publisher;
    private int publishyear;
    private int pages;
}
