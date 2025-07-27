#!/bin/bash

# AWS Elastic Beanstalk Deployment Script
# Make sure you have AWS CLI and EB CLI installed

echo "Building the application..."
./mvnw clean package -DskipTests

echo "Creating deployment package..."
mkdir -p .ebextensions
cp -r .ebextensions/* .ebextensions/

echo "Deploying to AWS Elastic Beanstalk..."
echo "Make sure you have:"
echo "1. AWS CLI configured (aws configure)"
echo "2. EB CLI installed (pip install awsebcli)"
echo "3. Created an EB application and environment"

echo ""
echo "To deploy, run these commands:"
echo "1. eb init (if not already initialized)"
echo "2. eb create sjsu-exam-scheduler-env (if environment doesn't exist)"
echo "3. eb deploy"
echo ""
echo "Or to deploy to existing environment:"
echo "eb deploy"

echo ""
echo "After deployment, you can:"
echo "- View logs: eb logs"
echo "- Open the application: eb open"
echo "- Check status: eb status" 