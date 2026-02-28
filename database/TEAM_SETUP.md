# Quick Start Guide for Team

## 🚀 Get Started in 5 Minutes

### Prerequisites Check
- [ ] Java 17+ installed
- [ ] Node.js 16+ installed
- [ ] MySQL running
- [ ] Git installed

### Step 1: Clone Repository
```bash
git clone <repository-url>
cd DesignathonNullPointersBackend
```

### Step 2: Setup Database
```bash
# Open MySQL
mysql -u root -p

# Create database
CREATE DATABASE attendance;
exit;

# Run setup script
mysql -u root -p attendance < database/setup_centralized_login.sql
```

### Step 3: Configure Backend
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 4: Start Backend
```bash
mvn spring-boot:run
```
✅ Backend running on http://localhost:8080

### Step 5: Setup Frontend
```bash
cd ../DesignathonNullPointersFrontend

# Create .env file
echo REACT_APP_API_URL=http://localhost:8080 > .env

# Install dependencies
npm install

# Start frontend
npm start
```
✅ Frontend running on http://localhost:3000

### Step 6: Test Login
Open http://localhost:3000

**Credentials:**
- Admin: `admin` / `admin123`
- Faculty: `faculty1` / `admin123`
- Student: `student1` / `admin123`

---

## 🐛 Common Issues

| Problem | Solution |
|---------|----------|
| Port 8080 in use | `netstat -ano | findstr :8080` then `taskkill /PID <PID> /F` |
| Login fails | Check backend is running, verify database users |
| CORS error | Ensure .env file exists with correct API URL |
| Module not found | Run `npm install` |

---

## 📞 Need Help?

1. Check main README.md
2. See database/SETUP_GUIDE.md
3. Contact team lead

**Happy Coding! 🎉**
