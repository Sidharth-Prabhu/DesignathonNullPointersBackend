package com.designathon.nullpointers.attendancebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @Column(name = "staff_code")
    private String staffCode;
    private String dept;
    private String phone;
    private String email;
}