package com.designathon.nullpointers.attendancebackend.service;

import com.designathon.nullpointers.attendancebackend.entity.User;
import com.designathon.nullpointers.attendancebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    // For initial setup, but not used in controllers yet
    public User saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }
}