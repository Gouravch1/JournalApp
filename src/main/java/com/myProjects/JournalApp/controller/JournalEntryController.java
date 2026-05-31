package com.myProjects.JournalApp.controller;

import com.myProjects.JournalApp.entity.JournalEntry;
import com.myProjects.JournalApp.entity.User;
import com.myProjects.JournalApp.repository.JournalEntryRepository;
import com.myProjects.JournalApp.service.JournalEntryService;
import com.myProjects.JournalApp.service.UserService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/journal")
    public class JournalEntryController {

        @Autowired
        JournalEntryService journalEntryService;

        @Autowired
        UserService userService;

        @Autowired
        JournalEntryRepository journalEntryRepository;

        // inserting entries into user
        @PostMapping
        public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
             try {
                 journalEntryService.saveEntry(myEntry, username);
                 return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
             }catch (Exception e) {
                 System.out.println(e);
             }
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


        // Get All Entries of a user
        @GetMapping
        public ResponseEntity<?> getAllJournalEntriesOfUser() {
             Authentication auth = SecurityContextHolder.getContext().getAuthentication();
             String username = auth.getName();
             User user = userService.findByUserName(username);
             List<JournalEntry> list = user.getJournalEntryList();
             if(list != null) {
                 return new ResponseEntity<>(list, HttpStatus.OK);
             }
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


       // Deleting entry by id of a user
        @DeleteMapping("/id/{myId}")
        public void deleteById(@PathVariable ObjectId myId) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.findByUserName(username);
            journalEntryService.deleteById(username , myId);
        }

        @PutMapping("/id/{myId}")
        public ResponseEntity<?> uptdateJournalEntryOfUser (@RequestBody JournalEntry journalEntry, @PathVariable ObjectId myId){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User user = userService.findByUserName(username);
            Optional<JournalEntry> entries = user.getJournalEntryList()
                    .stream()
                    .filter(x -> x.getId().equals(myId))
                    .findFirst();
            if(entries.isPresent()){
                JournalEntry oldEntry = entries.get();
                oldEntry.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().equals(" ") ? journalEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(!journalEntry.getContent().equals(" ") && journalEntry.getContent() != null ? journalEntry.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

