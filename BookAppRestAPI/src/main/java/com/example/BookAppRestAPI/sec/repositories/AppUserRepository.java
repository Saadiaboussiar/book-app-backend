package com.example.BookAppRestAPI.sec.repositories;

import com.example.BookAppRestAPI.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}
