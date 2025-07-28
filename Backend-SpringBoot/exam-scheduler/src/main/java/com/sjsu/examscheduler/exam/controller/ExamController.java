package com.sjsu.examscheduler.exam.controller;

import com.sjsu.examscheduler.exam.service.CurrentExamService;
import com.sjsu.examscheduler.exam.model.CurrentExam;
import com.sjsu.examscheduler.exam.model.HistoricExam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/exam")
public class ExamController {
    private final CurrentExamService currentExamService;

    @Autowired
    public ExamController(CurrentExamService currentExamService) {
        this.currentExamService = currentExamService;
    }


    @GetMapping
    public List<CurrentExam> getExams(@RequestParam(required = false) String className){

        if (className != null){
            return currentExamService.getExamsByClass(className);
        }
        else{
            return currentExamService.getExams();
        }
    }

    @GetMapping("/multiple")
    public List<CurrentExam> getExamsByNames(@RequestParam("names") List<String> examNames) {
        return currentExamService.getExamsByNames(examNames);
    }

    @PostMapping
    public ResponseEntity<CurrentExam> addExam(@RequestBody CurrentExam newExam) {
        CurrentExam createdExam = currentExamService.addExam(newExam);
        return new ResponseEntity<>(createdExam, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CurrentExam> updateExam(@RequestParam String className, @RequestParam String section, @RequestBody CurrentExam updatedExam) {
        // For now, return a simple response since we're using UUID-based IDs
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteExamsByClassName(@RequestParam(required = false) String className, @RequestParam(required = false) String section) {
        // For now, return a simple response since we're using UUID-based IDs
        return ResponseEntity.noContent().build();
    }
}
