# Supabase Database Setup Guide

This guide will help you set up the SJSU Exam Scheduler database on Supabase.

## 🚀 Quick Setup

### 1. Create Supabase Project

1. Go to [https://supabase.com](https://supabase.com)
2. Sign up or log in to your account
3. Click "New Project"
4. Choose your organization
5. Enter project details:
   - **Name**: `sjsu-exam-scheduler`
   - **Database Password**: Create a strong password
   - **Region**: Choose closest to your users
6. Click "Create new project"

### 2. Set Up Database Schema

1. Go to your Supabase project dashboard
2. Navigate to **SQL Editor** in the left sidebar
3. Create a new query and paste the contents of `schema.sql`
4. Click "Run" to execute the schema

### 3. Import Data

1. In the SQL Editor, create another new query
2. Paste the contents of `import_data.sql`
3. Click "Run" to populate the database with exam data

### 4. Configure Row Level Security (RLS)

For production use, you should enable Row Level Security:

```sql
-- Enable RLS on all tables
ALTER TABLE current_exams ENABLE ROW LEVEL SECURITY;
ALTER TABLE historic_exams ENABLE ROW LEVEL SECURITY;
ALTER TABLE course_catalog ENABLE ROW LEVEL SECURITY;
ALTER TABLE buildings ENABLE ROW LEVEL SECURITY;

-- Create policies for read access
CREATE POLICY "Allow read access to current_exams" ON current_exams FOR SELECT USING (true);
CREATE POLICY "Allow read access to historic_exams" ON historic_exams FOR SELECT USING (true);
CREATE POLICY "Allow read access to course_catalog" ON course_catalog FOR SELECT USING (true);
CREATE POLICY "Allow read access to buildings" ON buildings FOR SELECT USING (true);
```

### 5. Get Connection Details

1. Go to **Settings** → **Database** in your Supabase dashboard
2. Copy the following details:
   - **Database URL**
   - **API Key (anon/public)**
   - **API Key (service_role)**

## 🔧 Backend Configuration

Update your Spring Boot application to use Supabase:

### 1. Update `application.properties`

```properties
# Supabase Database Configuration
spring.datasource.url=jdbc:postgresql://db.[YOUR-PROJECT-REF].supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=[YOUR-DATABASE-PASSWORD]
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080

# CORS Configuration
spring.web.cors.allowed-origins=https://final-scheduler.vercel.app,http://localhost:3000
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
```

### 2. Update Frontend API Configuration

In your React app, update the API base URL to point to your deployed backend:

```javascript
// src/config/api.js
const API_BASE_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";

export const API_ENDPOINTS = {
  EXAMS: `${API_BASE_URL}/api/v1/exam`,
  HISTORIC_EXAMS: `${API_BASE_URL}/api/v1/historic-exams/historic`,
  SEARCH: `${API_BASE_URL}/api/v1/exam/search`,
};
```

## 📊 Database Schema Overview

### Tables

1. **`current_exams`** - Current semester exam schedule
2. **`historic_exams`** - Historical exam data from previous semesters
3. **`course_catalog`** - Course information for better search
4. **`buildings`** - Building information and locations

### Views

1. **`exam_schedule`** - Combined view of current and historic exams
2. **`search_exams()`** - Function for searching exams

### Indexes

- Full-text search indexes for course names and titles
- Performance indexes on frequently queried columns
- Composite indexes for complex queries

## 🔍 API Endpoints

Your backend will provide these endpoints:

| Method | Endpoint                               | Description                 |
| ------ | -------------------------------------- | --------------------------- |
| GET    | `/api/v1/exam`                         | Get all current exams       |
| GET    | `/api/v1/exam/search?q={term}`         | Search exams by course name |
| GET    | `/api/v1/exam/multiple?courses={list}` | Get multiple exams          |
| POST   | `/api/v1/exam`                         | Add new exam                |
| PUT    | `/api/v1/exam`                         | Update exam                 |
| DELETE | `/api/v1/exam`                         | Delete exam                 |
| GET    | `/api/v1/historic-exams/historic`      | Get historical exams        |
| GET    | `/api/v1/courses`                      | Get course catalog          |
| GET    | `/api/v1/buildings`                    | Get building information    |

## 🚀 Deployment Steps

### 1. Deploy Backend to Heroku/Railway/Render

1. Create a new project on your chosen platform
2. Connect your GitHub repository
3. Set environment variables:
   ```
   DATABASE_URL=your_supabase_connection_string
   SPRING_PROFILES_ACTIVE=production
   ```
4. Deploy the application

### 2. Update Frontend Environment Variables

In your Vercel project settings, add:

```
REACT_APP_API_URL=https://your-backend-url.com
```

### 3. Test the Application

1. Visit your deployed frontend: [https://final-scheduler.vercel.app](https://final-scheduler.vercel.app)
2. Test search functionality
3. Verify exam data is loading correctly
4. Test historical data access

## 🔒 Security Considerations

1. **Enable RLS** on all tables for production
2. **Use environment variables** for sensitive data
3. **Implement proper authentication** if needed
4. **Regular backups** of your Supabase database
5. **Monitor usage** and costs

## 📈 Performance Optimization

1. **Database Indexes** - Already configured in schema
2. **Connection Pooling** - Configure in application.properties
3. **Caching** - Consider Redis for frequently accessed data
4. **CDN** - Use Vercel's CDN for static assets

## 🛠️ Troubleshooting

### Common Issues

1. **Connection Refused**

   - Check database URL and credentials
   - Verify Supabase project is active

2. **CORS Errors**

   - Update CORS configuration in backend
   - Check frontend API URL

3. **Data Not Loading**

   - Verify data import was successful
   - Check database permissions

4. **Search Not Working**
   - Verify full-text search indexes are created
   - Check search function implementation

### Useful Queries

```sql
-- Check if data was imported correctly
SELECT COUNT(*) FROM current_exams;
SELECT COUNT(*) FROM historic_exams;
SELECT COUNT(*) FROM course_catalog;

-- Test search functionality
SELECT * FROM search_exams('CS 46A');

-- Check exam statistics
SELECT * FROM get_exam_statistics();
```

## 📞 Support

If you encounter issues:

1. Check Supabase logs in the dashboard
2. Review application logs
3. Test database connections
4. Verify environment variables

## 🎯 Next Steps

1. **Deploy backend** to a cloud platform
2. **Update frontend** to use production API
3. **Test thoroughly** with real data
4. **Monitor performance** and usage
5. **Add authentication** if needed
6. **Implement real-time updates** using Supabase subscriptions

---

**Note**: This setup provides a production-ready database for your SJSU Exam Scheduler application. The schema is optimized for search performance and includes comprehensive exam data for both current and historical semesters.
