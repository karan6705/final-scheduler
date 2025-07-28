package com.sjsu.examscheduler.service;

import com.sjsu.examscheduler.exam.model.HistoricExam;
import com.sjsu.examscheduler.exam.repo.HistoricExamRepository;
import com.sjsu.examscheduler.exam.service.HistoricExamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HistoricExamServiceTests {

    @Mock
    private HistoricExamRepository historicExamRepository;

    @InjectMocks
    private HistoricExamService historicExamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllHistoricExams() {
        // Create test data
        HistoricExam exam1 = new HistoricExam();
        exam1.setCourse("CS 46A");
        exam1.setSection("1");
        // Note: HistoricExam doesn't have courseTitle field
        exam1.setExamType("IN-PERSON - FORMAL EXAM - SJSU CAMPUS");
        exam1.setExamStartTime(LocalDateTime.now());
        exam1.setExamEndTime(LocalDateTime.now().plusHours(2));
        exam1.setBuilding("ENG");
        exam1.setRoom("280");
        exam1.setRowsFrom("1-15");
        exam1.setRowStart("AAA");
        exam1.setRowEnd("ZZZ");
        exam1.setYear("2023");

        HistoricExam exam2 = new HistoricExam();
        exam2.setCourse("MATH 30");
        exam2.setSection("1");
        // Note: HistoricExam doesn't have courseTitle field
        exam2.setExamType("IN-PERSON - FORMAL EXAM - SJSU CAMPUS");
        exam2.setExamStartTime(LocalDateTime.now());
        exam2.setExamEndTime(LocalDateTime.now().plusHours(2));
        exam2.setBuilding("SCI");
        exam2.setRoom("206");
        exam2.setRowsFrom("1-30");
        exam2.setRowStart("AAA");
        exam2.setRowEnd("ZZZ");
        exam2.setYear("2023");

        List<HistoricExam> expectedExams = Arrays.asList(exam1, exam2);

        // Mock repository behavior
        when(historicExamRepository.findAll()).thenReturn(expectedExams);

        // Call service method
        List<HistoricExam> result = historicExamService.getAllHistoricExams();

        // Verify results
        assertEquals(2, result.size());
        assertEquals("CS 46A", result.get(0).getCourse());
        assertEquals("MATH 30", result.get(1).getCourse());

        // Verify repository was called
        verify(historicExamRepository, times(1)).findAll();
    }

    @Test
    void testGetHistoricExamsByCourseAndYear() {
        // Create test data
        HistoricExam exam = new HistoricExam();
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
        exam.setYear("2023");

        List<HistoricExam> expectedExams = Arrays.asList(exam);

        // Mock repository behavior
        when(historicExamRepository.findByCourseContainingIgnoreCaseAndYear("CS", "2023"))
                .thenReturn(expectedExams);

        // Call service method
        List<HistoricExam> result = historicExamService.getHistoricExamsByCourseAndYear("CS", "2023");

        // Verify results
        assertEquals(1, result.size());
        assertEquals("CS 46A", result.get(0).getCourse());
        assertEquals("2023", result.get(0).getYear());

        // Verify repository was called with correct parameters
        verify(historicExamRepository, times(1)).findByCourseContainingIgnoreCaseAndYear("CS", "2023");
    }

    @Test
    void testSaveHistoricExam() {
        // Create test data
        HistoricExam exam = new HistoricExam();
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
        exam.setYear("2023");

        // Mock repository behavior
        when(historicExamRepository.save(any(HistoricExam.class))).thenReturn(exam);

        // Call service method
        HistoricExam result = historicExamService.saveHistoricExam(exam);

        // Verify results
        assertNotNull(result);
        assertEquals("CS 46A", result.getCourse());
        assertEquals("2023", result.getYear());

        // Verify repository was called
        verify(historicExamRepository, times(1)).save(exam);
    }
}
