package com.myProjects.JournalApp.repository;

import com.myProjects.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByusername(String username);
    void deleteByusername(String username);
}
