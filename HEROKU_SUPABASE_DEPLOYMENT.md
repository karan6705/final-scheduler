# Heroku + Supabase Deployment Guide

## 🚀 Deployment Strategy

- **Backend**: Heroku (Spring Boot)
- **Database**: Supabase (PostgreSQL)
- **Frontend**: Vercel (React) - Already deployed

## 📋 Prerequisites

1. Heroku account (free tier available)
2. Supabase account (free tier available)
3. Git repository ready

## 🗄️ Step 1: Set up Supabase Database

### 1.1 Create Supabase Project

1. Go to [supabase.com](https://supabase.com)
2. Sign up/Login
3. Click "New Project"
4. Choose your organization
5. Enter project details:
   - **Name**: `sjsu-exam-scheduler-db`
   - **Database Password**: Create a strong password
   - **Region**: Choose closest to you
6. Click "Create new project"

### 1.2 Get Database Connection Details

1. Go to **Settings** → **Database**
2. Copy the connection details:
   - **Host**: `db.xxxxxxxxxxxx.supabase.co`
   - **Database name**: `postgres`
   - **Port**: `5432`
   - **User**: `postgres`
   - **Password**: (the one you set)

### 1.3 Create Database Tables

The Spring Boot application will automatically create tables when it starts.

## ☁️ Step 2: Deploy to Heroku

### 2.1 Install Heroku CLI

```bash
# macOS
brew tap heroku/brew && brew install heroku

# Or download from: https://devcenter.heroku.com/articles/heroku-cli
```

### 2.2 Login to Heroku

```bash
heroku login
```

### 2.3 Create Heroku App

```bash
cd Backend-SpringBoot/exam-scheduler
heroku create sjsu-exam-scheduler-backend
```

### 2.4 Set Environment Variables

```bash
# Set Spring profile
heroku config:set SPRING_PROFILES_ACTIVE=heroku

# Set Supabase database connection
heroku config:set DATABASE_URL=jdbc:postgresql://db.xxxxxxxxxxxx.supabase.co:5432/postgres
heroku config:set DATABASE_USERNAME=postgres
heroku config:set DATABASE_PASSWORD=your_supabase_password

# Set Java options
heroku config:set JAVA_OPTS="-Xmx512m -Xms256m"
```

### 2.5 Deploy to Heroku

```bash
# Add Heroku remote
heroku git:remote -a sjsu-exam-scheduler-backend

# Deploy
git add -A
git commit -m "Deploy to Heroku with Supabase"
git push heroku main
```

### 2.6 Verify Deployment

```bash
# Check logs
heroku logs --tail

# Open the app
heroku open
```

## 🔗 Step 3: Connect Frontend to Backend

### 3.1 Update Frontend Environment

1. Go to your Vercel dashboard
2. Find your project: `sjsu-exam-scheduler`
3. Go to **Settings** → **Environment Variables**
4. Add:
   - **Name**: `REACT_APP_API_URL`
   - **Value**: `https://sjsu-exam-scheduler-backend.herokuapp.com`
5. Redeploy the frontend

### 3.2 Test the Connection

1. Visit your Vercel frontend: `https://sjsu-exam-scheduler.vercel.app`
2. Test the search functionality
3. Check browser console for any CORS errors

## 🔧 Troubleshooting

### Common Issues:

1. **Database Connection**: Make sure Supabase credentials are correct
2. **CORS Errors**: Check that the frontend URL is in the allowed origins
3. **Build Failures**: Check Heroku logs with `heroku logs --tail`

### Useful Commands:

```bash
# Check Heroku app status
heroku ps

# View logs
heroku logs --tail

# Restart app
heroku restart

# Check config vars
heroku config
```

## 📊 Monitoring

- **Heroku**: Use Heroku dashboard for app metrics
- **Supabase**: Use Supabase dashboard for database monitoring
- **Vercel**: Use Vercel dashboard for frontend analytics

## 🔄 Updates

To update the application:

1. Make changes to your code
2. Commit and push to GitHub
3. Deploy to Heroku: `git push heroku main`
4. Vercel will auto-deploy from GitHub

## 💰 Cost Estimation

- **Heroku**: Free tier (sleeps after 30 min inactivity)
- **Supabase**: Free tier (500MB database, 2GB bandwidth)
- **Vercel**: Free tier (already deployed)

Total cost: **$0/month** (free tier)
