package com.designathon.nullpointers.attendancebackend.service;

import com.designathon.nullpointers.attendancebackend.entity.Attendance;
import com.designathon.nullpointers.attendancebackend.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository repository;

    public Attendance saveAttendance(Attendance attendance) {
        return repository.save(attendance);
    }

    public List<Attendance> getAllAttendance() {
        return repository.findAll();
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public List<Attendance> getAttendanceByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    public List<Attendance> getAttendanceByClassroomAndDate(Long classroomId, LocalDate date) {
        return repository.findByClassroomIdAndDate(classroomId, date);
    }
}
