# Frontend Code for Centralized Login System

This folder contains complete frontend code for the centralized login system.
Copy these files to your frontend project folder.

## Files to Create/Replace

1. `src/components/Login.js` - Replace existing
2. `src/App.js` - Replace existing
3. `src/components/AdminDashboard.js` - New file (rename from Dashboard.js)
4. `src/components/FacultyDashboard.js` - Replace existing
5. `src/components/StudentDashboard.js` - New file
6. `src/components/MarkAttendance.js` - Replace existing
7. `src/theme/colors.js` - New file (create folder first)
8. `.env` - New file in root

---

## Step 1: Install Missing Dependencies

Run these commands in your frontend folder:

```bash
cd C:\Users\shyle\OneDrive\Desktop\frontend\DesignathonNullPointersFrontend
npm install @mui/x-date-pickers @mui/icons-material dayjs
```

---

## Step 2: Create `.env` File

Create a file named `.env` in the root of your frontend project:

```
REACT_APP_API_URL=http://localhost:8080
```

---

## Step 3: Create Theme Colors File

Create folder: `src/theme/`
Create file: `src/theme/colors.js`

```javascript
export const primaryColor = '#1976d2';
export const secondaryColor = '#1565c0';
export const successColor = '#4caf50';
export const accentColor = '#2196f3';
export const warningColor = '#ff9800';
export const errorColor = '#f44336';
```

---

## Step 4: Replace `src/components/Login.js`

```javascript
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Box,
  TextField,
  Button,
  Typography,
  Paper,
  Alert
} from '@mui/material';
import { primaryColor, secondaryColor } from '../theme/colors';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  // Check if already logged in
  useEffect(() => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');
    
    if (token && role) {
      // Redirect based on role
      const redirectMap = {
        'ADMIN': '/admin/dashboard',
        'FACULTY': '/faculty/dashboard',
        'STUDENT': '/student/dashboard'
      };
      navigate(redirectMap[role] || '/');
    }
  }, [navigate]);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const response = await axios.post(`${API_URL}/api/login`, {
        username,
        password
      });

      // Store token and user info
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('role', response.data.role);
      localStorage.setItem('username', response.data.username);

      // Redirect based on role
      const redirectUrl = response.data.redirectUrl;
      navigate(redirectUrl);
    } catch (err) {
      setError(err.response?.data?.error || 'Login failed. Please check your credentials.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        minHeight: '100vh',
        background: `linear-gradient(135deg, ${primaryColor} 0%, ${secondaryColor} 100%)`,
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        p: 2
      }}
    >
      <Container maxWidth="sm">
        <Paper elevation={10} sx={{ p: 4, borderRadius: 3 }}>
          <Box textAlign="center" mb={4}>
            <Typography variant="h4" fontWeight="bold" color={primaryColor}>
              Attendance Management System
            </Typography>
            <Typography variant="body2" color="text.secondary" mt={1}>
              Sign in to continue
            </Typography>
          </Box>

          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}

          <form onSubmit={handleLogin}>
            <TextField
              fullWidth
              label="Username"
              variant="outlined"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              margin="normal"
              required
              autoComplete="username"
            />

            <TextField
              fullWidth
              label="Password"
              type="password"
              variant="outlined"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              margin="normal"
              required
              autoComplete="current-password"
            />

            <Button
              type="submit"
              fullWidth
              variant="contained"
              size="large"
              sx={{
                mt: 3,
                py: 1.5,
                backgroundColor: primaryColor,
                '&:hover': { backgroundColor: secondaryColor },
              }}
              disabled={loading}
            >
              {loading ? 'Signing in...' : 'SIGN IN'}
            </Button>
          </form>

          <Box mt={3} textAlign="center">
            <Typography variant="caption" color="text.secondary">
              Test Credentials (password: test123)
            </Typography>
            <br />
            <Typography variant="caption" color="text.secondary">
              Admin: admin | Faculty: faculty1 | Student: student1
            </Typography>
          </Box>
        </Paper>
      </Container>
    </Box>
  );
};

export default Login;
```

---

