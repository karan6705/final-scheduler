# SJSU Exam Scheduler - Deployment Guide

This guide will help you deploy the SJSU Exam Scheduler to production environments.

## Prerequisites

- Heroku account (for backend deployment)
- Vercel account (for frontend deployment)
- PostgreSQL database (can use Heroku Postgres)
- Git repository access

## Backend Deployment (Heroku)

### 1. Create Heroku App

```bash
# Install Heroku CLI if not already installed
# Create new Heroku app
heroku create sjsu-exam-scheduler-backend

# Set Java buildpack
heroku buildpacks:set heroku/java
```

### 2. Configure Environment Variables

```bash
# Set database URL (replace with your PostgreSQL URL)
heroku config:set SPRING_DATASOURCE_URL=your_postgresql_url
heroku config:set SPRING_DATASOURCE_USERNAME=your_username
heroku config:set SPRING_DATASOURCE_PASSWORD=your_password

# Set CORS origins for frontend
heroku config:set CORS_ORIGINS=https://sjsu-exam-scheduler.vercel.app
```

### 3. Deploy Backend

```bash
# Add Heroku remote
git remote add heroku https://git.heroku.com/sjsu-exam-scheduler-backend.git

# Deploy to Heroku
git push heroku main
```

### 4. Verify Backend Deployment

```bash
# Check app status
heroku ps

# View logs
heroku logs --tail

# Test API endpoints
curl https://sjsu-exam-scheduler-backend.herokuapp.com/api/v1/exam
```

## Frontend Deployment (Vercel)

### 1. Prepare Frontend for Deployment

Update API endpoints in the frontend components to point to your Heroku backend:

```javascript
// Update these URLs in your components
const API_BASE_URL = "https://sjsu-exam-scheduler-backend.herokuapp.com";
```

### 2. Deploy to Vercel

```bash
# Install Vercel CLI
npm i -g vercel

# Login to Vercel
vercel login

# Deploy
vercel --prod
```

### 3. Configure Custom Domain (Optional)

- Go to Vercel dashboard
- Add custom domain: `sjsu-exam-scheduler.vercel.app`
- Update DNS settings as required

## Database Setup

### 1. Create Database Tables

```sql
-- Create main exam table
CREATE TABLE sjsu_exams (
    course VARCHAR(255) NOT NULL,
    section VARCHAR(255) NOT NULL,
    course_title VARCHAR(500),
    exam_type VARCHAR(255),
    exam_start_time TIMESTAMP,
    exam_end_time TIMESTAMP,
    building VARCHAR(255),
    room VARCHAR(255),
    rows_from VARCHAR(255),
    row_start VARCHAR(255),
    row_end VARCHAR(255),
    PRIMARY KEY (course, section)
);

-- Create historic exams table
CREATE TABLE historic_exams (
    course VARCHAR(255) NOT NULL,
    section VARCHAR(255) NOT NULL,
    year VARCHAR(255) NOT NULL,
    exam_type VARCHAR(255),
    exam_start_time VARCHAR(255),
    exam_end_time VARCHAR(255),
    building VARCHAR(255),
    room VARCHAR(255),
    rows_from VARCHAR(255),
    row_start VARCHAR(255),
    row_end VARCHAR(255),
    PRIMARY KEY (course, section, year)
);
```

### 2. Import SJSU Exam Data

```bash
# Import the sample data
psql your_database_url -c "\COPY sjsu_exams FROM 'SJSU_Sample_Exam_Schedule.csv' WITH (FORMAT csv, HEADER true);"
```

## Environment Configuration

### Backend Environment Variables

```properties
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:postgresql://your_db_host:5432/your_db_name
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password

# CORS Configuration
CORS_ORIGINS=https://sjsu-exam-scheduler.vercel.app,http://localhost:3000

# Server Configuration
SERVER_PORT=8080
```

### Frontend Environment Variables

```bash
# Create .env file in frontend directory
REACT_APP_API_BASE_URL=https://sjsu-exam-scheduler-backend.herokuapp.com
REACT_APP_SJSU_LOGO_URL=/assets/sjsu-logo.png
```

## Testing Deployment

### 1. Test Backend API

```bash
# Test exam retrieval
curl https://sjsu-exam-scheduler-backend.herokuapp.com/api/v1/exam

# Test specific course search
curl "https://sjsu-exam-scheduler-backend.herokuapp.com/api/v1/exam?className=CS%2046A"
```

### 2. Test Frontend

- Visit your Vercel deployment URL
- Test search functionality
- Test calendar integration
- Test historical data access

## Monitoring and Maintenance

### 1. Set Up Monitoring

```bash
# Enable Heroku add-ons for monitoring
heroku addons:create papertrail:choklad
heroku addons:create newrelic:wayne
```

### 2. Database Backup

```bash
# Set up automated backups
heroku addons:create heroku-postgresql:basic
heroku pg:backups schedule DATABASE_URL --at '02:00 UTC'
```

### 3. Performance Monitoring

- Monitor API response times
- Track database query performance
- Monitor frontend load times
- Set up alerts for downtime

## Troubleshooting

### Common Issues

1. **CORS Errors**

   - Verify CORS configuration in `CorsConfig.java`
   - Check that frontend URL is in allowed origins

2. **Database Connection Issues**

   - Verify database credentials
   - Check database URL format
   - Ensure database is accessible from Heroku

3. **Build Failures**

   - Check Java version compatibility
   - Verify all dependencies are in `pom.xml`
   - Check for compilation errors

4. **Frontend Build Issues**
   - Verify all dependencies are installed
   - Check for JavaScript errors
   - Ensure environment variables are set

## Security Considerations

1. **Database Security**

   - Use strong passwords
   - Enable SSL connections
   - Regular security updates

2. **API Security**

   - Implement rate limiting
   - Add authentication if needed
   - Validate all inputs

3. **Frontend Security**
   - Use HTTPS only
   - Implement Content Security Policy
   - Regular dependency updates

## Cost Optimization

### Heroku Costs

- Use hobby dyno for development
- Scale up only when needed
- Monitor usage regularly

### Vercel Costs

- Free tier is usually sufficient
- Monitor bandwidth usage
- Optimize bundle size

## Support and Documentation

- Keep deployment documentation updated
- Document any custom configurations
- Maintain runbooks for common issues
- Set up monitoring and alerting

## Next Steps

1. **Custom Domain Setup**

   - Configure custom domain for production
   - Set up SSL certificates
   - Update DNS records

2. **Performance Optimization**

   - Implement caching strategies
   - Optimize database queries
   - Add CDN for static assets

3. **Feature Enhancements**
   - Add user authentication
   - Implement admin panel
   - Add real-time notifications
