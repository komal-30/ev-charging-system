# ⚡ EV Charging System

A Spring Boot-based REST API for managing electric vehicle charging stations, reservations, and user bookings. This system enables users to locate, reserve, and manage EV charging sessions efficiently.

---

## 📌 About

The EV Charging System is a comprehensive backend solution designed for managing electric vehicle charging infrastructure. It provides a robust platform for charging station management, real-time availability tracking, and user reservation handling. The system leverages Spring Boot microservices architecture with comprehensive testing and CI/CD integration through Jenkins and SonarQube for code quality assurance.

**Key Features:**
- Manage multiple charging stations across locations
- Real-time charging station availability
- User reservation and booking system
- Station status updates and monitoring
- RESTful API for seamless integration
- Continuous integration with Jenkins
- Code quality analysis with SonarQube

---

## 🛠️ Technology Stack & Dependencies

| Framework & Language | Database & ORM | Testing & Quality | Build & DevOps |
|:---:|:---:|:---:|:---:|
| Spring Boot 3.4.4 | MySQL | Pact (JUnit 5) | Maven 3.9.6 |
| Java 17 | Hibernate 6.6.11 | Spring Boot Test | Jenkins |
| Spring Web | MySQL Connector/J | SonarQube Analysis | SonarCloud |
| Spring Data JPA | Jakarta Validation | - | Docker Ready |

**Key Dependencies:**
- `spring-boot-starter-web` - REST API framework
- `spring-boot-starter-data-jpa` - Database ORM
- `spring-boot-starter-actuator` - Application monitoring
- `hibernate-core` - JPA implementation
- `lombok` - Code generation and reduction
- `pact-consumer` - Contract testing
- `spring-cloud-dependencies` - Microservices support

---

## 📂 Project Structure

```
ev-charging-system/
├── .mvn/wrapper/              # Maven wrapper files
├── .settings/                 # Eclipse settings
├── src/
│   ├── main/
│   │   ├── java/com/ev/charging/system/
│   │   │   ├── controller/    # REST API endpoints
│   │   │   ├── entity/        # JPA entities (ChargingStation, Reservation, User)
│   │   │   ├── exception/     # Custom exception classes
│   │   │   ├── repository/    # Data access layer (DAO)
│   │   │   ├── service/       # Service interfaces
│   │   │   └── Application.java # Main Spring Boot entry point
│   │   └── resources/         # Configuration files (application.properties)
│   └── test/java/
│       └── com/ev/charging/system/test/ # Unit & integration tests
├── target/                    # Compiled binaries
├── Jenkinsfile               # CI/CD pipeline configuration
├── pom.xml                   # Maven dependencies
└── README.md                 # Project documentation
```

**Key Components:**
- **controller** - REST endpoints for charging stations and reservations
  - `ChargingStationController` - CRUD operations for stations
  - `ReservationController` - Reservation management
- **entity** - Database models for stations, reservations, users
- **repository** - Spring Data JPA repositories for database queries
- **service** - Business logic layer for core operations
- **exception** - Custom exception handling for errors

---

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.9.6+
- MySQL 8.0+
- Git
- Jenkins (optional, for CI/CD)

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/komal-30/ev-charging-system.git
   cd ev-charging-system
   ```

2. **Configure Database**
   - Create a MySQL database for EV charging system:
     ```sql
     CREATE DATABASE ev_charging_db;
     ```
   - Update `src/main/resources/application.properties`:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/ev_charging_db
     spring.datasource.username=root
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     ```

3. **Build the Project**
   ```bash
   mvn clean install
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`

5. **Verify Installation**
   - Access charging stations: `GET http://localhost:8080/api/charging-stations`
   - Check application health: `GET http://localhost:8080/actuator/health`

### Configuration Notes
- Update database credentials in `application.properties`
- Configure SonarQube token for code quality analysis
- Set Jenkins credentials for CI/CD pipeline
- Adjust logging levels as needed in configuration file

---

## 🔌 API Endpoints

### Charging Stations
- `GET /api/charging-stations` - Get all charging stations
- `GET /api/charging-stations/{id}` - Get station by ID
- `POST /api/charging-stations` - Create new station
- `PUT /api/charging-stations/{id}` - Update station details
- `DELETE /api/charging-stations/{id}` - Delete station

### Reservations
- `GET /api/reservations/user/{userId}` - Get user's reservations
- `GET /api/reservations/station/{stationId}` - Get station's reservations
- `POST /api/reservations/station/{stationId}/user/{userId}` - Create reservation
- `PUT /api/reservations/{reservationId}/status` - Update reservation status
- `DELETE /api/reservations/{reservationId}` - Cancel reservation

---

---

## 🔄 CI/CD Pipeline

The project includes a **Jenkins pipeline** (`Jenkinsfile`) with automated stages:

1. **Checkout Code** - Pulls latest code from main branch
2. **Build** - Compiles and packages using Maven
3. **SonarQube Analysis** - Code quality scanning via SonarCloud
4. **Archive Artifacts** - Stores JAR files for deployment

---

## 🧪 Testing

The project uses:
- **Pact (JUnit 5)** - Contract testing for microservices
- **Spring Boot Test** - Unit and integration testing
- **Maven Test** - Test execution framework

Run tests:
```bash
mvn test
```

---

## 📊 Database Schema

**Charging Station Entity**
- ID (Primary Key)
- Location
- Capacity
- Available Slots
- Status
- Created Date

**Reservation Entity**
- ID (Primary Key)
- User ID (Foreign Key)
- Station ID (Foreign Key)
- Start Time
- End Time
- Status (PENDING, CONFIRMED, COMPLETED, CANCELLED)
- Created Date

**User Entity**
- ID (Primary Key)
- Name
- Email
- Phone
- Created Date

---

## 🔐 Security Considerations

- Database credentials secured in environment variables
- Jenkins credentials management for sensitive data
- SonarQube token secured via Jenkins credentials store
- Input validation using Jakarta Validation
- RESTful endpoints follow security best practices

---

## 📈 Future Enhancements

- [ ] 🔑 **User Authentication** - JWT-based user authentication
- [ ] 💳 **Payment Integration** - Stripe/PayPal for charging fees
- [ ] 🗺️ **Map Integration** - Google Maps for station locations
- [ ] 📧 **Email Notifications** - Booking confirmations and reminders
- [ ] 📊 **Analytics Dashboard** - Usage statistics and reports
- [ ] 🔋 **Energy Management** - Monitor charging rates and power usage

---

## 🔗 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM](https://hibernate.org/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [SonarQube Quality Analysis](https://www.sonarqube.org/)
