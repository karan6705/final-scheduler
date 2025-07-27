package com.sjsu.examscheduler.exam.service;

import com.sjsu.examscheduler.exam.model.HistoricExam;
import com.sjsu.examscheduler.exam.repo.HistoricExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HistoricExamService {
    private final HistoricExamRepository historicExamRepository;

    @Autowired
    public HistoricExamService(HistoricExamRepository historicExamRepository) {
        this.historicExamRepository = historicExamRepository;
    }

    public List<HistoricExam> getExamsByNameAndYear(List<String> classNames, List<String> years) {
        Set<String> uniqueCombinations = new HashSet<>();
        List<String> lowercaseHistoricExamNames = classNames.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        List<String> lowercaseYears = years.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        // Filter exams by class names provided in the list
        return historicExamRepository.findAll().stream()
                .filter(historicExam -> {
                    String combination = historicExam.getCourse().toLowerCase() + historicExam.getYear().toLowerCase();
                    boolean isNewCombination = uniqueCombinations.add(combination);
                    return isNewCombination && lowercaseHistoricExamNames.contains(historicExam.getCourse().toLowerCase())
                            && lowercaseYears.contains(historicExam.getYear().toLowerCase());
                })
                .collect(Collectors.toList());
    }

    public List<HistoricExam> getAllHistoricExams() {
        return historicExamRepository.findAll();
    }

    public List<HistoricExam> getHistoricExamsByCourseAndYear(String course, String year) {
        return historicExamRepository.findByCourseContainingIgnoreCaseAndYear(course, year);
    }

    public HistoricExam saveHistoricExam(HistoricExam historicExam) {
        return historicExamRepository.save(historicExam);
    }
}