## Step 5: Replace `src/App.js`

```javascript
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import AdminDashboard from './components/AdminDashboard';
import FacultyDashboard from './components/FacultyDashboard';
import StudentDashboard from './components/StudentDashboard';
import MarkAttendance from './components/MarkAttendance';
import './App.css';

// Protected Route Component
const ProtectedRoute = ({ children, allowedRoles }) => {
  const token = localStorage.getItem('token');
  const role = localStorage.getItem('role');

  if (!token) {
    return <Navigate to="/" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/" replace />;
  }

  return children;
};

function App() {
  return (
    <Router>
      <Routes>
        {/* Public Route */}
        <Route path="/" element={<Login />} />

        {/* Admin Routes */}
        <Route
          path="/admin/dashboard"
          element={
            <ProtectedRoute allowedRoles={['ADMIN']}>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        {/* Faculty Routes */}
        <Route
          path="/faculty/dashboard"
          element={
            <ProtectedRoute allowedRoles={['FACULTY']}>
              <FacultyDashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/faculty/mark-attendance"
          element={
            <ProtectedRoute allowedRoles={['FACULTY']}>
              <MarkAttendance />
            </ProtectedRoute>
          }
        />

        {/* Student Routes */}
        <Route
          path="/student/dashboard"
          element={
            <ProtectedRoute allowedRoles={['STUDENT']}>
              <StudentDashboard />
            </ProtectedRoute>
          }
        />

        {/* Catch all - redirect to login */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Router>
  );
}

export default App;
```

---

## Step 6: Create `src/components/AdminDashboard.js`

