package com.designathon.nullpointers.attendancebackend.controller;

import com.designathon.nullpointers.attendancebackend.entity.Classroom;
import com.designathon.nullpointers.attendancebackend.entity.Faculty;
import com.designathon.nullpointers.attendancebackend.entity.Student;
import com.designathon.nullpointers.attendancebackend.entity.User;
import com.designathon.nullpointers.attendancebackend.service.ClassroomService;
import com.designathon.nullpointers.attendancebackend.service.FacultyService;
import com.designathon.nullpointers.attendancebackend.service.StudentService;
import com.designathon.nullpointers.attendancebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private UserService userService;

    @PostMapping("/students")
    public ResponseEntity<?> addStudent(@RequestBody Map<String, String> studentData) {
        try {
            // Create User account for student
            User user = new User();
            user.setUsername(studentData.get("username"));
            user.setPassword(studentData.get("password"));
            user.setRole("STUDENT");
            user = userService.saveUser(user);
            
            // Create Student profile
            Student student = new Student();
            student.setUser(user);
            student.setRegdNumber(studentData.get("regdNumber"));
            student.setDept(studentData.get("dept"));
            student.setPhone(studentData.get("phone"));
            student.setEmail(studentData.get("email"));
            
            return ResponseEntity.ok(studentService.addStudent(student));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/faculties")
    public ResponseEntity<?> addFaculty(@RequestBody Map<String, String> facultyData) {
        try {
            // Create User account for faculty
            User user = new User();
            user.setUsername(facultyData.get("username"));
            user.setPassword(facultyData.get("password"));
            user.setRole("FACULTY");
            user = userService.saveUser(user);
            
            // Create Faculty profile
            Faculty faculty = new Faculty();
            faculty.setUser(user);
            faculty.setStaffCode(facultyData.get("staffCode"));
            faculty.setDept(facultyData.get("dept"));
            faculty.setPhone(facultyData.get("phone"));
            faculty.setEmail(facultyData.get("email"));
            
            return ResponseEntity.ok(facultyService.addFaculty(faculty));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/faculties")
    public List<Faculty> getFaculties() {
        return facultyService.getAllFaculties();
    }

    @PostMapping("/classrooms")
    public Classroom createClassroom(@RequestBody Classroom classroom) {
        return classroomService.createClassroom(classroom);
    }

    @PostMapping("/classrooms/{id}/students")
    public Classroom addStudents(@PathVariable Long id, @RequestBody List<Long> studentIds) {
        return classroomService.addStudentsToClassroom(id, studentIds);
    }

    @GetMapping("/classrooms")
    public List<Classroom> getClassrooms() {
        return classroomService.getAllClassrooms();
    }
}