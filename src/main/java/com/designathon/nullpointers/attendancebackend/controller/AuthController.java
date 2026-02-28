package com.designathon.nullpointers.attendancebackend.controller;

import com.designathon.nullpointers.attendancebackend.entity.User;
import com.designathon.nullpointers.attendancebackend.security.JwtUtil;
import com.designathon.nullpointers.attendancebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.findByUsername(username);
            if (user == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
            }

            String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
            
            // Build response with redirect URL based on role
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", user.getRole());
            response.put("redirectUrl", getRedirectUrl(user.getRole()));
            response.put("username", user.getUsername());
            
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    private String getRedirectUrl(String role) {
        if (role == null) {
            return "/";
        }
        return switch (role.toUpperCase()) {
            case "ADMIN" -> "/admin/dashboard";
            case "FACULTY" -> "/faculty/dashboard";
            case "STUDENT" -> "/student/dashboard";
            default -> "/";
        };
    }
}