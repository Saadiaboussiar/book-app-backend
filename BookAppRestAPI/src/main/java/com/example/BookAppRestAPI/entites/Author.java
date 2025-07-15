package com.example.BookAppRestAPI.entites;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class Author {
    @Id
    private Long id;
    private String name;
    private int age;
    private String country;
    private int booksnumber;
    @OneToMany
    private List<Book> books;
}
