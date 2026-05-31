package com.myProjects.JournalApp.controller;


import com.myProjects.JournalApp.entity.User;
import com.myProjects.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
       List<User> users = userService.getAll();
       if(users != null) return new ResponseEntity<>(users , HttpStatus.OK);
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin")
    public void create_admin(@RequestBody User user){
        try{
            userService.saveEntryAsAdmin(user);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
