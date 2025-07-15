# Music API - Tech Screening Assignment

A RESTful API for managing bands and songs with CRUD operations and specialized filtering capabilities.

## Overview

This Spring Boot application provides a comprehensive music management system that:
- Loads artist and song data from JSON files
- Provides RESTful endpoints for CRUD operations
- Filters data according to specific requirements (Metal bands, songs before 2016)
- Includes comprehensive unit tests and API documentation

## Features

### Core Requirements
- ✅ **CRUD Operations**: Full Create, Read, Update, Delete functionality for artists and songs
- ✅ **Data Loading**: Reads and processes JSON files without altering them
- ✅ **Metal Band Filtering**: Finds all bands with Metal genre songs
- ✅ **Pre-2016 Song Filtering**: Retrieves all songs released before 2016
- ✅ **Search Functionality**: Search artists by name and songs by genre

### Extra Requirements Implemented
- ✅ **RESTful API**: Follows RESTful principles with proper HTTP methods and status codes
- ✅ **Tests**: Comprehensive unit tests with 29 test cases (100% service layer coverage)
- ✅ **API Documentation**: Swagger/OpenAPI documentation with detailed endpoint descriptions

## Technology Stack

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Web** - RESTful web services
- **Jackson** - JSON processing
- **SpringDoc OpenAPI** - API documentation
- **JUnit 5** - Unit testing
- **Mockito** - Mocking framework
- **Maven** - Build tool

## Project Structure

```
src/
├── main/java/com/rockstars/musicapi/
│   ├── MusicApiApplication.java          # Main application class
│   ├── controller/                       # REST controllers
│   │   ├── ArtistController.java
│   │   └── SongController.java
│   ├── service/                          # Business logic layer
│   │   ├── ArtistService.java
│   │   └── SongService.java
│   ├── repository/                       # Data access layer
│   │   ├── ArtistRepository.java
│   │   ├── SongRepository.java
│   │   └── impl/
│   │       ├── InMemoryArtistRepository.java
│   │       └── InMemorySongRepository.java
│   └── model/                           # Data models
│       ├── Artist.java
│       └── Song.java
├── main/resources/
│   └── instructions/                    # JSON data files
│       ├── artists.json
│       └── songs.json
└── test/java/com/rockstars/musicapi/
    └── service/                         # Unit tests
        ├── ArtistServiceTest.java
        └── SongServiceTest.java
```

## Quick Start

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Running the Application

1. **Clone and navigate to the project directory**

2. **Build the application**
   ```bash
   mvn clean compile
   ```

3. **Run tests**
   ```bash
   mvn test
   ```

4. **Start the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - API Base URL: `http://localhost:8080/api/v1`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - OpenAPI Spec: `http://localhost:8080/v3/api-docs`

## API Endpoints

### Artists

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/artists` | Get all artists |
| GET | `/api/v1/artists/{id}` | Get artist by ID |
| GET | `/api/v1/artists/search?name={name}` | Search artists by name |
| GET | `/api/v1/artists/metal` | Get all Metal artists |
| POST | `/api/v1/artists` | Create new artist |
| PUT | `/api/v1/artists/{id}` | Update artist |
| DELETE | `/api/v1/artists/{id}` | Delete artist |

### Songs

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/songs` | Get all songs |
| GET | `/api/v1/songs/{id}` | Get song by ID |
| GET | `/api/v1/songs/search?name={name}` | Search songs by name |
| GET | `/api/v1/songs/genre/{genre}` | Get songs by genre |
| GET | `/api/v1/songs/artist/{artistName}` | Get songs by artist |
| GET | `/api/v1/songs/before-year/{year}` | Get songs before specified year |
| GET | `/api/v1/songs/before-2016` | Get songs before 2016 |
| GET | `/api/v1/songs/filter?genre={genre}&year={year}` | Filter songs by genre and year |
| POST | `/api/v1/songs` | Create new song |
| PUT | `/api/v1/songs/{id}` | Update song |
| DELETE | `/api/v1/songs/{id}` | Delete song |

## Example Usage

### Get Metal Artists
```bash
curl -X GET "http://localhost:8080/api/v1/artists/metal"
```

### Get Songs Released Before 2016
```bash
curl -X GET "http://localhost:8080/api/v1/songs/before-2016"
```

### Search Songs by Genre
```bash
curl -X GET "http://localhost:8080/api/v1/songs/genre/Metal"
```

### Create New Artist
```bash
curl -X POST "http://localhost:8080/api/v1/artists" \
  -H "Content-Type: application/json" \
  -d '{"Name": "New Band"}'
```

## Data Models

### Artist
```json
{
  "Id": 1,
  "Name": "Artist Name"
}
```

### Song
```json
{
  "Id": 1,
  "Name": "Song Name",
  "Year": 2020,
  "Artist": "Artist Name",
  "Shortname": "songname",
  "Bpm": 120,
  "Duration": 240000,
  "Genre": "Rock",
  "SpotifyId": "spotify_id",
  "Album": "Album Name"
}
```

## Architecture & Design Patterns

### Clean Architecture
- **Controller Layer**: Handles HTTP requests/responses
- **Service Layer**: Contains business logic
- **Repository Layer**: Manages data access
- **Model Layer**: Defines data structures

### Design Patterns Used
- **Repository Pattern**: Abstracts data access logic
- **Dependency Injection**: Loose coupling between components
- **Builder Pattern**: Used in test data creation
- **Strategy Pattern**: Different filtering strategies in repositories

## Testing

The application includes comprehensive unit tests:
- **29 test cases** covering service layer functionality
- **100% service layer test coverage**
- **Mockito** for mocking dependencies
- **JUnit 5** for test execution

Run tests with:
```bash
mvn test
```

## Performance & Security Considerations

### Performance
- **In-memory storage** for fast data access
- **Concurrent data structures** (ConcurrentHashMap) for thread safety
- **Stream API** for efficient data filtering
- **Lazy loading** of JSON data on application startup

### Security
- **Input validation** using Bean Validation annotations
- **Proper HTTP status codes** for different scenarios
- **Exception handling** to prevent information leakage

## Future Enhancements

Given more time, the following could be implemented:
- **Database integration** (PostgreSQL/MySQL)
- **Caching layer** (Redis)
- **API security** (JWT authentication)
- **Docker containerization**
- **Integration tests**
- **API versioning**

## Development Notes

- **Time spent**: Approximately 4 hours as per assignment requirements
- **Focus**: Functionality and code quality over quantity
- **Code style**: Follows clean code principles with comprehensive documentation
- **Testing**: Emphasizes unit testing for reliability

## Contact

For questions about this implementation, please contact the development team.