```javascript
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AddStudent from './AddStudent';
import AddFaculty from './AddFaculty';
import CreateClassroom from './CreateClassroom';
import {
  Box,
  Typography,
  Grid,
  Card,
  CardContent,
  Button,
  List,
  ListItem,
  ListItemText,
  ListItemIcon,
  Divider
} from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import ClassIcon from '@mui/icons-material/Class';
import LogoutIcon from '@mui/icons-material/Logout';
import SchoolIcon from '@mui/icons-material/School';
import { primaryColor } from '../theme/colors';

const AdminDashboard = () => {
  const [activeSection, setActiveSection] = useState('dashboard');
  const navigate = useNavigate();
  const username = localStorage.getItem('username') || 'Admin';

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    navigate('/');
  };

  const menuItems = [
    { id: 'dashboard', label: 'Dashboard', icon: <DashboardIcon /> },
    { id: 'addStudent', label: 'Add Student', icon: <PersonAddIcon /> },
    { id: 'addFaculty', label: 'Add Faculty', icon: <PersonAddIcon /> },
    { id: 'createClassroom', label: 'Create Classroom', icon: <ClassIcon /> },
  ];

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh', bgcolor: '#f5f5f5' }}>
      {/* Sidebar */}
      <Box
        sx={{
          width: 260,
          bgcolor: primaryColor,
          color: 'white',
          p: 2,
          display: 'flex',
          flexDirection: 'column'
        }}
      >
        <Typography variant="h6" fontWeight="bold" gutterBottom>
          <SchoolIcon sx={{ mr: 1, verticalAlign: 'middle' }} />
          Attendance System
        </Typography>
        <Divider sx={{ my: 2, bgcolor: 'rgba(255,255,255,0.3)' }} />
        
        <List>
          {menuItems.map((item) => (
            <ListItem
              button
              key={item.id}
              onClick={() => setActiveSection(item.id)}
              sx={{
                borderRadius: 2,
                mb: 1,
                bgcolor: activeSection === item.id ? 'rgba(255,255,255,0.2)' : 'transparent',
                '&:hover': { bgcolor: 'rgba(255,255,255,0.1)' }
              }}
            >
              <ListItemIcon sx={{ color: 'white' }}>{item.icon}</ListItemIcon>
              <ListItemText primary={item.label} />
            </ListItem>
          ))}
        </List>

        <Box sx={{ mt: 'auto' }}>
          <Button
            fullWidth
            startIcon={<LogoutIcon />}
            onClick={handleLogout}
            sx={{
              color: 'white',
              justifyContent: 'flex-start',
              '&:hover': { bgcolor: 'rgba(255,255,255,0.1)' }
            }}
          >
            Logout
          </Button>
        </Box>
      </Box>

      {/* Main Content */}
      <Box sx={{ flex: 1, p: 4 }}>
        {/* Top Bar */}
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 4 }}>
          <Typography variant="h5">
            Hi, {username}! Welcome back
          </Typography>
          <Button variant="outlined" onClick={handleLogout} startIcon={<LogoutIcon />}>
            Logout
          </Button>
        </Box>

        {/* Dashboard Content */}
        {activeSection === 'dashboard' && (
          <Grid container spacing={3}>
            <Grid item xs={12} sm={6} md={3}>
              <Card sx={{ bgcolor: '#4caf50', color: 'white' }}>
                <CardContent>
                  <Typography variant="h4">0</Typography>
                  <Typography variant="body2">CGPA</Typography>
                </CardContent>
              </Card>
            </Grid>
            <Grid item xs={12} sm={6} md={3}>
              <Card sx={{ bgcolor: '#ff9800', color: 'white' }}>
                <CardContent>
                  <Typography variant="h4">0</Typography>
                  <Typography variant="body2">Arrears in Hand</Typography>
                </CardContent>
              </Card>
            </Grid>
            <Grid item xs={12} sm={6} md={3}>
              <Card sx={{ bgcolor: '#2196f3', color: 'white' }}>
                <CardContent>
                  <Typography variant="h4">0%</Typography>
                  <Typography variant="body2">Average Attendance</Typography>
                </CardContent>
              </Card>
            </Grid>
            <Grid item xs={12} sm={6} md={3}>
              <Card sx={{ bgcolor: '#f44336', color: 'white' }}>
                <CardContent>
                  <Typography variant="h4">0</Typography>
                  <Typography variant="body2">Taken Leave</Typography>
                </CardContent>
              </Card>
            </Grid>

            <Grid item xs={12}>
              <Card sx={{ mt: 3 }}>
                <CardContent>
                  <Typography variant="h6" gutterBottom>Announcements</Typography>
                  <Typography color="text.secondary">No Announcements</Typography>
                </CardContent>
              </Card>
            </Grid>
          </Grid>
        )}

        {activeSection === 'addStudent' && <AddStudent />}
        {activeSection === 'addFaculty' && <AddFaculty />}
        {activeSection === 'createClassroom' && <CreateClassroom />}
      </Box>
    </Box>
  );
};

export default AdminDashboard;
```

---

## Step 7: Replace `src/components/FacultyDashboard.js`

