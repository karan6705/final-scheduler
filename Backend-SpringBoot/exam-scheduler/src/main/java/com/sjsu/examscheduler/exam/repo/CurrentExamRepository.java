package com.sjsu.examscheduler.exam.repo;

import com.sjsu.examscheduler.exam.model.CurrentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CurrentExamRepository extends JpaRepository<CurrentExam, UUID> {
    List<CurrentExam> findByCourseContainingIgnoreCase(String course);
    List<CurrentExam> findByCourseContainingIgnoreCaseAndExamType(String course, String examType);
} 