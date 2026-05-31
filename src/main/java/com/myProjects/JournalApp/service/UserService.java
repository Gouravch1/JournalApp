package com.myProjects.JournalApp.service;

import com.myProjects.JournalApp.entity.User;
import com.myProjects.JournalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j            // LOGGING TRACES
public class UserService {
    @Autowired
    private UserRepository userRepository;


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void saveEntry(User user){
        try {
            user.setCreatedAt(LocalDateTime.now());
            user.setRoles(Arrays.asList("USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            log.info("username {} created successfully" , user.getUsername());
        } catch (DuplicateKeyException e) {
            log.error("username {} already exists", user.getUsername());
        }

    }
    public void saveEntryAsAdmin(User user){
        user.setCreatedAt(LocalDateTime.now());
        user.setRoles(Arrays.asList("USER","ADMIN"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }

    // will implement later on
    public void deleteById(User data){
         userRepository.delete(data);
    }

    public void deleteAll() {userRepository.deleteAll();}

    public User findByUserName(String username){
        return userRepository.findByusername(username);
    }
}
