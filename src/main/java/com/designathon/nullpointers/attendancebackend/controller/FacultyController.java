package com.designathon.nullpointers.attendancebackend.controller;

import com.designathon.nullpointers.attendancebackend.entity.Attendance;
import com.designathon.nullpointers.attendancebackend.entity.Classroom;
import com.designathon.nullpointers.attendancebackend.entity.Student;
import com.designathon.nullpointers.attendancebackend.service.AttendanceService;
import com.designathon.nullpointers.attendancebackend.service.ClassroomService;
import com.designathon.nullpointers.attendancebackend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/faculty")
@PreAuthorize("hasRole('FACULTY')")
public class FacultyController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AttendanceService attendanceService;

    // Get all classrooms assigned to faculty (for now, return all classrooms)
    @GetMapping("/my-classes")
    public List<Classroom> getMyClasses() {
        return classroomService.getAllClassrooms();
    }

    // Get students in a specific classroom
    @GetMapping("/classroom/{id}/students")
    public List<Student> getClassroomStudents(@PathVariable Long id) {
        Classroom classroom = classroomService.getAllClassrooms().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (classroom == null) {
            return new ArrayList<>();
        }
        return classroom.getStudents();
    }

    // Mark attendance for a classroom
    @PostMapping("/attendance/mark")
    public ResponseEntity<?> markAttendance(@RequestBody Map<String, Object> payload) {
        try {
            Long classId = Long.valueOf(payload.get("classId").toString());
            String dateStr = payload.get("date").toString();
            LocalDate date = LocalDate.parse(dateStr);
            
            @SuppressWarnings("unchecked")
            Map<String, String> attendanceMap = (Map<String, String>) payload.get("attendance");
            
            // Get all students in the classroom
            List<Student> students = getClassroomStudents(classId);
            
            // Save attendance for each student
            for (Student student : students) {
                String status = attendanceMap.getOrDefault(student.getRegdNumber(), "PRESENT");
                
                Attendance attendance = new Attendance();
                attendance.setStudent(student);
                
                Classroom classroom = new Classroom();
                classroom.setId(classId);
                attendance.setClassroom(classroom);
                
                attendance.setDate(date);
                attendance.setStatus(status);
                
                attendanceService.saveAttendance(attendance);
            }
            
            return ResponseEntity.ok(Map.of("message", "Attendance marked successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Get attendance summary for today
    @GetMapping("/attendance/today")
    public Map<String, Object> getTodayAttendance() {
        LocalDate today = LocalDate.now();
        List<Attendance> todayRecords = attendanceService.getAttendanceByDate(today);
        
        Map<String, Object> response = new HashMap<>();
        response.put("date", today);
        response.put("totalRecords", todayRecords.size());
        response.put("attendance", todayRecords);
        
        return response;
    }
}
