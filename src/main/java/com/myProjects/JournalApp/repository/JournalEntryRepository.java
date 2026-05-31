package com.myProjects.JournalApp.repository;


import com.myProjects.JournalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

//mongo db repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
     void deleteById(ObjectId id);
}


//controller ---> service ---> repository