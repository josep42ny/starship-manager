# Starship Manager

A Spring Boot application for managing starships. This API provides endpoints for user authentication and comprehensive CRUD operations for starship management with JWT-based security.

## API Reference

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/api/v1/auth/login` | User login with username and password | No |
| POST | `/api/v1/auth/register` | User registration | No |
| GET | `/api/v1/starships` | List all starships with pagination and filtering | Yes |
| GET | `/api/v1/starships/{id}` | Get a specific starship by ID | Yes |
| POST | `/api/v1/starships` | Create a new starship | Yes |
| PATCH | `/api/v1/starships/{id}` | Update an existing starship | Yes |
| DELETE | `/api/v1/starships/{id}` | Delete a starship | Yes |

**Swagger UI**: Interactive API documentation is available at `http://localhost:8080/swagger-ui/index.html`

## Environment Variables

The application requires the following environment variables to be set in a `.env` file:

| Variable | Description | Example |
|----------|-------------|---------|
| `JWT_EXPIRATION` | JWT token expiration time in milliseconds | `3600000` |
| `JWT_ISSUER` | JWT issuer identifier | `starship-manager` |
| `JWT_SECRET` | Secret key for JWT signing (must be secure) | `your-secret-key-here-min-32-chars` |
| `CORS_ALLOWED_ORIGINS` | Comma-separated list of allowed CORS origins | `http://localhost:3000,http://localhost:8080` |
| `CORS_ALLOWED_METHODS` | Allowed HTTP methods for CORS | `GET,POST,PUT,PATCH,DELETE` |
| `CORS_ALLOWED_HEADERS` | Allowed headers for CORS requests | `Content-Type,Authorization` |
| `MARIADB_ROOT_PASSWORD` | Root password for MariaDB database | `root_password` |
| `MARIADB_PASSWORD` | Password for the application database user | `app_password` |
| `MARIADB_USER` | Database user for the application | `starship_user` |
| `MARIADB_DATABASE` | Database name | `starship_db` |

Create a `.env` file in the project root by copying `.env.template` and filling in the required values:

```bash
cp .env.template .env
# Edit .env with your configuration values
```

## Run Locally

### Configure environment variables:

```bash
cp .env.template .env
# Edit .env with your desired configuration
```

### Start the services:

```bash
docker-compose up -d
```

This will:
- Start a MariaDB 12 database container
- Create necessary volumes for data persistence
- Configure the database with the specified credentials

### Build and run the Spring Boot application:
```bash
./mvnw spring-boot:run
```

### Access the application:

    API: http://localhost:8080
    Swagger UI: http://localhost:8080/swagger-ui/index.html

### Stop the services:
```bash
docker-compose down
```