```javascript
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  CardActionArea,
  Box,
  Button
} from '@mui/material';
import LogoutIcon from '@mui/icons-material/Logout';
import { primaryColor, successColor, accentColor, warningColor } from '../theme/colors';

const FacultyDashboard = () => {
  const navigate = useNavigate();
  const [facultyName, setFacultyName] = useState('Faculty');

  useEffect(() => {
    const name = localStorage.getItem('username') || 'Faculty';
    setFacultyName(name);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    navigate('/');
  };

  const actions = [
    {
      title: 'Mark\nAttendance',
      icon: '📅',
      color: successColor,
      path: '/faculty/mark-attendance',
    },
    {
      title: 'View\nAttendance',
      icon: '👁️',
      color: accentColor,
      path: '/faculty/view-attendance',
    },
    {
      title: "Today's\nSummary",
      icon: '📊',
      color: warningColor,
      path: '/faculty/today-summary',
    },
  ];

  return (
    <Box sx={{ minHeight: '100vh', bgcolor: '#f8f9fa', pt: 4 }}>
      <Container maxWidth="lg">
        {/* Top Bar */}
        <Box sx={{ display: 'flex', justifyContent: 'flex-end', mb: 2 }}>
          <Button
            variant="outlined"
            startIcon={<LogoutIcon />}
            onClick={handleLogout}
          >
            Logout
          </Button>
        </Box>

        {/* Welcome Card */}
        <Card
          sx={{
            mb: 5,
            background: `linear-gradient(135deg, ${primaryColor} 0%, ${accentColor} 100%)`,
            color: 'white',
            borderRadius: 4,
            boxShadow: 6,
          }}
        >
          <CardContent sx={{ p: 5 }}>
            <Typography variant="h6" sx={{ opacity: 0.9 }}>
              Welcome back,
            </Typography>
            <Typography variant="h4" fontWeight="bold" mt={1}>
              {facultyName}
            </Typography>
            <Typography variant="body1" mt={2} sx={{ opacity: 0.85 }}>
              Manage your class attendance efficiently
            </Typography>
          </CardContent>
        </Card>

        <Typography variant="h5" gutterBottom fontWeight="600" color="text.primary">
          Quick Actions
        </Typography>

        <Grid container spacing={3}>
          {actions.map((action, idx) => (
            <Grid item xs={12} sm={6} md={4} key={idx}>
              <Card elevation={4} sx={{ borderRadius: 3, overflow: 'hidden' }}>
                <CardActionArea
                  onClick={() => navigate(action.path)}
                  sx={{
                    height: '100%',
                    p: 3,
                    textAlign: 'center',
                    background: `linear-gradient(135deg, ${action.color}10 0%, ${action.color}20 100%)`,
                  }}
                >
                  <Box sx={{ mb: 2 }}>
                    <Typography variant="h3">{action.icon}</Typography>
                  </Box>
                  <Typography variant="h6" fontWeight="bold" color="text.primary" sx={{ whiteSpace: 'pre-line' }}>
                    {action.title}
                  </Typography>
                </CardActionArea>
              </Card>
            </Grid>
          ))}
        </Grid>
      </Container>
    </Box>
  );
};

export default FacultyDashboard;
```

---

## Step 8: Create `src/components/StudentDashboard.js`

```javascript
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import {
  Container,
  Typography,
  Grid,
  Card,
  CardContent,
  Box,
  Button,
  CircularProgress
} from '@mui/material';
import SchoolIcon from '@mui/icons-material/School';
import EventIcon from '@mui/icons-material/Event';
import TrendingUpIcon from '@mui/icons-material/TrendingUp';
import LogoutIcon from '@mui/icons-material/Logout';
import { primaryColor, successColor, warningColor } from '../theme/colors';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const StudentDashboard = () => {
  const navigate = useNavigate();
  const [studentName, setStudentName] = useState('Student');
  const [attendanceData, setAttendanceData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const name = localStorage.getItem('username') || 'Student';
    setStudentName(name);
    // fetchAttendanceData();
    setLoading(false);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    navigate('/');
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box sx={{ minHeight: '100vh', bgcolor: '#f8f9fa', pt: 4 }}>
      <Container maxWidth="lg">
        {/* Top Bar */}
        <Box sx={{ display: 'flex', justifyContent: 'flex-end', mb: 2 }}>
          <Button
            variant="outlined"
            startIcon={<LogoutIcon />}
            onClick={handleLogout}
          >
            Logout
          </Button>
        </Box>

        {/* Welcome Card */}
        <Card
          sx={{
            mb: 5,
            background: `linear-gradient(135deg, ${primaryColor} 0%, ${successColor} 100%)`,
            color: 'white',
            borderRadius: 4,
            boxShadow: 6,
          }}
        >
          <CardContent sx={{ p: 5 }}>
            <Typography variant="h6" sx={{ opacity: 0.9 }}>
              Welcome,
            </Typography>
            <Typography variant="h4" fontWeight="bold" mt={1}>
              {studentName}
            </Typography>
            <Typography variant="body1" mt={2} sx={{ opacity: 0.85 }}>
              Check your attendance and academic progress
            </Typography>
          </CardContent>
        </Card>

        {/* Stats Cards */}
        <Typography variant="h5" gutterBottom fontWeight="600" color="text.primary">
          Your Progress
        </Typography>

        <Grid container spacing={3}>
          <Grid item xs={12} sm={6} md={4}>
            <Card elevation={3} sx={{ borderRadius: 3 }}>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  <SchoolIcon sx={{ fontSize: 40, color: primaryColor, mr: 2 }} />
                  <Box>
                    <Typography variant="h4" fontWeight="bold">0</Typography>
                    <Typography variant="body2" color="text.secondary">CGPA</Typography>
                  </Box>
                </Box>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} sm={6} md={4}>
            <Card elevation={3} sx={{ borderRadius: 3 }}>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  <TrendingUpIcon sx={{ fontSize: 40, color: successColor, mr: 2 }} />
                  <Box>
                    <Typography variant="h4" fontWeight="bold">0%</Typography>
                    <Typography variant="body2" color="text.secondary">Attendance</Typography>
                  </Box>
                </Box>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} sm={6} md={4}>
            <Card elevation={3} sx={{ borderRadius: 3 }}>
              <CardContent>
                <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                  <EventIcon sx={{ fontSize: 40, color: warningColor, mr: 2 }} />
                  <Box>
                    <Typography variant="h4" fontWeight="bold">0</Typography>
                    <Typography variant="body2" color="text.secondary">Arrears</Typography>
                  </Box>
                </Box>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
};

export default StudentDashboard;
```

