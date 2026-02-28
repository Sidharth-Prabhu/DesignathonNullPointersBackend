package com.designathon.nullpointers.attendancebackend.controller;

import com.designathon.nullpointers.attendancebackend.entity.Attendance;
import com.designathon.nullpointers.attendancebackend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    @Autowired
    private AttendanceService attendanceService;

    // Get my attendance history
    @GetMapping("/my-attendance")
    public Map<String, Object> getMyAttendance(@RequestParam Long studentId) {
        List<Attendance> attendanceRecords = attendanceService.getAttendanceByStudent(studentId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("studentId", studentId);
        response.put("totalRecords", attendanceRecords.size());
        response.put("attendance", attendanceRecords);
        
        // Calculate attendance percentage
        long totalDays = attendanceRecords.size();
        long presentDays = attendanceRecords.stream()
                .filter(a -> "PRESENT".equals(a.getStatus()))
                .count();
        
        double percentage = totalDays > 0 ? (presentDays * 100.0 / totalDays) : 0;
        response.put("attendancePercentage", Math.round(percentage * 100.0) / 100.0);
        
        return response;
    }

    // Get my attendance summary
    @GetMapping("/attendance-summary")
    public Map<String, Object> getAttendanceSummary(@RequestParam Long studentId) {
        List<Attendance> allRecords = attendanceService.getAttendanceByStudent(studentId);
        
        Map<String, Long> summary = new HashMap<>();
        summary.put("present", allRecords.stream().filter(a -> "PRESENT".equals(a.getStatus())).count());
        summary.put("absent", allRecords.stream().filter(a -> "ABSENT".equals(a.getStatus())).count());
        summary.put("odInternal", allRecords.stream().filter(a -> "OD_INTERNAL".equals(a.getStatus())).count());
        summary.put("odExternal", allRecords.stream().filter(a -> "OD_EXTERNAL".equals(a.getStatus())).count());
        
        Map<String, Object> response = new HashMap<>();
        response.put("studentId", studentId);
        response.put("summary", summary);
        response.put("totalDays", allRecords.size());
        
        return response;
    }
}
