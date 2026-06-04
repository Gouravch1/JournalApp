package com.myProjects.JournalApp.scheduler;

import com.myProjects.JournalApp.entity.JournalEntry;
import com.myProjects.JournalApp.entity.User;
import com.myProjects.JournalApp.enums.Sentiment;
import com.myProjects.JournalApp.repository.UserRepository;
import com.myProjects.JournalApp.repository.UserRepositoryImpl;
import com.myProjects.JournalApp.service.EmailService;
import com.myProjects.JournalApp.service.SentimentAnalysisServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MailScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisServices sentimentAnalysisServices;

    @Scheduled(cron = "0 0 9 ? * SUN *")
    public void fetchUserAndSendMail(){
        List<User> users = userRepository.getUsersOfSA();
        for(User user : users){
            List<JournalEntry> journalEntryList = user.getJournalEntryList();
            List<Sentiment> filteredList = journalEntryList.stream()
                    .filter(x -> x.getDate().after(Date.from(Instant.now().minus(7 , ChronoUnit.DAYS))))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());

            Map<Sentiment , Integer> map = new HashMap<>();
            for(Sentiment sentiment : filteredList){
                 map.put(sentiment , map.getOrDefault(sentiment , 0) + 1);
            }

            Sentiment currSentiment = null;
            int max = Integer.MIN_VALUE;
            for(Sentiment sentiment : map.keySet()){
                if(map.get(sentiment) > max ){
                    currSentiment = sentiment;
                    max = map.get(sentiment);
                }
            }
            try {
                emailService.sendMail(user.getEmail(), "Hi " + user.getUsername() + " Your Sentiment Analysis This Week is ", currSentiment.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
