package com.example.testing_work.service;

import com.example.testing_work.model.Student;
import com.example.testing_work.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAll(){
        List<Student> students = studentRepository.findAll();
        return students;
    }

    public Student getById(int id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            return student.get();
        }
        return null;
    }

    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student s = studentRepository.save(student);
        return ResponseEntity.ok(s);
    }

}
