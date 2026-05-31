package com.myProjects.JournalApp.service;

import com.myProjects.JournalApp.entity.JournalEntry;
import com.myProjects.JournalApp.entity.User;
import com.myProjects.JournalApp.repository.JournalEntryRepository;
import com.myProjects.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    // post
    @Transactional
    public void saveEntry(JournalEntry journalEntry , String username){
         try{
             User user = userService.findByUserName(username);
             journalEntry.setDate(new Date());
             JournalEntry saved = journalEntryRepository.save(journalEntry);
             user.getJournalEntryList().add(saved);
             userService.saveUser(user);
         } catch (Exception e) {
             throw new RuntimeException("An error occurred while saving the entry ",e);
         }
    }
    // for updating the current entry

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    // Get All
    public List<JournalEntry> getAll(){
         return journalEntryRepository.findAll();
    }

    //Get By Id
    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    //Delete by Id
    public ResponseEntity<?> deleteById(String username , ObjectId id){
        User user = userService.findByUserName(username);
        boolean removed = user.getJournalEntryList().removeIf(x -> x.getId().equals(id));
        if(removed) {
            userService.saveUser(user);
            journalEntryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
