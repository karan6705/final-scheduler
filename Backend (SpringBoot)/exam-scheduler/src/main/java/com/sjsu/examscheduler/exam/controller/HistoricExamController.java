package com.sjsu.examscheduler.exam.controller;

import com.sjsu.examscheduler.exam.model.HistoricExam;
import com.sjsu.examscheduler.exam.service.HistoricExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/historic-exams")
@CrossOrigin(origins = "*")
public class HistoricExamController {
    private final HistoricExamService historicExamService;

    @Autowired
    public HistoricExamController(HistoricExamService historicExamService) {
        this.historicExamService = historicExamService;
    }

    @GetMapping("/historic")
    public List<HistoricExam> getHistoricExamsByNameAndYear(@RequestParam("names") List<String> classNames, @RequestParam("years") List<String> years) {
        return historicExamService.getExamsByNameAndYear(classNames, years);
    }

    @PostMapping
    public HistoricExam createHistoricExam(@RequestBody HistoricExam historicExam) {
        return historicExamService.saveHistoricExam(historicExam);
    }
}
