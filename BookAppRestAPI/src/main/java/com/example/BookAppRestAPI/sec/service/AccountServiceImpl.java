package com.example.BookAppRestAPI.sec.service;

import com.example.BookAppRestAPI.sec.entities.AppRole;
import com.example.BookAppRestAPI.sec.entities.AppUser;
import com.example.BookAppRestAPI.sec.repositories.AppRoleRepository;
import com.example.BookAppRestAPI.sec.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AppUserRepository appUserRepository,AppRoleRepository appRoleRepository,PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder=passwordEncoder;
        this.appRoleRepository=appRoleRepository;

    }

    @Override
    public AppUser addNewUser(AppUser user) {
        String pw=user.getPassword();
        user.setPassword(passwordEncoder.encode(pw));
        return appUserRepository.save(user);
    }

    @Override
    public AppRole addNewRole(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public void addRoletoUser(String roleName, String username) {
        AppUser user=appUserRepository.findByUsername(username);
        AppRole role=appRoleRepository.findByRoleName(roleName);
        user.getApproles().add(role);

    }

    @Override
    public AppUser loadUserBYUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> UsersList() {
        return appUserRepository.findAll();
    }
}
