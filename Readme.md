# WebLogic Demo Application 🚀

A simple Java web application using Maven that can be deployed as a WAR file to WebLogic or any Java EE application server.

## Project Structure

```
weblogic-demo/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── demo/
│       │               ├── HelloResource.java
│       │               └── RestApplication.java
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml
│           └── index.jsp
```

## Features

- REST endpoint at `/api/hello` that returns: "Hello from WebLogic Demo App 🚀"
- Simple JSP landing page at root
- Java 11 compatible (also works with Java 8)
- Packaged as WAR file

## Prerequisites

- Java 8 or 11 installed
- Maven 3.6+ installed
- WebLogic Server (or any Java EE application server)

## Building the WAR File

### Option 1: Using Maven Command

```bash
mvn clean package
```

The WAR file will be generated at: `target/weblogic-demo.war`

### Option 2: Step-by-step Build

```bash
# Clean previous builds
mvn clean

# Compile the code
mvn compile

# Package as WAR
mvn package
```

### Option 3: Skip Tests (if any)

```bash
mvn clean package -DskipTests
```

## Deployment

1. Build the WAR file using the command above
2. Copy `target/weblogic-demo.war` to your WebLogic deployments directory
3. Or deploy through WebLogic Admin Console:
   - Navigate to Deployments
   - Click "Install"
   - Select the WAR file
   - Follow the deployment wizard

## Testing the Application

After deployment, access:

- **Home Page**: `http://localhost:7001/weblogic-demo/`
- **REST Endpoint**: `http://localhost:7001/weblogic-demo/api/hello`

(Replace `localhost:7001` with your WebLogic server address and port)

## Expected Output

When you access `/api/hello`, you should see:

```
Hello from WebLogic Demo App 🚀
```

## Technology Stack

- Java 11
- Maven 3
- JAX-RS 2.1 (REST API)
- Servlet API 4.0
- JSP

## Notes

- Dependencies are marked as `provided` scope since WebLogic provides these libraries
- The application uses standard Java EE APIs for maximum compatibility
- No external dependencies or database required
