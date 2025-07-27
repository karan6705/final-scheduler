# AWS Elastic Beanstalk Deployment Guide

## Prerequisites

1. **AWS CLI** - Install and configure

   ```bash
   # Install AWS CLI
   curl "https://awscli.amazonaws.com/AWSCLIV2.pkg" -o "AWSCLIV2.pkg"
   sudo installer -pkg AWSCLIV2.pkg -target /

   # Configure AWS CLI
   aws configure
   ```

2. **EB CLI** - Install Elastic Beanstalk CLI

   ```bash
   pip install awsebcli
   ```

3. **Java 21** - Make sure you have Java 21 installed locally

## Configuration Files

The application is configured with the following files:

- `.ebextensions/01_environment.config` - Environment variables
- `.ebextensions/02_java.config` - Java configuration
- `src/main/resources/application-aws.properties` - AWS-specific Spring Boot configuration

## Deployment Steps

### Step 1: Build the Application

```bash
cd "SJSU-Scheduler/Backend-SpringBoot/exam-scheduler"
./mvnw clean package -DskipTests
```

### Step 2: Initialize EB CLI (First time only)

```bash
eb init
```

- Select your region
- Create new application: `sjsu-exam-scheduler`
- Select Java platform
- Choose to use CodeCommit (optional)

### Step 3: Create Environment (First time only)

```bash
eb create sjsu-exam-scheduler-env
```

- This will create a new environment with the application

### Step 4: Deploy

```bash
eb deploy
```

## Environment Variables

The application uses these environment variables (set in `.ebextensions/01_environment.config`):

- `SPRING_PROFILES_ACTIVE=aws` - Uses AWS configuration
- `SERVER_PORT=5000` - Application port

## Database Configuration

### Option 1: Use H2 (In-Memory) - For Testing

The application is configured to use H2 database by default for testing.

### Option 2: Use AWS RDS - For Production

To use PostgreSQL on AWS RDS:

1. Create an RDS instance in AWS Console
2. Update environment variables in `.ebextensions/01_environment.config`:

```yaml
option_settings:
  aws:elasticbeanstalk:application:environment:
    SPRING_PROFILES_ACTIVE: aws
    SERVER_PORT: 5000
    RDS_HOSTNAME: your-rds-endpoint.amazonaws.com
    RDS_USERNAME: your_username
    RDS_PASSWORD: your_password
    RDS_DRIVER: org.postgresql.Driver
    RDS_DIALECT: org.hibernate.dialect.PostgreSQLDialect
```

## Useful Commands

```bash
# View logs
eb logs

# Open application in browser
eb open

# Check environment status
eb status

# SSH into environment
eb ssh

# View configuration
eb config

# Terminate environment
eb terminate sjsu-exam-scheduler-env
```

## API Endpoints

After deployment, your API will be available at:

- `https://your-eb-url.elasticbeanstalk.com/api/v1/exam`

## Troubleshooting

1. **Port Issues**: The application is configured to run on port 5000 for AWS
2. **Java Version**: Updated to Java 21 in configuration
3. **Database**: Uses H2 by default, can be configured for RDS
4. **CORS**: Configured to allow all origins for development

## Security Considerations

1. **CORS**: Update CORS settings for production
2. **Database**: Use RDS with proper security groups
3. **Environment Variables**: Store sensitive data in AWS Systems Manager Parameter Store
4. **HTTPS**: Configure SSL certificate for production

## Cost Optimization

- Use t3.micro or t3.small instances for development
- Consider using Spot instances for cost savings
- Monitor usage with AWS CloudWatch
