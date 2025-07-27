package com.sjsu.examscheduler.integration;

import com.sjsu.examscheduler.exam.model.Exam;
import com.sjsu.examscheduler.exam.repo.ExamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ExamControllerIntegrationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ExamRepository examRepository;

    private MockMvc mockMvc;

    @Test
    void testGetAllExams() throws Exception {
        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Create test exam
        Exam exam = new Exam();
        exam.setCourse("CS 46A");
        exam.setSection("1");
        exam.setCourseTitle("Introduction to Programming");
        exam.setExamType("IN-PERSON - FORMAL EXAM - SJSU CAMPUS");
        exam.setExamStartTime(LocalDateTime.now());
        exam.setExamEndTime(LocalDateTime.now().plusHours(2));
        exam.setBuilding("ENG");
        exam.setRoom("280");
        exam.setRowsFrom("1-15");
        exam.setRowStart("AAA");
        exam.setRowEnd("ZZZ");

        examRepository.save(exam);

        // Test GET /api/v1/exam endpoint
        mockMvc.perform(get("/api/v1/exam")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].course").value("CS 46A"))
                .andExpect(jsonPath("$[0].section").value("1"));
    }

    @Test
    void testGetExamByClassName() throws Exception {
        // Setup MockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Create test exam
        Exam exam = new Exam();
        exam.setCourse("MATH 30");
        exam.setSection("1");
        exam.setCourseTitle("Calculus I");
        exam.setExamType("IN-PERSON - FORMAL EXAM - SJSU CAMPUS");
        exam.setExamStartTime(LocalDateTime.now());
        exam.setExamEndTime(LocalDateTime.now().plusHours(2));
        exam.setBuilding("SCI");
        exam.setRoom("206");
        exam.setRowsFrom("1-30");
        exam.setRowStart("AAA");
        exam.setRowEnd("ZZZ");

        examRepository.save(exam);

        // Test GET /api/v1/exam?className=MATH endpoint
        mockMvc.perform(get("/api/v1/exam")
                        .param("className", "MATH")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].course").value("MATH 30"));
    }
}
