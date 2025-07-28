package com.sjsu.examscheduler.exam.service;

import com.sjsu.examscheduler.exam.repo.CurrentExamRepository;
import com.sjsu.examscheduler.exam.model.CurrentExam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrentExamService {
    private final CurrentExamRepository currentExamRepository;

    @Autowired
    public CurrentExamService(CurrentExamRepository currentExamRepository) {
        this.currentExamRepository = currentExamRepository;
    }

    public List<CurrentExam> getExams() {
        return currentExamRepository.findAll();
    }

    public List<CurrentExam> getExamsByClass(String className) {
        return currentExamRepository.findAll().stream()
                .filter(exam -> exam.getCourse().toLowerCase().contains(className.toLowerCase()))
                .collect(Collectors.toList());
    }

    public CurrentExam addExam(CurrentExam newExam) {
        return currentExamRepository.save(newExam);
    }

    public List<CurrentExam> getExamsByNames(List<String> examNames) {
        List<String> lowercaseExamNames = examNames.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        // Filter exams by class names provided in the list
        return currentExamRepository.findAll().stream()
                .filter(exam -> lowercaseExamNames.contains(exam.getCourse().toLowerCase()))
                .collect(Collectors.toList());
    }
} 