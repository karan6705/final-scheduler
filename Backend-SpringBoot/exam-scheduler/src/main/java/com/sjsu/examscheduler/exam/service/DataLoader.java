package com.sjsu.examscheduler.exam.service;

import com.sjsu.examscheduler.exam.model.CurrentExam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CurrentExamService currentExamService;

    @Override
    public void run(String... args) throws Exception {
        // Add some sample data for testing
        CurrentExam exam1 = new CurrentExam(
            "CS 46A", "1", "Introduction to Programming", 
            "IN-PERSON - FORMAL EXAM - SJSU CAMPUS",
            LocalDateTime.of(2024, 12, 15, 10, 0),
            LocalDateTime.of(2024, 12, 15, 12, 0),
            "ENG", "280", "1-15", "AAA", "ZZZ"
        );

        CurrentExam exam2 = new CurrentExam(
            "MATH 30", "1", "Calculus I", 
            "IN-PERSON - FORMAL EXAM - SJSU CAMPUS",
            LocalDateTime.of(2024, 12, 16, 14, 0),
            LocalDateTime.of(2024, 12, 16, 16, 0),
            "SCI", "206", "1-30", "AAA", "ZZZ"
        );

        CurrentExam exam3 = new CurrentExam(
            "CS 46B", "1", "Advanced Programming", 
            "IN-PERSON - FORMAL EXAM - SJSU CAMPUS",
            LocalDateTime.of(2024, 12, 17, 9, 0),
            LocalDateTime.of(2024, 12, 17, 11, 0),
            "ENG", "280", "1-20", "AAA", "ZZZ"
        );

        currentExamService.addExam(exam1);
        currentExamService.addExam(exam2);
        currentExamService.addExam(exam3);

        System.out.println("Sample data loaded successfully!");
    }
} 