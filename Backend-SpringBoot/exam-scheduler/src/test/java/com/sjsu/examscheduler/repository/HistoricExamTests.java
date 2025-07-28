package com.sjsu.examscheduler.repository;

import com.sjsu.examscheduler.exam.model.HistoricExam;
import com.sjsu.examscheduler.exam.repo.HistoricExamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class HistoricExamTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HistoricExamRepository historicExamRepository;

    @Test
    public void testSaveHistoricExam() {
        // Create a test historic exam
        HistoricExam exam = new HistoricExam();
        exam.setCourse("CS 46A");
        exam.setSection("1");
        // Note: HistoricExam doesn't have courseTitle field
        exam.setExamType("IN-PERSON - FORMAL EXAM - SJSU CAMPUS");
        exam.setExamStartTime(LocalDateTime.now());
        exam.setExamEndTime(LocalDateTime.now().plusHours(2));
        exam.setBuilding("ENG");
        exam.setRoom("280");
        exam.setRowsFrom("1-15");
        exam.setRowStart("AAA");
        exam.setRowEnd("ZZZ");
        exam.setYear("2023");

        // Save the exam
        HistoricExam savedExam = historicExamRepository.save(exam);

        // Verify the exam was saved
        assertNotNull(savedExam);
        assertEquals("CS 46A", savedExam.getCourse());
        assertEquals("1", savedExam.getSection());
        assertEquals("2023", savedExam.getYear());
    }

    @Test
    public void testFindByCourseAndSectionAndYear() {
        // Create and save a test historic exam
        HistoricExam exam = new HistoricExam();
        exam.setCourse("MATH 30");
        exam.setSection("1");
        // Note: HistoricExam doesn't have courseTitle field
        exam.setExamType("IN-PERSON - FORMAL EXAM - SJSU CAMPUS");
        exam.setExamStartTime(LocalDateTime.now());
        exam.setExamEndTime(LocalDateTime.now().plusHours(2));
        exam.setBuilding("SCI");
        exam.setRoom("206");
        exam.setRowsFrom("1-30");
        exam.setRowStart("AAA");
        exam.setRowEnd("ZZZ");
        exam.setYear("2023");

        historicExamRepository.save(exam);

        // Find the exam by course, section, and year
        Optional<HistoricExam> foundExam = historicExamRepository.findByCourseAndSectionAndYear("MATH 30", "1", "2023");

        // Verify the exam was found
        assertTrue(foundExam.isPresent());
        assertEquals("MATH 30", foundExam.get().getCourse());
        assertEquals("1", foundExam.get().getSection());
        assertEquals("2023", foundExam.get().getYear());
    }

    @Test
    public void testFindByCourseContainingIgnoreCaseAndYear() {
        // Create and save test historic exams
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
        exam2.setCourse("CS 46B");
        exam2.setSection("1");
        // Note: HistoricExam doesn't have courseTitle field
        exam2.setExamType("IN-PERSON - FORMAL EXAM - SJSU CAMPUS");
        exam2.setExamStartTime(LocalDateTime.now());
        exam2.setExamEndTime(LocalDateTime.now().plusHours(2));
        exam2.setBuilding("ENG");
        exam2.setRoom("280");
        exam2.setRowsFrom("1-20");
        exam2.setRowStart("AAA");
        exam2.setRowEnd("ZZZ");
        exam2.setYear("2023");

        historicExamRepository.save(exam1);
        historicExamRepository.save(exam2);

        // Find historic exams containing "CS" for year 2023
        List<HistoricExam> csExams = historicExamRepository.findByCourseContainingIgnoreCaseAndYear("CS", "2023");

        // Verify we found both CS exams
        assertEquals(2, csExams.size());
        assertTrue(csExams.stream().allMatch(exam -> exam.getCourse().startsWith("CS")));
        assertTrue(csExams.stream().allMatch(exam -> "2023".equals(exam.getYear())));
    }
}
