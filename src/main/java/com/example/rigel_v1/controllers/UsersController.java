package com.example.rigel_v1.controllers;


import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UsersController {

    private final UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public void addUser(@NonNull @RequestBody Users user){
        this.userRepository.save(user);
    }

    @GetMapping//this function is a get request (fetches sth from the database)
    //works but since all Maps, etc. must be non-null
    public Optional<Users> getAllUsers(){
        Long a = new Long(6);
        return userRepository.findById(a);
    }
    /*public Iterable<Users> getAllUsers(){
        return userRepository.findAll();
    }*/

    @RequestMapping("/users")
    public String getUsers(Model model){
        model.addAttribute("students", userRepository.findAll());
        return "users/list";
    }
}
