# SJSU Exam Scheduler

This project is a comprehensive exam scheduler designed for San Jose State University, benefiting over 30,000 students. It utilizes a PostgreSQL database to store all exam-related information, a Spring Boot application to create a RESTful API for the backend, and a ReactJS frontend for intuitive user interaction.

## Features

- **PostgreSQL Database:** Stores detailed information about exams, including dates, times, courses, locations, and more.
- **Spring Boot Backend:** Provides a robust RESTful API to manage exam data efficiently. The backend is packaged as a Dockerfile and hosted on Heroku.
- **ReactJS Frontend:** A user-friendly interface for viewing, adding, editing, and deleting exam information. The frontend is hosted on Vercel.
- **Search Functionality:** Search for exams by course name or multiple courses at once.
- **Calendar Integration:** Add exams to your personal calendar and export them.
- **Historical Data:** View exam schedules from previous semesters.
- **Mobile Responsive:** Works seamlessly on desktop and mobile devices.

## Prerequisites

Before running this project locally, ensure you have the following installed:

- Java Development Kit (JDK) 21 or higher
- Node.js and npm (Node Package Manager)
- PostgreSQL database
- IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## Installation

### Backend Setup

1. Clone this repository.
2. Navigate to the `Backend (SpringBoot)/exam-scheduler` directory.
3. Configure the `application.properties` file in the `src/main/resources` directory with your PostgreSQL database credentials.
4. Run the Spring Boot application using:
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend Setup

1. Navigate to the `Frontend (ReactJS)` directory in your terminal.
2. Run `npm install` to install the necessary dependencies.
3. Update the API endpoints in the components to point to your backend URL.
4. Run `npm start` to start the ReactJS application.

## API Endpoints

The backend provides the following RESTful API endpoints:

- `GET /api/v1/exam` - Get all exams or filter by class name
- `GET /api/v1/exam/multiple` - Get multiple exams by course names
- `POST /api/v1/exam` - Add a new exam
- `PUT /api/v1/exam` - Update an existing exam
- `DELETE /api/v1/exam` - Delete an exam by course and section
- `GET /api/v1/historic-exams/historic` - Get historical exam data

## Database Schema

The application uses two main tables:

- `w2024` - Current semester exam schedule
- `historic_exams` - Historical exam data from previous semesters

## Usage

- Access the frontend application via `http://localhost:3000`.
- Use the search functionality to find exams by course name.
- Add exams to your calendar and export them.
- View historical exam schedules for planning purposes.

## Contributing

Contributions are welcome! If you'd like to enhance this project or report issues, please submit a pull request or open an issue.

## License

This project is open source and available under the MIT License.
