package com.sjsu.examscheduler.repository;

import com.sjsu.examscheduler.exam.model.Exam;
import com.sjsu.examscheduler.exam.repo.ExamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ExamTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExamRepository examRepository;

    @Test
    public void testSaveExam() {
        // Create a test exam
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

        // Save the exam
        Exam savedExam = examRepository.save(exam);

        // Verify the exam was saved
        assertNotNull(savedExam);
        assertEquals("CS 46A", savedExam.getCourse());
        assertEquals("1", savedExam.getSection());
    }

    @Test
    public void testFindByCourseAndSection() {
        // Create and save a test exam
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

        // Find the exam by course and section
        Optional<Exam> foundExam = examRepository.findByCourseAndSection("MATH 30", "1");

        // Verify the exam was found
        assertTrue(foundExam.isPresent());
        assertEquals("MATH 30", foundExam.get().getCourse());
        assertEquals("1", foundExam.get().getSection());
    }

    @Test
    public void testFindByCourseContainingIgnoreCase() {
        // Create and save test exams
        Exam exam1 = new Exam();
        exam1.setCourse("CS 46A");
        exam1.setSection("1");
        exam1.setCourseTitle("Introduction to Programming");
        exam1.setExamType("IN-PERSON - FORMAL EXAM - SJSU CAMPUS");
        exam1.setExamStartTime(LocalDateTime.now());
        exam1.setExamEndTime(LocalDateTime.now().plusHours(2));
        exam1.setBuilding("ENG");
        exam1.setRoom("280");
        exam1.setRowsFrom("1-15");
        exam1.setRowStart("AAA");
        exam1.setRowEnd("ZZZ");

        Exam exam2 = new Exam();
        exam2.setCourse("CS 46B");
        exam2.setSection("1");
        exam2.setCourseTitle("Advanced Programming");
        exam2.setExamType("IN-PERSON - FORMAL EXAM - SJSU CAMPUS");
        exam2.setExamStartTime(LocalDateTime.now());
        exam2.setExamEndTime(LocalDateTime.now().plusHours(2));
        exam2.setBuilding("ENG");
        exam2.setRoom("280");
        exam2.setRowsFrom("1-20");
        exam2.setRowStart("AAA");
        exam2.setRowEnd("ZZZ");

        examRepository.save(exam1);
        examRepository.save(exam2);

        // Find exams containing "CS"
        List<Exam> csExams = examRepository.findByCourseContainingIgnoreCase("CS");

        // Verify we found both CS exams
        assertEquals(2, csExams.size());
        assertTrue(csExams.stream().allMatch(exam -> exam.getCourse().startsWith("CS")));
    }
}
