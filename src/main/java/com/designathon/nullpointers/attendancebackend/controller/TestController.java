package com.designathon.nullpointers.attendancebackend.controller;

import com.designathon.nullpointers.attendancebackend.entity.User;
import com.designathon.nullpointers.attendancebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TestController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/api/test/users")
    public List<Map<String, Object>> getAllUsers() {
        // Debug endpoint - shows all users (passwords partially masked)
        return userRepository.findAll().stream().map(user -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("role", user.getRole());
            map.put("password_length", user.getPassword().length());
            map.put("password_starts_with", user.getPassword().substring(0, Math.min(10, user.getPassword().length())));
            return map;
        }).collect(Collectors.toList());
    }
}
