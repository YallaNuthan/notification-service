# Real-Time Notification & Event Processing System

A Spring Boot backend system for event-driven notification processing with caching, scheduling, and global exception handling.

## Tech Stack
Java 25 | Spring Boot 3.5 | Spring Data JPA | Spring Cache | H2 | REST API | Scheduling | Maven

## Features
- Publish and process real-time events (DEPLOYMENT, BUILD_FAILURE, CODE_REVIEW, RELEASE)
- Auto-generates notifications from events
- In-memory caching with Spring Cache
- Scheduled auto-processing of pending notifications every 60 seconds
- Global exception handling with structured error responses
- Event stats and notification summary endpoints

## API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/events | Publish new event |
| GET | /api/events | Get all events |
| GET | /api/events/type?name= | Filter by event type |
| GET | /api/events/stats | Event statistics |
| GET | /api/notifications | Get all notifications |
| POST | /api/notifications/process | Process pending notifications |
| GET | /api/notifications/summary | Notification summary |

## Run Locally
mvnw.cmd spring-boot:run

Server starts at http://localhost:8080
