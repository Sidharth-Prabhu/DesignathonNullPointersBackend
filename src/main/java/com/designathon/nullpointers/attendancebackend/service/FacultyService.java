package com.designathon.nullpointers.attendancebackend.service;

import com.designathon.nullpointers.attendancebackend.entity.Faculty;
import com.designathon.nullpointers.attendancebackend.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private FacultyRepository repository;

    public Faculty addFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public List<Faculty> getAllFaculties() {
        return repository.findAll();
    }

}