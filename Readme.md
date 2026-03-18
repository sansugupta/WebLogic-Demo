# Incident Management System

A production-style Java web application for managing incidents, built with Spring Boot and designed for WebLogic deployment.

## 🚀 Features

### Core Functionality
- **Incident Management**: Create, read, update, and delete incidents
- **Status Tracking**: Track incident status (Open, In Progress, Resolved)
- **Priority Management**: Set priority levels (Low, Medium, High, Critical)
- **RESTful APIs**: Complete REST API for programmatic access
- **Web Interface**: User-friendly JSP-based web UI
- **Health Monitoring**: Built-in health check endpoints

### Technical Features
- **Production-Ready**: Proper logging, exception handling, and validation
- **Clean Architecture**: Layered architecture (Controller → Service → Repository)
- **In-Memory Storage**: Thread-safe concurrent data storage
- **Environment Support**: Configurable for different environments
- **WAR Deployment**: Ready for WebLogic server deployment

## 🏗️ Architecture

```
src/main/java/com/example/incident/
├── IncidentManagementApplication.java    # Main Spring Boot application
├── controller/
│   ├── IncidentController.java          # REST API endpoints
│   ├── WebController.java               # Web UI controllers
│   └── HealthController.java            # Health check endpoints
├── service/
│   └── IncidentService.java             # Business logic layer
├── repository/
│   └── IncidentRepository.java          # Data access layer
├── model/
│   ├── Incident.java                    # Incident entity
│   ├── Status.java                      # Status enumeration
│   └── Priority.java                    # Priority enumeration
└── exception/
    ├── IncidentNotFoundException.java   # Custom exception
    └── GlobalExceptionHandler.java     # Global error handling
```

## 📋 API Endpoints

### Incident Management
- `POST /incidents` - Create new incident
- `GET /incidents` - Get all incidents
- `GET /incidents?status=OPEN` - Filter by status
- `GET /incidents/{id}` - Get incident by ID
- `PUT /incidents/{id}` - Update entire incident
- `PUT /incidents/{id}/status` - Update incident status
- `DELETE /incidents/{id}` - Delete incident
- `GET /incidents/stats` - Get incident statistics

### Health & Monitoring
- `GET /health` - Application health check
- `GET /info` - Application information

### Web Interface
- `/` - Redirects to incident list
- `/web/incidents` - Incident dashboard
- `/web/incidents/new` - Create incident form
- `/web/incidents/{id}` - Incident details

## 🛠️ Technology Stack

- **Java 11** - Programming language
- **Spring Boot 2.7.18** - Application framework
- **Maven** - Build tool
- **JSP + JSTL** - Web UI technology
- **SLF4J + Logback** - Logging framework
- **Bean Validation** - Input validation
- **Concurrent Collections** - Thread-safe data storage

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- WebLogic Server (for deployment)

### Building the Application

1. **Clean and compile:**
   ```bash
   mvn clean compile
   ```

2. **Run tests (if any):**
   ```bash
   mvn test
   ```

3. **Build WAR file:**
   ```bash
   mvn clean package
   ```

4. **Generated WAR location:**
   ```
   target/incident-management.war
   ```

### Running Locally (Development)

```bash
mvn spring-boot:run
```

Access the application at: `http://localhost:8080`

### WebLogic Deployment

1. Build the WAR file using Maven
2. Deploy `target/incident-management.war` to WebLogic Server
3. Access via your WebLogic server URL

## 🔧 Configuration

### Environment Variables
- `APP_ENV` - Environment (dev/prod) - defaults to "dev"

### Application Properties
Located in `src/main/resources/application.properties`:

```properties
# Application Configuration
spring.application.name=incident-management-system
server.port=8080

# Environment Configuration
app.env=${APP_ENV:dev}

# JSP Configuration
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

# Logging Configuration
logging.level.com.example.incident=INFO
```

## 📊 Usage Examples

### Creating an Incident via API

```bash
curl -X POST http://localhost:8080/incidents \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Server Down",
    "description": "Production server is not responding",
    "status": "OPEN",
    "priority": "CRITICAL"
  }'
```

### Getting All Incidents

```bash
curl http://localhost:8080/incidents
```

### Updating Incident Status

```bash
curl -X PUT http://localhost:8080/incidents/1/status \
  -H "Content-Type: application/json" \
  -d '{"status": "IN_PROGRESS"}'
```

### Health Check

```bash
curl http://localhost:8080/health
```

## 🎯 Sample Data

The application starts with empty data. You can create incidents via:
1. **Web UI**: Navigate to `/web/incidents/new`
2. **REST API**: Use POST `/incidents` endpoint
3. **Sample curl commands** (see Usage Examples above)

## 📝 Logging

The application provides comprehensive logging:

- **API Requests**: All REST API calls are logged
- **Business Operations**: Service layer operations
- **Error Handling**: Detailed error logging
- **Performance**: Request/response timing

Log levels can be configured in `application.properties`.

## 🔒 Production Considerations

### Security
- Input validation on all endpoints
- Proper error handling without sensitive data exposure
- CORS configuration for cross-origin requests

### Performance
- Thread-safe concurrent data structures
- Efficient in-memory storage
- Proper resource management

### Monitoring
- Health check endpoints
- Application metrics
- Structured logging

## 🧪 Testing

### Manual Testing
1. Start the application
2. Navigate to `http://localhost:8080`
3. Create, view, and manage incidents through the web interface
4. Test REST APIs using curl or Postman

### API Testing with Postman
Import the following endpoints into Postman:
- Base URL: `http://localhost:8080`
- Test all CRUD operations
- Verify error handling with invalid data

## 📦 Deployment

### WAR File Deployment
1. Build: `mvn clean package`
2. Deploy `target/incident-management.war` to WebLogic
3. Configure environment variables if needed
4. Access via WebLogic server URL

### Environment-Specific Configuration
- **Development**: Default configuration works
- **Production**: Set `APP_ENV=prod` environment variable

## 🤝 Contributing

1. Follow the existing code structure
2. Add proper logging for new features
3. Include input validation
4. Update documentation for new endpoints

## 📄 License

This is a demonstration application for educational purposes.

---

**Built with ❤️ for production-ready incident management**