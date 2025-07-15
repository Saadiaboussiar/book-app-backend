package com.example.BookAppRestAPI.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.BookAppRestAPI.entites.Book;
import com.example.BookAppRestAPI.repositories.BookRepository;
import com.example.BookAppRestAPI.sec.entities.AppUser;
import com.example.BookAppRestAPI.sec.repositories.AppUserRepository;
import com.example.BookAppRestAPI.sec.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class BooksController {
    private BookRepository bookRepository;
    private AccountService accountService;
    private AppUserRepository appUserRepository;


    public BooksController(BookRepository bookRepository, AccountService accountService, AppUserRepository appUserRepository) {
        this.bookRepository = bookRepository;
        this.accountService = accountService;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping("/books")
    @PostAuthorize("hasAuthority('USER')")
    public List<Book> showBooks(){
        List<Book> booklist= bookRepository.findAll();
        booklist.sort(Comparator.comparing(Book::getId));
        return booklist;
    }
    @GetMapping(path = "/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String authorizationToken=request.getHeader("Authorization");
        if(authorizationToken!=null && authorizationToken.startsWith("Bearer ")){
            try {
                String jwt=authorizationToken.substring(7);
                Algorithm algo=Algorithm.HMAC256("mySecret2005");
                JWTVerifier jwtVerifier= JWT.require(algo).build();
                DecodedJWT decodeJwt= jwtVerifier.verify(jwt);
                String username=decodeJwt.getSubject();
                AppUser appUser=accountService.loadUserBYUsername(username);
                String jwtAccessToken= JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",appUser.getApproles().stream().map(r->r.getRoleName()).collect(Collectors.toList())) //On transforme une collection de type AppRole a une to type string
                        .sign(algo);

                Map<String,String> idToken=new HashMap<>();
                idToken.put("access-token",jwtAccessToken);
                idToken.put("refresh-token",jwt);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),idToken);

            }catch (Exception e){
                throw e;
            }
        }else{
            throw new RuntimeException("Refresh Token required!!");
        }
    }

    @PostMapping("/books")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")

    public String addBook(@RequestBody Book book){
        if(!bookRepository.existsByTitleAndAuthor(book.getTitle(),book.getAuthor())){
            bookRepository.save(book);}
        return "Book added"+book.getTitle();
    }

    @PutMapping("/books/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")

    public Book editbook(@PathVariable Long id,@RequestBody Book book){

        Book b= bookRepository.findById(id).get();
        b.setAuthor(book.getAuthor());
        b.setTitle(book.getTitle());
        b.setCategory(book.getCategory());
        b.setPublisher(book.getPublisher());
        b.setPublishyear(book.getPublishyear());
        b.setPages(book.getPages());
        bookRepository.save(b);
        return b;
    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")

    public boolean delete(@PathVariable Long id) {
        if (!bookRepository.findById(id).equals(Optional.empty())) {
            bookRepository.deleteById(id);
            
            return true;
        }
        return false;
    }

    @GetMapping(path="/users")
    public List<AppUser> users(){
        return accountService.UsersList();
    }

    @PostMapping(path="/users")
    public AppUser saveUser(@RequestBody AppUser appUser){
        if(appUserRepository.findByUsername(appUser.getUsername())==null){
            AppUser newUser=accountService.addNewUser(appUser);
            if(!appUser.getUsername().equalsIgnoreCase("manager") && !appUser.getUsername().equalsIgnoreCase("admin")){
                accountService.addRoletoUser("USER",appUser.getUsername());
            } else if (appUser.getUsername().equalsIgnoreCase("manager")) {
                accountService.addRoletoUser("MANAGER",appUser.getUsername());
                accountService.addRoletoUser("USER",appUser.getUsername());
            }else{
                accountService.addRoletoUser("ADMIN",appUser.getUsername());
                accountService.addRoletoUser("USER",appUser.getUsername());

            }
            return newUser;
        }else{
            return null;
        }

    }


}
