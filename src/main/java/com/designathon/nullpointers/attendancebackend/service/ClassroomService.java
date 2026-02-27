package com.designathon.nullpointers.attendancebackend.service;

import com.designathon.nullpointers.attendancebackend.entity.Classroom;
import com.designathon.nullpointers.attendancebackend.entity.Student;
import com.designathon.nullpointers.attendancebackend.repository.ClassroomRepository;
import com.designathon.nullpointers.attendancebackend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {
    @Autowired
    private ClassroomRepository repository;
    @Autowired
    private StudentRepository studentRepository;

    public Classroom createClassroom(Classroom classroom) {
        return repository.save(classroom);
    }

    public Classroom addStudentsToClassroom(Long classroomId, List<Long> studentIds) {
        Classroom classroom = repository.findById(classroomId).orElseThrow();
        List<Student> students = studentRepository.findAllById(studentIds);
        classroom.getStudents().addAll(students);
        return repository.save(classroom);
    }

    public List<Classroom> getAllClassrooms() {
        return repository.findAll();
    }
}