package com.designathon.nullpointers.attendancebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String regdNumber;
    private String dept;
    private String phone;
    private String email;
}