---

## Step 9: Replace `src/components/MarkAttendance.js`

```javascript
import React, { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  CircularProgress,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  List,
  ListItem,
  ListItemText,
  ListItemIcon,
  IconButton,
  Alert
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers';
import dayjs from 'dayjs';
import axios from 'axios';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import DirectionsRunIcon from '@mui/icons-material/DirectionsRun';
import LogoutIcon from '@mui/icons-material/Logout';
import { useNavigate } from 'react-router-dom';
import { primaryColor } from '../theme/colors';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const MarkAttendance = () => {
  const navigate = useNavigate();
  const [selectedDate, setSelectedDate] = useState(dayjs());
  const [classes, setClasses] = useState([]);
  const [selectedClass, setSelectedClass] = useState('');
  const [students, setStudents] = useState([]);
  const [attendance, setAttendance] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    fetchFacultyClasses();
  }, []);

  useEffect(() => {
    if (selectedClass) {
      fetchStudents();
    }
  }, [selectedClass]);

  const fetchFacultyClasses = async () => {
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${API_URL}/api/faculty/my-classes`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setClasses(res.data);
      if (res.data.length > 0) setSelectedClass(res.data[0].id);
    } catch (err) {
      setError('Failed to load classes');
      console.error(err);
    }
  };

  const fetchStudents = async () => {
    setLoading(true);
    setError('');
    try {
      const token = localStorage.getItem('token');
      const res = await axios.get(`${API_URL}/api/faculty/classroom/${selectedClass}/students`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      const studentList = res.data;
      setStudents(studentList);

      const initialAttendance = {};
      studentList.forEach((s) => {
        initialAttendance[s.regdNumber] = 'PRESENT';
      });
      setAttendance(initialAttendance);
    } catch (err) {
      setError('Failed to load students');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const toggleStatus = (regdNumber, newStatus) => {
    setAttendance((prev) => ({
      ...prev,
      [regdNumber]: newStatus,
    }));
  };

  const handleSubmit = async () => {
    setLoading(true);
    setError('');
    setSuccess('');
    try {
      const token = localStorage.getItem('token');
      const payload = {
        classId: selectedClass,
        date: selectedDate.format('YYYY-MM-DD'),
        attendance: attendance,
      };

      await axios.post(`${API_URL}/api/faculty/attendance/mark`, payload, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setSuccess('Attendance submitted successfully!');
    } catch (err) {
      setError('Failed to submit attendance');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    navigate('/');
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'PRESENT': return '#4caf50';
      case 'ABSENT': return '#f44336';
      case 'OD_INTERNAL': return '#9c27b0';
      case 'OD_EXTERNAL': return '#673ab7';
      default: return '#757575';
    }
  };

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Container maxWidth="md" sx={{ py: 4 }}>
        {/* Top Bar */}
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
          <Typography variant="h4" fontWeight="bold">
            Mark Attendance
          </Typography>
          <Button variant="outlined" startIcon={<LogoutIcon />} onClick={handleLogout}>
            Logout
          </Button>
        </Box>

        {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
        {success && <Alert severity="success" sx={{ mb: 2 }}>{success}</Alert>}

        <Card sx={{ mb: 4 }}>
          <CardContent>
            <Box sx={{ display: 'flex', gap: 3, alignItems: 'center', flexWrap: 'wrap' }}>
              <FormControl sx={{ minWidth: 160 }}>
                <InputLabel>Class / Section</InputLabel>
                <Select
                  value={selectedClass}
                  label="Class / Section"
                  onChange={(e) => setSelectedClass(e.target.value)}
                >
                  {classes.map((cls) => (
                    <MenuItem key={cls.id} value={cls.id}>
                      {cls.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>

              <DatePicker
                label="Date"
                value={selectedDate}
                onChange={(newValue) => setSelectedDate(newValue)}
                sx={{ width: 180 }}
              />
            </Box>
          </CardContent>
        </Card>

        {loading ? (
          <Box textAlign="center" py={6}>
            <CircularProgress />
          </Box>
        ) : (
          <>
            <Typography variant="h6" gutterBottom>
              Students ({students.length})
            </Typography>

            <List sx={{ bgcolor: 'white', borderRadius: 2, boxShadow: 2 }}>
              {students.map((student) => {
                const status = attendance[student.regdNumber] || 'PRESENT';
                return (
                  <ListItem
                    key={student.regdNumber}
                    secondaryAction={
                      <Box sx={{ display: 'flex', gap: 1 }}>
                        <IconButton
                          color={status === 'PRESENT' ? 'success' : 'default'}
                          onClick={() => toggleStatus(student.regdNumber, 'PRESENT')}
                        >
                          <CheckIcon />
                        </IconButton>
                        <IconButton
                          color={status === 'ABSENT' ? 'error' : 'default'}
                          onClick={() => toggleStatus(student.regdNumber, 'ABSENT')}
                        >
                          <CloseIcon />
                        </IconButton>
                        <IconButton
                          color={status.includes('OD') ? 'secondary' : 'default'}
                          onClick={() =>
                            toggleStatus(
                              student.regdNumber,
                              status === 'OD_INTERNAL' ? 'PRESENT' : 'OD_INTERNAL'
                            )
                          }
                        >
                          <DirectionsRunIcon />
                        </IconButton>
                      </Box>
                    }
                  >
                    <ListItemText
                      primary={student.name}
                      secondary={`Reg: ${student.regdNumber}`}
                    />
                    <Box
                      sx={{
                        width: 12,
                        height: 12,
                        borderRadius: '50%',
                        bgcolor: getStatusColor(status),
                        ml: 2,
                      }}
                    />
                  </ListItem>
                );
              })}
            </List>

            <Box mt={4} textAlign="center">
              <Button
                variant="contained"
                size="large"
                onClick={handleSubmit}
                disabled={loading}
                sx={{
                  px: 6,
                  py: 1.5,
                  backgroundColor: primaryColor,
                  '&:hover': { backgroundColor: '#1565c0' },
                }}
              >
                {loading ? 'Submitting...' : 'SUBMIT ATTENDANCE'}
              </Button>
            </Box>
          </>
        )}
      </Container>
    </LocalizationProvider>
  );
};

export default MarkAttendance;
```

---

## Step 10: Update Existing Components

Update `AddStudent.js`, `AddFaculty.js`, and `CreateClassroom.js` to use MUI components and add logout functionality.

---

## Step 11: Run the Application

```bash
npm start
```

---

## Test Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | test123 |
| Faculty | faculty1 | test123 |
| Student | student1 | test123 |

---

## Troubleshooting

1. **CORS Error**: Ensure backend CORS allows `http://localhost:3000`
2. **Login Fails**: Check if users exist in database (run SQL script)
3. **401 Unauthorized**: Verify token is being stored and sent
4. **Module not found**: Run `npm install` for missing dependencies
