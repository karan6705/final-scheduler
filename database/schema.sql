-- SJSU Exam Scheduler Database Schema
-- This schema is designed for Supabase PostgreSQL

-- Enable UUID extension for unique identifiers
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create enum for exam types
CREATE TYPE exam_type AS ENUM (
    'IN-PERSON - FORMAL EXAM - SJSU CAMPUS',
    'IN-PERSON - FORMAL EXAM - D.T. CAMPUS',
    'IN-PERSON - FORMAL EXAM - MAC CAMPUS',
    'IN-PERSON - LAB EXAM - SJSU CAMPUS',
    'IN-PERSON - LAB EXAM - D.T. CAMPUS',
    'IN-PERSON - LAB EXAM - MAC CAMPUS',
    'IN-PERSON - PRACTICAL EXAM - SJSU CAMPUS',
    'IN-PERSON - ORAL EXAM - SJSU CAMPUS',
    'IN-PERSON - TWO DAY ORAL EXAM - D.T. CAMPUS',
    'IN-PERSON - IN DEPARTMENT EXAM - D.T. CAMPUS',
    'ONLINE - TAKE-HOME - 24 HOURS',
    'ONLINE - TAKE-HOME - 48 HOURS',
    'ONLINE - TAKE-HOME - 72 HOURS',
    'ONLINE - TAKE-HOME - 7 DAYS',
    'ONLINE - TAKE-HOME - 8 DAYS',
    'ONLINE - TAKE-HOME - 10 DAYS',
    'ONLINE - TIMED EXAM - 3 HOURS',
    'ONLINE - TIMED EXAM - 72 HOURS'
);

-- Create enum for semesters
CREATE TYPE semester_type AS ENUM ('F2023', 'W2024', 'S2024', 'F2024', 'W2025');

-- Current semester exams table
CREATE TABLE current_exams (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    course VARCHAR(20) NOT NULL,
    section VARCHAR(10) NOT NULL,
    course_title VARCHAR(255) NOT NULL,
    exam_type exam_type NOT NULL,
    exam_start_time TIMESTAMP NOT NULL,
    exam_end_time TIMESTAMP NOT NULL,
    building VARCHAR(50),
    room VARCHAR(50),
    rows_from VARCHAR(20),
    row_start VARCHAR(10),
    row_end VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(course, section)
);

-- Historical exams table
CREATE TABLE historic_exams (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    course VARCHAR(20) NOT NULL,
    section VARCHAR(10) NOT NULL,
    year semester_type NOT NULL,
    exam_type exam_type NOT NULL,
    exam_start_time TIMESTAMP NOT NULL,
    exam_end_time TIMESTAMP NOT NULL,
    building VARCHAR(50),
    room VARCHAR(50),
    rows_from VARCHAR(20),
    row_start VARCHAR(10),
    row_end VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(course, section, year)
);

-- Course catalog table for better search functionality
CREATE TABLE course_catalog (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_title VARCHAR(255) NOT NULL,
    department VARCHAR(50),
    credits INTEGER,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Building information table
CREATE TABLE buildings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    building_code VARCHAR(10) UNIQUE NOT NULL,
    building_name VARCHAR(100) NOT NULL,
    campus VARCHAR(50),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_current_exams_course ON current_exams(course);
CREATE INDEX idx_current_exams_section ON current_exams(section);
CREATE INDEX idx_current_exams_start_time ON current_exams(exam_start_time);
CREATE INDEX idx_current_exams_building ON current_exams(building);

CREATE INDEX idx_historic_exams_course ON historic_exams(course);
CREATE INDEX idx_historic_exams_year ON historic_exams(year);
CREATE INDEX idx_historic_exams_start_time ON historic_exams(exam_start_time);

CREATE INDEX idx_course_catalog_code ON course_catalog(course_code);
CREATE INDEX idx_course_catalog_department ON course_catalog(department);

CREATE INDEX idx_buildings_code ON buildings(building_code);

-- Create full-text search indexes
CREATE INDEX idx_current_exams_search ON current_exams USING gin(to_tsvector('english', course || ' ' || course_title));
CREATE INDEX idx_historic_exams_search ON historic_exams USING gin(to_tsvector('english', course || ' ' || year::text));

-- Create updated_at trigger function
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create triggers for updated_at
CREATE TRIGGER update_current_exams_updated_at BEFORE UPDATE ON current_exams FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_historic_exams_updated_at BEFORE UPDATE ON historic_exams FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_course_catalog_updated_at BEFORE UPDATE ON course_catalog FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_buildings_updated_at BEFORE UPDATE ON buildings FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Create views for easier querying
CREATE VIEW exam_schedule AS
SELECT 
    'current' as exam_period,
    course,
    section,
    course_title,
    exam_type,
    exam_start_time,
    exam_end_time,
    building,
    room,
    rows_from,
    row_start,
    row_end
FROM current_exams
UNION ALL
SELECT 
    'historic' as exam_period,
    course,
    section,
    '' as course_title,
    exam_type,
    exam_start_time,
    exam_end_time,
    building,
    room,
    rows_from,
    row_start,
    row_end
FROM historic_exams;

-- Create a function to search exams
CREATE OR REPLACE FUNCTION search_exams(search_term TEXT)
RETURNS TABLE (
    exam_period TEXT,
    course VARCHAR(20),
    section VARCHAR(10),
    course_title VARCHAR(255),
    exam_type exam_type,
    exam_start_time TIMESTAMP,
    exam_end_time TIMESTAMP,
    building VARCHAR(50),
    room VARCHAR(50)
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        'current' as exam_period,
        ce.course,
        ce.section,
        ce.course_title,
        ce.exam_type,
        ce.exam_start_time,
        ce.exam_end_time,
        ce.building,
        ce.room
    FROM current_exams ce
    WHERE ce.course ILIKE '%' || search_term || '%'
       OR ce.course_title ILIKE '%' || search_term || '%'
    UNION ALL
    SELECT 
        'historic' as exam_period,
        he.course,
        he.section,
        '' as course_title,
        he.exam_type,
        he.exam_start_time,
        he.exam_end_time,
        he.building,
        he.room
    FROM historic_exams he
    WHERE he.course ILIKE '%' || search_term || '%';
END;
$$ LANGUAGE plpgsql;

-- Grant necessary permissions (for Supabase)
GRANT ALL ON ALL TABLES IN SCHEMA public TO authenticated;
GRANT ALL ON ALL SEQUENCES IN SCHEMA public TO authenticated;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO authenticated; 