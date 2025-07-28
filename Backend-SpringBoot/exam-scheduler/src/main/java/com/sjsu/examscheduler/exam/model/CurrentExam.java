package com.sjsu.examscheduler.exam.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "current_exams")
public class CurrentExam implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    @Column(name = "course", nullable = false)
    private String course;
    
    @Column(name = "section", nullable = false)
    private String section;
    
    @Column(name = "course_title", nullable = false)
    private String courseTitle;
    
    @Column(name = "exam_type", nullable = false)
    private String examType;
    
    @Column(name = "exam_start_time", nullable = false)
    private LocalDateTime examStartTime;
    
    @Column(name = "exam_end_time", nullable = false)
    private LocalDateTime examEndTime;
    
    @Column(name = "building")
    private String building;
    
    @Column(name = "room")
    private String room;
    
    @Column(name = "rows_from")
    private String rowsFrom;
    
    @Column(name = "row_start")
    private String rowStart;
    
    @Column(name = "row_end")
    private String rowEnd;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public CurrentExam() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public CurrentExam(String course, String section, String courseTitle, String examType, 
                      LocalDateTime examStartTime, LocalDateTime examEndTime, String building, 
                      String room, String rowsFrom, String rowStart, String rowEnd) {
        this();
        this.course = course;
        this.section = section;
        this.courseTitle = courseTitle;
        this.examType = examType;
        this.examStartTime = examStartTime;
        this.examEndTime = examEndTime;
        this.building = building;
        this.room = room;
        this.rowsFrom = rowsFrom;
        this.rowStart = rowStart;
        this.rowEnd = rowEnd;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public LocalDateTime getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(LocalDateTime examStartTime) {
        this.examStartTime = examStartTime;
    }

    public LocalDateTime getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(LocalDateTime examEndTime) {
        this.examEndTime = examEndTime;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRowsFrom() {
        return rowsFrom;
    }

    public void setRowsFrom(String rowsFrom) {
        this.rowsFrom = rowsFrom;
    }

    public String getRowStart() {
        return rowStart;
    }

    public void setRowStart(String rowStart) {
        this.rowStart = rowStart;
    }

    public String getRowEnd() {
        return rowEnd;
    }

    public void setRowEnd(String rowEnd) {
        this.rowEnd = rowEnd;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CurrentExam{" +
                "id=" + id +
                ", course='" + course + '\'' +
                ", section='" + section + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", examType='" + examType + '\'' +
                ", examStartTime=" + examStartTime +
                ", examEndTime=" + examEndTime +
                ", building='" + building + '\'' +
                ", room='" + room + '\'' +
                ", rowsFrom='" + rowsFrom + '\'' +
                ", rowStart='" + rowStart + '\'' +
                ", rowEnd='" + rowEnd + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 