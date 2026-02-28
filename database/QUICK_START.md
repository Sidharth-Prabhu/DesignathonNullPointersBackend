# Quick Start - Centralized Login System

## 🚀 3-Step Setup

### Step 1: Database (2 minutes)
```bash
# Open MySQL and run:
mysql -u root -p
USE attendance;
source C:\Users\shyle\OneDrive\Desktop\backend\DesignathonNullPointersBackend\database\setup_centralized_login.sql
```

### Step 2: Backend (Already compiled ✅)
```bash
cd C:\Users\shyle\OneDrive\Desktop\backend\DesignathonNullPointersBackend
mvn spring-boot:run
```
Backend runs on: **http://localhost:8080**

### Step 3: Frontend
```bash
cd C:\Users\shyle\OneDrive\Desktop\frontend\DesignathonNullPointersFrontend

# Install dependencies (first time only)
npm install @mui/x-date-pickers @mui/icons-material dayjs

# Start React app
npm start
```
Frontend runs on: **http://localhost:3000**

---

## 🔑 Test Login Credentials

| Username | Password | Role | Redirects To |
|----------|----------|------|--------------|
| `admin` | `test123` | Admin | Admin Dashboard |
| `faculty1` | `test123` | Faculty | Faculty Dashboard |
| `student1` | `test123` | Student | Student Dashboard |

---

## 📁 Complete Code for Frontend

All frontend code is in:
```
C:\Users\shyle\OneDrive\Desktop\backend\DesignathonNullPointersBackend\database\FRONTEND_CODE.md
```

Copy-paste the code from that file to create/update your React components.

---

## ✅ Verify It Works

1. Open http://localhost:3000
2. Login with `admin` / `test123`
3. Should see Admin Dashboard
4. Logout and try `faculty1` / `test123`
5. Should see Faculty Dashboard
6. Logout and try `student1` / `test123`
7. Should see Student Dashboard

---

## 🐛 Common Issues

| Problem | Solution |
|---------|----------|
| Login fails | Run SQL script to create users |
| CORS error | Ensure backend is running on port 8080 |
| Module not found | Run `npm install @mui/x-date-pickers @mui/icons-material dayjs` |
| 401 Unauthorized | Check if token is in localStorage |

---

## 📞 Files Reference

### Backend Files (Already Created)
- ✅ `AuthController.java` - Updated with role-based redirect
- ✅ `SecurityConfig.java` - Updated with role protection
- ✅ `FacultyController.java` - New faculty endpoints
- ✅ `StudentController.java` - New student endpoints
- ✅ `Attendance.java` - New attendance entity

### Frontend Files (You Need to Create)
- 📝 `src/components/Login.js` - Replace with new code
- 📝 `src/App.js` - Replace with new code
- 📝 `src/components/AdminDashboard.js` - New file
- 📝 `src/components/FacultyDashboard.js` - Replace
- 📝 `src/components/StudentDashboard.js` - New file
- 📝 `src/components/MarkAttendance.js` - Replace
- 📝 `src/theme/colors.js` - New file (create folder)
- 📝 `.env` - New file in root

All code available in `FRONTEND_CODE.md`

---

## 🎯 What's New?

### Before:
- Single login → always goes to `/dashboard`
- No role-based routing
- Separate admin/faculty login

### After:
- **One login** → Smart redirect based on role
- Admin → `/admin/dashboard`
- Faculty → `/faculty/dashboard`
- Student → `/student/dashboard`
- Centralized user management

---

**Happy Coding! 🎉**
