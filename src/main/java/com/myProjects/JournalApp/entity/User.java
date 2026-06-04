package com.myProjects.JournalApp.entity;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NotBlank
    private String username;
    private String email;
    private Boolean sentimentAnalysis;
    @NotBlank
    private String password;
    private LocalDateTime createdAt;

    @DBRef
    private List<JournalEntry> journalEntryList = new ArrayList<>();
    private List<String> roles;
}
