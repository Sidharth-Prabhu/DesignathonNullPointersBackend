package com.designathon.nullpointers.attendancebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "regd_number")
    private String regdNumber;

    private String dept;
    private String phone;
    private String email;

    // Helper method to get name from User entity
    public String getName() {
        return user != null ? user.getUsername() : "";
    }

    // Helper method to set name in User entity
    public void setName(String name) {
        if (this.user != null) {
            this.user.setUsername(name);
        }
    }
}