## About

It is a simple HTTP server with a JSON API, written from scratch in Java. The API allows managing products and categories (CRUD operations).  
The project was developed as the final project for the "Java Developer. Basic" course at Otus.

### Features

- Multithreaded request handling using a thread pool;
- PostgreSQL database running in Docker;
- HikariCP for database connection pooling;
- Flyway for database migrations;
- Simple layered architecture (Controller > Service > Repository);
- Log4j2 for logging;
- External configuration via `application.properties`;