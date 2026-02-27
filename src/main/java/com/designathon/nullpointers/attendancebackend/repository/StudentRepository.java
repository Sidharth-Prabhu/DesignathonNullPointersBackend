package com.designathon.nullpointers.attendancebackend.repository;

import com.designathon.nullpointers.attendancebackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {}