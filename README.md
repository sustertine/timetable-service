# Timetable Service

The Timetable Service is a Spring Boot application written in Java. It provides a RESTful API for managing timetables and their entries.

## Features

- Create, update, and delete timetables.
- Add and remove entries from a timetable.
- Retrieve all timetables or a specific timetable by its ID.
- Retrieve all entries of a specific timetable.

## Endpoints

- `POST /timetables`: Create a new timetable.
- `GET /timetables`: Get all timetables.
- `GET /timetables/{id}`: Get a specific timetable by its ID.
- `PUT /timetables/{id}`: Update a specific timetable by its ID.
- `DELETE /timetables/{id}`: Delete a specific timetable by its ID.
- `POST /timetables/{id}/entries`: Add an entry to a specific timetable.
- `DELETE /timetables/{id}/entries/{entryId}`: Delete an entry from a specific timetable.
- `GET /timetable-entries/{id}`: Get a specific timetable entry by its ID.
- `PUT /timetable-entries/{id}`: Update a specific timetable entry by its ID.

## Database Configuration

The service uses PostgreSQL as its database. The configuration is specified in the `application.properties` file:

```properties example
spring.datasource.url=jdbc:postgresql://localhost:5432/timetable
spring.datasource.username=postgres
spring.datasource.password=admin
```

You need to have a PostgreSQL server running somewhere. 
The database name should be `timetable`, and the username and password should be `postgres` and `admin`, respectively. 
The application will automatically create the necessary tables on startup if they do not exist.