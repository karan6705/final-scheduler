# SJSU Exam Scheduler

A comprehensive exam scheduling system designed for San Jose State University, serving over 30,000 students. This full-stack application provides an intuitive interface for managing and viewing exam schedules.

## 🚀 Features

- **📅 Exam Management**: Add, edit, delete, and view exam schedules
- **🔍 Advanced Search**: Search exams by course name or multiple courses
- **📱 Mobile Responsive**: Works seamlessly on desktop and mobile devices
- **📊 Historical Data**: View exam schedules from previous semesters
- **📅 Calendar Integration**: Add exams to personal calendar and export them
- **🎨 Modern UI**: Beautiful and intuitive user interface

## 🛠️ Tech Stack

### Backend

- **Spring Boot 3.2.1** - Java-based RESTful API
- **Spring Data JPA** - Database operations
- **H2 Database** - In-memory database for local development
- **PostgreSQL** - Production database
- **Maven** - Dependency management

### Frontend

- **React 18.2.0** - Modern JavaScript framework
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls
- **Sass** - CSS preprocessor
- **FontAwesome** - Icon library

## 📋 Prerequisites

Before running this project locally, ensure you have:

- **Java Development Kit (JDK) 21** or higher
- **Node.js 18** and npm (Node Package Manager)
- **Git** for version control
- **IDE** (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone git@github.com:karan6705/final-scheduler.git
cd final-scheduler
```

### 2. Backend Setup

Navigate to the backend directory and start the Spring Boot application:

```bash
cd Backend-SpringBoot/exam-scheduler
./mvnw spring-boot:run
```

The backend will start on **http://localhost:8080**

### 3. Frontend Setup

In a new terminal, navigate to the frontend directory and install dependencies:

```bash
cd Frontend-ReactJS
npm install
npm start
```

The frontend will start on **http://localhost:3000**

## 📚 API Endpoints

The backend provides the following RESTful API endpoints:

| Method | Endpoint                          | Description                           |
| ------ | --------------------------------- | ------------------------------------- |
| GET    | `/api/v1/exam`                    | Get all exams or filter by class name |
| GET    | `/api/v1/exam/multiple`           | Get multiple exams by course names    |
| POST   | `/api/v1/exam`                    | Add a new exam                        |
| PUT    | `/api/v1/exam`                    | Update an existing exam               |
| DELETE | `/api/v1/exam`                    | Delete an exam by course and section  |
| GET    | `/api/v1/historic-exams/historic` | Get historical exam data              |

## 🗄️ Database Schema

The application uses two main tables:

- **`w2024`** - Current semester exam schedule
- **`historic_exams`** - Historical exam data from previous semesters

## 🔧 Configuration

### Local Development

The application is configured to use H2 in-memory database for local development:

- **Database URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`
- **H2 Console**: http://localhost:8080/h2-console

### Production

For production deployment, the application can be configured to use PostgreSQL by updating the `application.properties` file.

## 📁 Project Structure

```
SJSU-Scheduler/
├── Backend-SpringBoot/
│   └── exam-scheduler/
│       ├── src/main/java/com/sjsu/examscheduler/
│       │   ├── controller/     # REST API controllers
│       │   ├── model/         # Data models
│       │   ├── repo/          # Repository interfaces
│       │   └── service/       # Business logic
│       └── src/main/resources/
│           └── application.properties
├── Frontend-ReactJS/
│   ├── src/
│   │   ├── components/        # React components
│   │   ├── assets/           # Images and static files
│   │   └── App.js           # Main application component
│   └── public/
└── README.md
```

## 🚀 Deployment

### Backend Deployment

The Spring Boot application can be deployed to:

- **Heroku** - Using the provided Procfile
- **AWS** - Using Elastic Beanstalk
- **Docker** - Containerized deployment

### Frontend Deployment

The React application can be deployed to:

- **Vercel** - Recommended for React apps
- **Netlify** - Static site hosting
- **GitHub Pages** - Free hosting

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👥 Authors

- **Karan** - _Initial work_ - [karan6705](https://github.com/karan6705)

## 🙏 Acknowledgments

- San Jose State University for the project requirements
- Spring Boot and React communities for excellent documentation
- All contributors who helped improve this project

---

**Note**: This application is designed specifically for San Jose State University's exam scheduling needs and serves over 30,000 students.
