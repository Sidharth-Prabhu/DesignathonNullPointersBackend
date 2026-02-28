package com.designathon.nullpointers.attendancebackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Attendance {
    @Id


    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false, length = 20)
    private String status; // PRESENT, ABSENT, OD_INTERNAL, OD_EXTERNAL
}
