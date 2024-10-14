 HELP.md

# Bookstore.com Web Application

## Overview

This web-based application is developed for **Bookstore.com**, allowing users to add new books and view the collection. Built using **Spring Boot** for the backend and **React** with **Vite** for the frontend, the app ensures optimal performance and scalability. The application meets specific requirements such as duplicate book name prevention, displaying the newest books first, and supporting multiple concurrent users.

---

# 1. Main Components of the Web Application

## 1.1 Overview

The **Bookstore.com Web Application** is composed of three main components: the frontend, the backend, and the database. It is designed to handle concurrent users, with a clean interface for adding books and browsing the catalog.

## 1.2 Main Components

- **Frontend (ReactJS with Vite)**:
    - The frontend is a **ReactJS** single-page application (SPA) that allows users to add new books and view the existing ones. A "Load More" button loads additional books dynamically.
    - **Vite** is used for fast development and optimized builds for production.
    - The frontend communicates with the backend API to manage book data.

- **Backend (Spring Boot WebFlux)**:
    - The backend is built using **Spring Boot** with **WebFlux** to support reactive programming, ensuring efficient handling of concurrent requests.
    - The backend exposes a set of REST APIs for adding books and fetching the book list.

- **Database (H2 with R2DBC)**:
    - The database is an in-memory **H2 database** accessed reactively using **R2DBC**. This ensures that database interactions are non-blocking and fast.
    - Being in-memory, the database is ephemeral and resets with every application restart, ideal for testing and development.

## 1.3 Architecture Reasoning

- **Spring Boot WebFlux** was chosen for its ability to handle multiple concurrent requests efficiently, which is critical for meeting the requirement of handling up to 10 parallel sessions per second.
- **React with Vite** offers a fast development environment with modern JavaScript features, ensuring a smooth user experience.
- **R2DBC** was used to enable reactive interactions with the database, complementing the non-blocking nature of WebFlux, ensuring that the system scales with growing demand.

---

# 2. Setup Guide

## 2.1 Prerequisites

- **Java 21** installed
- **Node.js** and **npm** installed
- **Maven** installed

## 2.2 Backend Setup

1. Clone the repository:
   `git clone https://github.com/GirtsG/bookstore-app.git`
   
2. Navigate to the project directory
   
3. Run the Spring Boot backend:
   `./mvnw spring-boot:run`
   

The backend should now be running at `http://localhost:8080`.

## 2.3 Frontend Setup

1. Navigate to the frontend directory `bookstore-frontend`
   
2. Install dependencies:
   `npm install`
   
3. Run the frontend:
   `npm run dev`
   

The frontend should now be available at `http://localhost:5173`.

## 2.4 Database

The application uses an **in-memory H2 database**, which requires no additional setup. The database is reset each time the application restarts, and the connection details are:

- URL: `r2dbc:h2:mem:///bookstoredb`
- Username: `sa`
- Password: (no password)

---

# 3. Main Application Features

## 3.1 Add New Book

The user interface allows users to add new books. When adding a book, the system ensures that the book name is unique to prevent duplicate entries in the database.

## 3.2 View Existing Books

The main page displays books with the most recent additions at the top. Users can load more books by clicking the "Load More" button, which fetches additional books dynamically.

## 3.3 Performance Optimization

The application is designed to ensure that the main page load time does not exceed 2 seconds, even with multiple parallel sessions running.

---

# 4. Testing Instructions

To test the application:

1. **Add a book**: Use the form on the main page to add a new book.
2. **Prevent duplicate book names**: Try adding a book with an existing name to ensure that duplicates are not allowed.
3. **Verify page load speed**: Test that the main page loads in under 2 seconds.
4. **Concurrent Sessions**: Use a load testing tool like **Apache JMeter** or **Postman** to simulate up to 10 parallel user sessions.

---

# 5. Source Code and Pre-compiled Package

The full source code and instructions for running the application are included in the GitHub repository.

GitHub Repository: [https://github.com/GirtsG/bookstore-app](https://github.com/GirtsG/bookstore-app)

To run the application locally, follow the **Setup Guide** in Section 2.

---

# 6. Conclusion

This web application is built with scalability, performance, and simplicity in mind. By utilizing a reactive stack with Spring WebFlux and ReactJS, the app is well-suited to handle concurrent user requests while providing a smooth user experience.
