package com.designathon.nullpointers.attendancebackend.repository;

import com.designathon.nullpointers.attendancebackend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByClassroomIdAndDate(Long classroomId, LocalDate date);
}
