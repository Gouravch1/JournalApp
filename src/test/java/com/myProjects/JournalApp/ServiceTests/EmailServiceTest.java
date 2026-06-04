package com.myProjects.JournalApp.ServiceTests;


import com.myProjects.JournalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EmailService service;

    @Test
    public void emailServiceTest(){
        service.sendMail("gcd94701@gmail.com" , " Testing Mail Service ", "Testing mail Service (mail 1)");
    }
}
