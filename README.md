# Attendance Management System

A full-stack web application for managing student attendance with role-based access control.

## 🚀 Features

- **Centralized Login System** - Single login for Admin, Faculty, and Student roles
- **Role-Based Dashboards** - Custom interface for each user type
- **Attendance Management** - Mark and track attendance with multiple status types
- **User Management** - Admin can add students, faculty, and create classrooms
- **Secure Authentication** - JWT-based token authentication
- **Responsive UI** - Modern Material-UI design

## 🛠️ Tech Stack

### Backend
- **Framework:** Spring Boot 4.0.3
- **Language:** Java 17
- **Database:** MySQL
- **Security:** Spring Security + JWT
- **ORM:** Spring Data JPA
- **Build Tool:** Maven

### Frontend
- **Framework:** React 19.2.4
- **UI Library:** Material-UI (MUI) v7
- **Routing:** React Router DOM v7
- **HTTP Client:** Axios
- **Build Tool:** Create React App

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17 or higher** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Node.js 16 or higher** - [Download](https://nodejs.org/)
- **MySQL 8.0 or higher** - [Download](https://dev.mysql.com/downloads/)
- **Git** - [Download](https://git-scm.com/)

## 🔧 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd DesignathonNullPointers
```

### Step 2: Database Setup

1. **Create MySQL Database:**
   ```sql
   CREATE DATABASE attendance;
   ```

2. **Run Database Setup Script:**
   
   Navigate to the database folder and run the SQL script:
   ```bash
   mysql -u root -p attendance < database/setup_centralized_login.sql
   ```
   
   Or manually execute the SQL commands from `database/setup_centralized_login.sql`

3. **Verify Users:**
   ```sql
   USE attendance;
   SELECT id, username, role FROM user;
   ```
   
   You should see 3 users: admin, faculty1, student1

### Step 3: Backend Setup

1. **Navigate to Backend Folder:**
   ```bash
   cd DesignathonNullPointersBackend
   ```

2. **Configure Database Connection:**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/attendance?useSSL=false&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```

3. **Build the Project:**
   ```bash
   mvn clean install
   ```

4. **Run the Backend:**
   ```bash
   mvn spring-boot:run
   ```
   
   Backend will start on: **http://localhost:8080**

### Step 4: Frontend Setup

1. **Navigate to Frontend Folder:**
   ```bash
   cd DesignathonNullPointersFrontend
   ```

2. **Install Dependencies:**
   ```bash
   npm install
   ```

3. **Configure API URL:**
   
   Create a `.env` file in the root directory:
   ```
   REACT_APP_API_URL=http://localhost:8080
   ```

4. **Run the Frontend:**
   ```bash
   npm start
   ```
   
   Frontend will start on: **http://localhost:3000**

## 🔐 Test Credentials

| Role | Username | Password | Dashboard |
|------|----------|----------|-----------|
| Admin | `admin` | `admin123` | `/admin/dashboard` |
| Faculty | `faculty1` | `admin123` | `/faculty/dashboard` |
| Student | `student1` | `admin123` | `/student/dashboard` |

## 📁 Project Structure

```
DesignathonNullPointers/
├── DesignathonNullPointersBackend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/designathon/nullpointers/attendancebackend/
│   │   │   │   ├── controller/      # REST API endpoints
│   │   │   │   ├── entity/          # JPA entities
│   │   │   │   ├── repository/      # Data repositories
│   │   │   │   ├── service/         # Business logic
│   │   │   │   └── security/        # Security configuration
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── pom.xml
│   └── database/                    # Database scripts
│
└── DesignathonNullPointersFrontend/
    ├── public/
    ├── src/
    │   ├── components/              # React components
    │   ├── theme/                   # Theme configuration
    │   ├── App.js                   # Main app component
    │   └── index.js                 # Entry point
    ├── package.json
    └── .env                         # Environment variables
```

## 🎯 API Endpoints

### Public Endpoints
- `POST /api/login` - User login
- `GET /api/encode-password` - Encode password (utility)
- `GET /api/test/users` - List all users (debug)

### Admin Endpoints (Requires ADMIN role)
- `POST /api/admin/students` - Add student
- `GET /api/admin/students` - Get all students
- `POST /api/admin/faculties` - Add faculty
- `GET /api/admin/faculties` - Get all faculties
- `POST /api/admin/classrooms` - Create classroom
- `POST /api/admin/classrooms/{id}/students` - Add students to classroom
- `GET /api/admin/classrooms` - Get all classrooms

### Faculty Endpoints (Requires FACULTY role)
- `GET /api/faculty/my-classes` - Get assigned classes
- `GET /api/faculty/classroom/{id}/students` - Get classroom students
- `POST /api/faculty/attendance/mark` - Mark attendance
- `GET /api/faculty/attendance/today` - Today's attendance summary

### Student Endpoints (Requires STUDENT role)
- `GET /api/student/my-attendance` - Get personal attendance
- `GET /api/student/attendance-summary` - Get attendance statistics

## 🔍 Troubleshooting

### Backend Issues

**Problem:** Port 8080 already in use
```bash
# Windows - Find and kill process
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

**Problem:** Database connection failed
- Check MySQL is running
- Verify database name is `attendance`
- Check username/password in `application.properties`

**Problem:** Build fails
```bash
# Clean and rebuild
mvn clean
mvn install
```

### Frontend Issues

**Problem:** Port 3000 already in use
```bash
# Use different port
set PORT=3001 && npm start
```

**Problem:** Module not found
```bash
# Reinstall dependencies
rm -rf node_modules package-lock.json
npm install
```

**Problem:** CORS errors
- Ensure backend is running on port 8080
- Check `.env` file has correct API URL
- Restart both backend and frontend

**Problem:** Login fails
- Verify backend is running
- Check database has test users
- Ensure credentials are correct (password: admin123)

### Database Issues

**Problem:** Table doesn't exist
- Let Hibernate auto-create tables (ddl-auto=update)
- Or run: `mysql -u root -p attendance < database/fix_user_table.sql`

**Problem:** User not found
- Run the setup script: `database/setup_centralized_login.sql`
- Verify with: `SELECT * FROM user;`

## 🧪 Testing the Application

1. **Start Both Servers:**
   - Backend: `mvn spring-boot:run`
   - Frontend: `npm start`

2. **Open Browser:** http://localhost:3000

3. **Test Each Role:**
   - Login as admin → Should see Admin Dashboard
   - Logout → Login as faculty → Should see Faculty Dashboard
   - Logout → Login as student → Should see Student Dashboard

4. **Test Features:**
   - Admin: Add student, add faculty, create classroom
   - Faculty: Mark attendance
   - Student: View attendance

## 📝 Additional Resources

- **Backend Documentation:** See `database/SETUP_GUIDE.md`
- **Frontend Code:** See `database/FRONTEND_CODE.md`
- **Quick Start:** See `database/QUICK_START.md`

## 👥 Team

Designathon NullPointers Team

## 📄 License

This project is created for Designathon purposes.

## 🤝 Contributing

1. Create a feature branch
2. Make your changes
3. Test thoroughly
4. Submit a pull request

---

**Need Help?** Open an issue or contact the development team.
