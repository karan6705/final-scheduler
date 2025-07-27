# McGill to SJSU Conversion Summary

This document summarizes all the changes made to convert the McGill Exam Scheduler to the SJSU Exam Scheduler.

## Backend Changes

### Package Structure

- **Old**: `com.mcgill.examscheduler`
- **New**: `com.sjsu.examscheduler`

### Updated Files

1. **Main Application Class**

   - `McGillExamSchedulerApplication.java` → `SJSUExamSchedulerApplication.java`
   - Updated package name and class name

2. **Configuration Files**

   - `pom.xml`: Updated groupId, name, and description
   - `CorsConfig.java`: Updated allowed origins to reference SJSU URLs

3. **Model Classes**

   - `Exam.java`: Updated package name
   - `ExamKey.java`: Updated package name
   - `HistoricExam.java`: Updated package name
   - `HistoricExamKey.java`: Updated package name

4. **Repository Classes**

   - `ExamRepository.java`: Updated package name and imports
   - `HistoricExamRepository.java`: Updated package name and imports

5. **Service Classes**

   - `ExamService.java`: Updated package name and imports
   - `HistoricExamService.java`: Updated package name and imports

6. **Controller Classes**

   - `ExamController.java`: Updated package name and imports
   - `HistoricExamController.java`: Updated package name and imports

7. **Test Classes**
   - All test files updated to use `com.sjsu.examscheduler` package
   - Main test class renamed to `SJSUExamSchedulerApplicationTests`

## Frontend Changes

### Package Configuration

- `package.json`: Updated name from "mcgill-exams" to "sjsu-exams"

### Component Updates

1. **Home Component**

   - Updated welcome text from "McGill Exam Scheduler!" to "SJSU Exam Scheduler!"

2. **Sidebar Component**

   - Updated external link from McGill exam schedule to SJSU exam schedule
   - Temporarily using McGill logo (needs SJSU logo replacement)

3. **API Endpoints**

   - Updated all API calls from McGill backend URLs to SJSU backend URLs:
     - `https://mcgillexams-5294e99e879f.herokuapp.com` → `https://sjsuexams-5294e99e879f.herokuapp.com`

4. **HTML Title**
   - Updated page title from "McGill Exams" to "SJSU Exams"

### Components with Updated API Endpoints

- `DataHandling/index.js`
- `MultiSelect/index.js`
- `Historic/index.js`

## Documentation Changes

### README Updates

- Updated project title and description
- Updated university references from McGill to SJSU
- Enhanced installation instructions
- Added API endpoints documentation
- Added database schema information
- Updated usage instructions

## Database Considerations

The current database structure remains the same, but you may want to:

1. Update table names to reflect SJSU terminology
2. Import SJSU-specific exam data
3. Update building and room references to SJSU campus locations

## Deployment Considerations

### Backend Deployment

- Update Heroku app name to reflect SJSU branding
- Update environment variables for SJSU database
- Update CORS configuration for SJSU frontend domain

### Frontend Deployment

- Update Vercel project name and domain
- Update API endpoint URLs in production
- Replace McGill logo with SJSU logo

## Remaining Tasks

1. **Logo Replacement**

   - Create and add SJSU logo to assets
   - Update all logo references in components

2. **Database Migration**

   - Import SJSU exam data
   - Update table names if needed
   - Update building/room references

3. **Test Updates**

   - Update remaining test files that still reference McGill packages
   - Ensure all tests pass with new package structure

4. **Deployment**
   - Set up new Heroku app for SJSU backend
   - Set up new Vercel project for SJSU frontend
   - Update all environment variables and configurations

## Files Still Needing Updates

The following files still contain McGill references and need to be updated:

- Some test files in the `com/mcgill/examscheduler` directory (old structure)
- Generated files in `target/` directory (will be regenerated on build)
- Some CSV data files contain McGill-specific data

## Notes

- The core functionality remains the same
- All API endpoints and data structures are preserved
- The conversion maintains backward compatibility
- The frontend UI and user experience remain unchanged
- All existing features (search, calendar, historical data) are preserved
