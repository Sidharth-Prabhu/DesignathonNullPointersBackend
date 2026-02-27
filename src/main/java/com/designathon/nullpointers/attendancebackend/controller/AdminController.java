package com.designathon.nullpointers.attendancebackend.controller;

import com.designathon.nullpointers.attendancebackend.entity.Classroom;
import com.designathon.nullpointers.attendancebackend.entity.Faculty;
import com.designathon.nullpointers.attendancebackend.entity.Student;
import com.designathon.nullpointers.attendancebackend.service.ClassroomService;
import com.designathon.nullpointers.attendancebackend.service.FacultyService;
import com.designathon.nullpointers.attendancebackend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private ClassroomService classroomService;

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/faculties")
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @GetMapping("/faculties")
    public List<Faculty> getFaculties() {
        return facultyService.getAllFaculties();
    }

    @PostMapping("/classrooms")
    public Classroom createClassroom(@RequestBody Classroom classroom) {
        return classroomService.createClassroom(classroom);
    }

    @PostMapping("/classrooms/{id}/students")
    public Classroom addStudents(@PathVariable Long id, @RequestBody List<Long> studentIds) {
        return classroomService.addStudentsToClassroom(id, studentIds);
    }

    @GetMapping("/classrooms")
    public List<Classroom> getClassrooms() {
        return classroomService.getAllClassrooms();
    }
}