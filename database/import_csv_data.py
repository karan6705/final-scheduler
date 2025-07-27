#!/usr/bin/env python3
"""
CSV Data Import Script for SJSU Exam Scheduler
This script imports exam data from CSV files into Supabase database.
"""

import csv
import os
import sys
from datetime import datetime
import psycopg2
from psycopg2.extras import execute_values
import argparse


def parse_date_time(date_str):
    """Parse date string from CSV format to PostgreSQL timestamp."""
    try:
        # Handle format like "15-Dec-2024 at 8:30 AM"
        date_obj = datetime.strptime(date_str, "%d-%b-%Y at %I:%M %p")
        return date_obj.strftime("%Y-%m-%d %H:%M:%S")
    except ValueError:
        print(f"Warning: Could not parse date: {date_str}")
        return None


def connect_to_supabase(connection_string):
    """Connect to Supabase database."""
    try:
        conn = psycopg2.connect(connection_string)
        return conn
    except Exception as e:
        print(f"Error connecting to database: {e}")
        sys.exit(1)


def import_current_exams(conn, csv_file):
    """Import current semester exams from CSV."""
    print(f"Importing current exams from {csv_file}...")

    with open(csv_file, 'r', encoding='utf-8') as file:
        reader = csv.DictReader(file)

        data = []
        for row in reader:
            start_time = parse_date_time(row['Exam_Start_Time'])
            end_time = parse_date_time(row['Exam_End_Time'])

            if start_time and end_time:
                data.append((
                    row['Course'],
                    row['Section'],
                    row['Course_Title'],
                    row['Exam_Type'],
                    start_time,
                    end_time,
                    row['Building'],
                    row['Room'],
                    row['Rows_From'],
                    row['Row_Start'],
                    row['Row_End']
                ))

        if data:
            with conn.cursor() as cur:
                # Clear existing data
                cur.execute("DELETE FROM current_exams")

                # Insert new data
                execute_values(
                    cur,
                    """
                    INSERT INTO current_exams 
                    (course, section, course_title, exam_type, exam_start_time, 
                     exam_end_time, building, room, rows_from, row_start, row_end)
                    VALUES %s
                    """,
                    data
                )
                conn.commit()
                print(f"Imported {len(data)} current exams")
        else:
            print("No valid data found in CSV file")


def import_historic_exams(conn, csv_file):
    """Import historical exams from CSV."""
    print(f"Importing historic exams from {csv_file}...")

    with open(csv_file, 'r', encoding='utf-8') as file:
        reader = csv.DictReader(file)

        data = []
        for row in reader:
            start_time = parse_date_time(row['Exam_Start_Time'])
            end_time = parse_date_time(row['Exam_End_Time'])

            if start_time and end_time:
                data.append((
                    row['Course'],
                    row['Section'],
                    row['Year'],
                    row['Exam_Type'],
                    start_time,
                    end_time,
                    row['Building'],
                    row['Room'],
                    row['Rows_From'],
                    row['Row_Start'],
                    row['Row_End']
                ))

        if data:
            with conn.cursor() as cur:
                # Clear existing data
                cur.execute("DELETE FROM historic_exams")

                # Insert new data
                execute_values(
                    cur,
                    """
                    INSERT INTO historic_exams 
                    (course, section, year, exam_type, exam_start_time, 
                     exam_end_time, building, room, rows_from, row_start, row_end)
                    VALUES %s
                    """,
                    data
                )
                conn.commit()
                print(f"Imported {len(data)} historic exams")
        else:
            print("No valid data found in CSV file")


def create_course_catalog(conn):
    """Create course catalog from exam data."""
    print("Creating course catalog...")

    with conn.cursor() as cur:
        # Get unique courses from current exams
        cur.execute("""
            INSERT INTO course_catalog (course_code, course_title, department)
            SELECT DISTINCT 
                course as course_code,
                course_title,
                CASE 
                    WHEN course LIKE 'CS%' THEN 'Computer Science'
                    WHEN course LIKE 'MATH%' THEN 'Mathematics'
                    WHEN course LIKE 'PHYS%' THEN 'Physics'
                    WHEN course LIKE 'ENGL%' THEN 'English'
                    WHEN course LIKE 'HIST%' THEN 'History'
                    WHEN course LIKE 'BUS%' THEN 'Business'
                    WHEN course LIKE 'PSYC%' THEN 'Psychology'
                    WHEN course LIKE 'CHEM%' THEN 'Chemistry'
                    WHEN course LIKE 'BIOL%' THEN 'Biology'
                    WHEN course LIKE 'ART%' THEN 'Art'
                    WHEN course LIKE 'MUSC%' THEN 'Music'
                    WHEN course LIKE 'KIN%' THEN 'Kinesiology'
                    WHEN course LIKE 'COMM%' THEN 'Communication'
                    WHEN course LIKE 'PHIL%' THEN 'Philosophy'
                    WHEN course LIKE 'SOC%' THEN 'Sociology'
                    WHEN course LIKE 'ANTH%' THEN 'Anthropology'
                    WHEN course LIKE 'GEOG%' THEN 'Geography'
                    WHEN course LIKE 'SPAN%' THEN 'Spanish'
                    WHEN course LIKE 'FREN%' THEN 'French'
                    WHEN course LIKE 'JPN%' THEN 'Japanese'
                    WHEN course LIKE 'CHIN%' THEN 'Chinese'
                    WHEN course LIKE 'ACCT%' THEN 'Accounting'
                    ELSE 'Other'
                END as department
            FROM current_exams
            ON CONFLICT (course_code) DO NOTHING
        """)
        conn.commit()
        print("Course catalog created")


def main():
    parser = argparse.ArgumentParser(
        description='Import CSV data into Supabase database')
    parser.add_argument('--connection-string', required=True,
                        help='Supabase database connection string')
    parser.add_argument('--current-exams', default='../SJSU_Sample_Exam_Schedule.csv',
                        help='Path to current exams CSV file')
    parser.add_argument('--historic-exams', default='../Historic_Schedule.csv',
                        help='Path to historic exams CSV file')

    args = parser.parse_args()

    # Connect to database
    conn = connect_to_supabase(args.connection_string)

    try:
        # Import data
        if os.path.exists(args.current_exams):
            import_current_exams(conn, args.current_exams)
        else:
            print(
                f"Warning: Current exams file not found: {args.current_exams}")

        if os.path.exists(args.historic_exams):
            import_historic_exams(conn, args.historic_exams)
        else:
            print(
                f"Warning: Historic exams file not found: {args.historic_exams}")

        # Create course catalog
        create_course_catalog(conn)

        print("Data import completed successfully!")

    except Exception as e:
        print(f"Error during import: {e}")
        conn.rollback()
    finally:
        conn.close()


if __name__ == "__main__":
    main()
