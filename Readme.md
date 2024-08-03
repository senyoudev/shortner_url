# Tiny URL Service

A Spring Boot application for shortening URLs using Redis for caching and PostgreSQL for persistence.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Logging](#logging)
- [Contributing](#contributing)
- [License](#license)

## Overview

This application provides a service for shortening URLs. It uses Spring Boot for the backend, Redis for caching, and PostgreSQL for storing the URL mappings. The service encodes long URLs into short URLs and stores them in the database.

## Features

- Shorten long URLs
- Redirect from short URLs to original URLs
- Caching of URL mappings with Redis
- RESTful API for URL shortening and redirection
- Logging for monitoring and debugging

## Architecture

- **Spring Boot**: Backend framework for creating RESTful services.
- **PostgreSQL**: Database for storing URL mappings.
- **Redis**: In-memory data store for caching URL mappings.
- **SLF4J & Logback**: Logging framework for logging application events.

## Prerequisites

- Java 17 or higher (maybe it works with lower versions but it was tested with Java 17)
- Docker and Docker Compose
- Maven (optional, for local builds)

## Setup

### Using Docker

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/tinyurl-service.git
    cd tinyurl-service
    ```

2. Start the services using Docker Compose:
    ```bash
    docker-compose up -d
    ```

3. Build and run the Spring Boot application:
    ```bash
    ./mvnw spring-boot:run
    ```

### Local Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/tinyurl-service.git
    cd tinyurl-service
    ```

2. Install PostgreSQL and Redis, and start the services.

3. Update the `application.properties` file with your PostgreSQL and Redis configurations.

4. Build and run the Spring Boot application:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```

## Usage

### Shorten a URL

Send a POST request to `/api/shorten` with a JSON body containing the long URL.

Example:
```bash
curl -X POST http://localhost:8080/api/url/shorten -H "Content-Type: application/json" -d '{"originalUrl": "https://example.com"}'
```

Response
```Json
{
    "shortUrl": "http://localhost:8080/abc123"
}
```

### Redirect to the Original URL

Send a GET request to `/api/url/{shortUrl}` to redirect to the original URL.

Example:
```bash
curl -L http://localhost:8080/api/url/abc123
```