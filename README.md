# Overview
This is a Java project built with Spring Boot, MongoDB, and Docker Compose. The project provides a set of RESTful APIs for scheduling events and listing schedules for a class/room. It prevents scheduling two overlapping events for the same class. a ReadWriteLock was used to prevent two transactions happening at the same time

# Prerequisites

Make sure you have the following installed on your machine:

- Java Development Kit (JDK)
- Docker and Docker Compose
- Maven

# Getting Started

Clone this repository to your local machine:
```bash
git clone https://github.com/josephbitar/EventScheduler.git
```

# API Endpoints

- Schedule Event: </br>
Endpoint: POST /api/v1.0/schedules </br>
Schedule a new event. </br>
Request Body:
```json
{
    "title": "Demo title",
    "startTime": "2023-01-31T13:34:56",
    "endTime": "2023-01-31T14:34:56",
    "className": "Room 1",
    "isOnline": false,
    "organizer": "Mike.Smith"
}
```

- List Schedules by ClassName: </br>
Endpoint: GET /api/v1.0/schedules/{className} </br>
List all schedules for a specific classname. </br>
```bash
GET /api/v1.0/schedules/{className}
```


