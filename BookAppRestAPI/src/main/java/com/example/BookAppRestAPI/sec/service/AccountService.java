package com.example.BookAppRestAPI.sec.service;

import com.example.BookAppRestAPI.sec.entities.AppRole;
import com.example.BookAppRestAPI.sec.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser user);
    AppRole addNewRole(AppRole role);
    void addRoletoUser(String roleName, String username);
    AppUser loadUserBYUsername(String username);
    List<AppUser> UsersList();

}
