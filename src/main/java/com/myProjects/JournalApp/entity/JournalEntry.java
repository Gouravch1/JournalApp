package com.myProjects.JournalApp.entity;


import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//POJO CLASS
@Document(collection = "journal_entries") //CONVERTING AS DOCUMENT FOR MONGO DB / DATABASE
@Getter
@Setter
public class JournalEntry {

    @Id // PRIMARY KEY
    private ObjectId id;
    private String title;
    private String content;
    private Date date;

}
