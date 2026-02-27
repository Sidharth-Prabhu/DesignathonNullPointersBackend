package com.designathon.nullpointers.attendancebackend.repository;

import com.designathon.nullpointers.attendancebackend.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {}