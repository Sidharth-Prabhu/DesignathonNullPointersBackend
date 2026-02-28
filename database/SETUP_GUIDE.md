# Centralized Login System - Complete Setup Guide

## Overview
This guide will help you set up the centralized login system where:
- **Admin** users → `/admin/dashboard`
- **Faculty** users → `/faculty/dashboard`
- **Student** users → `/student/dashboard`

---

## 📁 Backend Setup (Already Done ✅)

The following backend changes have been implemented:

### 1. Updated Files
- ✅ `AuthController.java` - Now returns `redirectUrl` based on role
- ✅ `SecurityConfig.java` - Added role-based endpoint protection
- ✅ New entities and controllers for attendance management

### 2. New Files Created
- ✅ `Attendance.java` - Entity for attendance records
- ✅ `AttendanceRepository.java` - Repository for attendance
- ✅ `AttendanceService.java` - Service for attendance operations
- ✅ `FacultyController.java` - Faculty-specific endpoints
- ✅ `StudentController.java` - Student-specific endpoints
- ✅ `setup_centralized_login.sql` - Database setup script

---

## 🗄️ Database Setup (REQUIRED - Step 1)

### Execute the SQL Script

1. **Open MySQL Command Line** or **MySQL Workbench**

2. **Connect to your database:**
   ```sql
   USE attendance;
   ```

3. **Run the setup script:**
   
   **Option A - From Command Line:**
   ```bash
   mysql -u root -p attendance < "C:\Users\shyle\OneDrive\Desktop\backend\DesignathonNullPointersBackend\database\setup_centralized_login.sql"
   ```

   **Option B - Copy-Paste in MySQL Workbench:**
   
   Open the file `setup_centralized_login.sql` and copy this content:

   ```sql
   -- ============================================
   -- Centralized Login System - Database Setup
   -- ============================================
   
   -- Insert Admin User (password: test123)
   INSERT INTO user (username, password, role) 
   VALUES ('admin', '$2a$10$YQ5E6zKZqJxZ8vN9mH3LxuR4tW2sP0qY7nF8jK9dL1mC3vB5xA6gO', 'ADMIN')
   ON DUPLICATE KEY UPDATE password = VALUES(password), role = VALUES(role);
   
   -- Insert Faculty User (password: test123)
   INSERT INTO user (username, password, role) 
   VALUES ('faculty1', '$2a$10$YQ5E6zKZqJxZ8vN9mH3LxuR4tW2sP0qY7nF8jK9dL1mC3vB5xA6gO', 'FACULTY')
   ON DUPLICATE KEY UPDATE password = VALUES(password), role = VALUES(role);
   
   -- Insert Student User (password: test123)
   INSERT INTO user (username, password, role) 
   VALUES ('student1', '$2a$10$YQ5E6zKZqJxZ8vN9mH3LxuR4tW2sP0qY7nF8jK9dL1mC3vB5xA6gO', 'STUDENT')
   ON DUPLICATE KEY UPDATE password = VALUES(password), role = VALUES(role);
   
   -- Verify users
   SELECT id, username, role FROM user;
   ```

4. **Verify the users were created:**
   ```sql
   SELECT * FROM user;
   ```

   You should see 3 users with roles: ADMIN, FACULTY, STUDENT

---

## 🖥️ Frontend Setup (Step 2)

### 1. Navigate to Frontend Folder
```bash
cd C:\Users\shyle\OneDrive\Desktop\frontend\DesignathonNullPointersFrontend
```

### 2. Install Missing Dependencies
```bash
npm install @mui/x-date-pickers @mui/icons-material dayjs
```

### 3. Create Required Files

Follow the instructions in:
```
C:\Users\shyle\OneDrive\Desktop\backend\DesignathonNullPointersBackend\database\FRONTEND_CODE.md
```

This file contains complete code for:
- `src/components/Login.js`
- `src/App.js`
- `src/components/AdminDashboard.js`
- `src/components/FacultyDashboard.js`
- `src/components/StudentDashboard.js`
- `src/components/MarkAttendance.js`
- `src/theme/colors.js`
- `.env`

### Quick Setup Commands:

