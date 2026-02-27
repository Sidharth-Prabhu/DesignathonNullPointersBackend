package com.designathon.nullpointers.attendancebackend.repository;

import com.designathon.nullpointers.attendancebackend.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {}