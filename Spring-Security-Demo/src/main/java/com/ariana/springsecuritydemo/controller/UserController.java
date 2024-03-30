package com.ariana.springsecuritydemo.controller;

import com.ariana.springsecuritydemo.model.Comenzi;
import com.ariana.springsecuritydemo.service.ComenziService;
import com.ariana.springsecuritydemo.service.UserService;
import com.ariana.springsecuritydemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
@EnableWebSecurity
public class UserController {

    private final UserService userService;
    private final ComenziService comenziService;

    @Autowired
    public UserController(UserService userService, ComenziService comenziService) {
        this.userService = userService;
        this.comenziService = comenziService;
    }

    @GetMapping("/getAll")
    public List<User> list(){
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public String add(@RequestBody User user){
        userService.saveUser(user);
        return "New user is added";
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        try {
            User utilizator = userService.get(id);
            return new ResponseEntity<User>(utilizator, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            System.out.println("User not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user,@PathVariable Long id){
        try{
            User existingUser = userService.get(id);
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);

        }catch (NoSuchElementException e){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "Deleted User with id "+id;
    }

    @GetMapping("/{startDate}/{endDate}")
    public ResponseEntity<List<Comenzi>> generateReport(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<Comenzi> reports = comenziService.getOrdersBetweenDates(startDate, endDate);
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
