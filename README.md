
# Mini Quiz Application

## Overview
The **Mini Quiz Application** is a Java-based application where users can take quizzes, track their scores, and choose from a variety of quiz categories. The app also includes an admin panel where administrators can add, edit, or delete quiz questions. 

## App
##### login layout
![table_page](https://github.com/Crepopcorn/personal_budget_tracker/blob/main/layout-applogin.jpg)

##### quiz answer layout
![table_page](https://github.com/Crepopcorn/personal_budget_tracker/blob/main/layout-appquiz.jpg)

## Features
- **Take Quizzes**: Users can attempt quizzes from different categories such as Science, Math, or History.
- **Track Scores**: After each quiz, users can see their score and get feedback on their answers.
- **Admin Panel**: Admins can manage quizzes by adding, removing, or updating questions.
- **User-friendly Interface**: The app is designed with a simple and intuitive interface to ensure ease of use.
- **Data Persistence**: Quiz data is stored in a MySQL database.

## Tools & Technologies
- **Programming Language**: Java
- **GUI**: AWT (Abstract Window Toolkit)
- **Backend**: Spring Boot
- **Database**: MySQL

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/crepopcorn/online-quiz-application.git
   ```

2. Make sure MySQL is installed and configured. Create a new database for the quiz application by running the following SQL command:
   ```sql
   CREATE DATABASE quiz_app;
   ```

3. Open the `DatabaseConnection.java` file and update it with your MySQL credentials (username, password, and database name).

4. Run the following SQL script to create the quizzes table:
   ```sql
   CREATE TABLE quizzes (
       id INT AUTO_INCREMENT PRIMARY KEY,
       category VARCHAR(50),
       question TEXT,
       answer VARCHAR(255)
   );

    CREATE TABLE users (
      id INT AUTO_INCREMENT PRIMARY KEY,
      username VARCHAR(50) UNIQUE NOT NULL,
      password VARCHAR(100) NOT NULL
   );
   ```

5. Open the project in your preferred IDE (like VS Code or IntelliJ), and run the `Main.java` file to launch the application.

## Requirements
- Java 8 or above
- MySQL Server
- Spring Boot for backend