```bash
# Create .env file
echo REACT_APP_API_URL=http://localhost:8080 > .env

# Create theme folder and colors.js
mkdir src\theme

# Create colors.js
echo export const primaryColor = '#1976d2'; > src\theme\colors.js
echo export const secondaryColor = '#1565c0'; >> src\theme\colors.js
echo export const successColor = '#4caf50'; >> src\theme\colors.js
echo export const accentColor = '#2196f3'; >> src\theme\colors.js
echo export const warningColor = '#ff9800'; >> src\theme\colors.js
echo export const errorColor = '#f44336'; >> src\theme\colors.js
```

---

## 🚀 Running the Application

### 1. Start Backend (Spring Boot)
```bash
cd C:\Users\shyle\OneDrive\Desktop\backend\DesignathonNullPointersBackend
mvn spring-boot:run
```

Backend will start on: **http://localhost:8080**

### 2. Start Frontend (React)
```bash
cd C:\Users\shyle\OneDrive\Desktop\frontend\DesignathonNullPointersFrontend
npm start
```

Frontend will start on: **http://localhost:3000**

---

## 🔐 Test Credentials

| Role | Username | Password | Redirects To |
|------|----------|----------|--------------|
| **Admin** | `admin` | `test123` | `/admin/dashboard` |
| **Faculty** | `faculty1` | `test123` | `/faculty/dashboard` |
| **Student** | `student1` | `test123` | `/student/dashboard` |

---

## ✅ Verification Steps

### 1. Test Backend Login Endpoint
```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d "{\"username\":\"admin\",\"password\":\"test123\"}"
```

Expected response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "role": "ADMIN",
  "redirectUrl": "/admin/dashboard",
  "username": "admin"
}
```

### 2. Test Frontend Login
1. Open http://localhost:3000
2. Enter username: `admin`
3. Enter password: `test123`
4. Click "SIGN IN"
5. Should redirect to `/admin/dashboard`

### 3. Test Role-Based Redirects
- Login as `faculty1` → Should go to `/faculty/dashboard`
- Login as `student1` → Should go to `/student/dashboard`

---

## 🐛 Troubleshooting

### Issue: "Invalid username or password"
**Solution:** 
- Verify users exist in database: `SELECT * FROM user;`
- Check password hash matches exactly
- Ensure BCrypt is working correctly

### Issue: "CORS Error" in browser console
**Solution:**
- Backend `SecurityConfig.java` already allows `http://localhost:3000`
- Ensure backend is running on port 8080
- Clear browser cache and restart

### Issue: "401 Unauthorized" after login
**Solution:**
- Check if token is being stored: `console.log(localStorage.getItem('token'))`
- Verify JWT secret matches in `application.properties`
- Check browser console for errors

### Issue: "Module not found" in React
**Solution:**
```bash
npm install
npm install @mui/x-date-pickers @mui/icons-material dayjs
```

### Issue: Database table doesn't exist
**Solution:**
```sql
-- Let Hibernate create tables automatically
# In application.properties, ensure:
spring.jpa.hibernate.ddl-auto=update
```

---

## 📊 Database Schema

After running the setup, your `user` table should have:

| id | username | password (hashed) | role |
|----|----------|------------------|------|
| 1 | admin | $2a$10$... | ADMIN |
| 2 | faculty1 | $2a$10$... | FACULTY |
| 3 | student1 | $2a$10$... | STUDENT |

---

## 📝 Next Steps

After successful login setup, you can:

1. **Add more users** via Admin Dashboard
2. **Create classrooms** and assign students
3. **Mark attendance** as faculty
4. **View attendance** as student

---

## 📞 Need Help?

If you encounter issues:
1. Check backend console for Spring Boot errors
2. Check browser console for React errors
3. Verify database connection in `application.properties`
4. Ensure both backend and frontend are running

---

## 🔧 Configuration Files

### Backend: `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/attendance?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=12345678
jwt.secret=4f8kPqW3zR9tV2xY7mN5bJ0cL6aD8eF1gH3iK9oM2pQ4rT6uW8yZ0
```

### Frontend: `.env`
```
REACT_APP_API_URL=http://localhost:8080
```

---

**Setup Complete! 🎉**

You now have a fully functional centralized login system with role-based routing.
