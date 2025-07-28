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

        // Add more sample exams
        CurrentExam exam4 = new CurrentExam(
            "CS 146", "1", "Data Structures and Algorithms", 
            "IN-PERSON - FORMAL EXAM - SJSU CAMPUS",
            LocalDateTime.of(2024, 12, 18, 13, 0),
            LocalDateTime.of(2024, 12, 18, 15, 0),
            "ENG", "285", "1-25", "AAA", "ZZZ"
        );

        CurrentExam exam5 = new CurrentExam(
            "CS 151", "1", "Object-Oriented Design", 
            "IN-PERSON - FORMAL EXAM - SJSU CAMPUS",
            LocalDateTime.of(2024, 12, 19, 10, 0),
            LocalDateTime.of(2024, 12, 19, 12, 0),
            "ENG", "280", "1-20", "AAA", "ZZZ"
        );

        CurrentExam exam6 = new CurrentExam(
            "MATH 31", "1", "Calculus II", 
            "IN-PERSON - FORMAL EXAM - SJSU CAMPUS",
            LocalDateTime.of(2024, 12, 20, 14, 0),
            LocalDateTime.of(2024, 12, 20, 16, 0),
            "SCI", "206", "1-30", "AAA", "ZZZ"
        );

        CurrentExam exam7 = new CurrentExam(
            "PHYS 50", "1", "General Physics", 
            "IN-PERSON - FORMAL EXAM - SJSU CAMPUS",
            LocalDateTime.of(2024, 12, 21, 9, 0),
            LocalDateTime.of(2024, 12, 21, 11, 0),
            "SCI", "210", "1-25", "AAA", "ZZZ"
        );

        CurrentExam exam8 = new CurrentExam(
            "ENGR 10", "1", "Introduction to Engineering", 
            "IN-PERSON - FORMAL EXAM - SJSU CAMPUS",
            LocalDateTime.of(2024, 12, 22, 15, 0),
            LocalDateTime.of(2024, 12, 22, 17, 0),
            "ENG", "290", "1-20", "AAA", "ZZZ"
        );

        currentExamService.addExam(exam4);
        currentExamService.addExam(exam5);
        currentExamService.addExam(exam6);
        currentExamService.addExam(exam7);
        currentExamService.addExam(exam8);

        System.out.println("Sample data loaded successfully!");
    }
} 