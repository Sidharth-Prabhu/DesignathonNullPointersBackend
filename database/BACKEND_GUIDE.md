# Backend Development Guide

## 📁 Project Structure

```
DesignathonNullPointersBackend/
├── src/main/java/com/designathon/nullpointers/attendancebackend/
│   ├── controller/           # REST API endpoints
│   │   ├── AuthController.java
│   │   ├── AdminController.java
│   │   ├── FacultyController.java
│   │   ├── StudentController.java
│   │   ├── TestController.java
│   │   └── PasswordEncoderController.java
│   ├── entity/               # Database models
│   │   ├── User.java
│   │   ├── Student.java
│   │   ├── Faculty.java
│   │   ├── Classroom.java
│   │   └── Attendance.java
│   ├── repository/           # JPA repositories
│   │   ├── UserRepository.java
│   │   ├── StudentRepository.java
│   │   ├── FacultyRepository.java
│   │   ├── ClassroomRepository.java
│   │   └── AttendanceRepository.java
│   ├── service/              # Business logic
│   │   ├── UserService.java
│   │   ├── StudentService.java
│   │   ├── FacultyService.java
│   │   ├── ClassroomService.java
│   │   └── AttendanceService.java
│   └── security/             # Security configuration
│       ├── SecurityConfig.java
│       ├── JwtUtil.java
│       ├── JwtFilter.java
│       └── UserDetailsServiceImpl.java
├── src/main/resources/
│   └── application.properties
├── database/
│   ├── setup_centralized_login.sql
│   └── fix_user_table.sql
└── pom.xml
```

## 🔧 Configuration

### application.properties

```properties
# Application Name
spring.application.name=attendance-backend

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/attendance?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Secret (Keep this secure!)
jwt.secret=4f8kPqW3zR9tV2xY7mN5bJ0cL6aD8eF1gH3iK9oM2pQ4rT6uW8yZ0
```

## 🎯 Adding New Features

### Create New Entity

```java
@Entity
@Data
public class NewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    // Add more fields
}
```

### Create Repository

```java
public interface NewEntityRepository extends JpaRepository<NewEntity, Long> {
    // Add custom queries here
}
```

### Create Service

```java
@Service
public class NewEntityService {
    @Autowired
    private NewEntityRepository repository;
    
    public NewEntity save(NewEntity entity) {
        return repository.save(entity);
    }
    
    public List<NewEntity> findAll() {
        return repository.findAll();
    }
}
```

### Create Controller

```java
@RestController
@RequestMapping("/api/new-endpoint")
public class NewEntityController {
    @Autowired
    private NewEntityService service;
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewEntity entity) {
        return ResponseEntity.ok(service.save(entity));
    }
    
    @GetMapping
    public List<NewEntity> getAll() {
        return service.findAll();
    }
}
```

## 🔐 Security

### Add New Protected Endpoint

1. Add to `SecurityConfig.java`:
```java
.requestMatchers("/api/your-endpoint/**").hasRole("ADMIN")
```

2. Add to controller:
```java
@PreAuthorize("hasRole('ADMIN')")
```

## 🧪 Testing APIs

### Using cURL

```bash
# Test login
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"

# Test protected endpoint
curl -X GET http://localhost:8080/api/admin/students \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

### Using Postman

1. Import collection from `database/api-collection.json` (if available)
2. Set environment variable: `base_url = http://localhost:8080`
3. Login to get token
4. Token auto-added to subsequent requests

## 🐛 Debugging

### Enable SQL Logging
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Debug JWT Tokens
```java
// Add to any controller for debugging
@GetMapping("/debug/token/{token}")
public Claims debugToken(@PathVariable String token) {
    return jwtUtil.extractClaims(token);
}
```

### Check Database
```sql
-- View all users
SELECT * FROM user;

-- View attendance records
SELECT * FROM attendance;

-- Clear all data (careful!)
TRUNCATE attendance;
TRUNCATE classroom_student;
TRUNCATE classroom;
```

## 📝 Code Style

- Use Lombok `@Data` for entities
- Follow REST naming conventions
- Use ResponseEntity for responses
- Add `@PreAuthorize` for security
- Keep services thin, move logic to domain models

## 🚀 Build & Run

```bash
# Clean build
mvn clean

# Compile
mvn compile

# Build JAR
mvn package

# Run
mvn spring-boot:run

# Run JAR
java -jar target/attendance-backend-0.0.1-SNAPSHOT.jar
```

## 📊 Database Schema

```
user
├── id (BIGINT, PK, AUTO_INCREMENT)
├── username (VARCHAR)
├── password (VARCHAR, BCrypt)
└── role (VARCHAR: ADMIN/FACULTY/STUDENT)

student
├── id (BIGINT, PK, AUTO_INCREMENT)
├── name (VARCHAR)
├── regdNumber (VARCHAR)
├── dept (VARCHAR)
├── phone (VARCHAR)
└── email (VARCHAR)

faculty
├── id (BIGINT, PK, AUTO_INCREMENT)
├── name (VARCHAR)
├── staffCode (VARCHAR)
├── dept (VARCHAR)
├── phone (VARCHAR)
└── email (VARCHAR)

classroom
├── id (BIGINT, PK, AUTO_INCREMENT)
├── name (VARCHAR)
└── students (Many-to-Many with Student)

attendance
├── id (BIGINT, PK, AUTO_INCREMENT)
├── student_id (BIGINT, FK)
├── classroom_id (BIGINT, FK)
├── date (DATE)
└── status (VARCHAR: PRESENT/ABSENT/OD_INTERNAL/OD_EXTERNAL)
```

## 🔒 Security Best Practices

1. **Never commit** `application.properties` with real passwords
2. Use environment variables for production
3. Rotate JWT secret regularly
4. Implement token refresh mechanism
5. Add rate limiting for login endpoint

## 📚 Resources

- Spring Boot Docs: https://spring.io/projects/spring-boot
- Spring Security: https://spring.io/projects/spring-security
- JWT.io: https://jwt.io/
- Hibernate ORM: https://hibernate.org/orm/

---

**Happy Coding! 🎉**
