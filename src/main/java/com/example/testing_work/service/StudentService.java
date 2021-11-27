package com.example.testing_work.service;

import com.example.testing_work.dto.UpdateStudentDto;
import com.example.testing_work.model.Student;
import com.example.testing_work.repository.StudentRepository;
import com.example.testing_work.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAll(){
        return studentRepository.findAll();
    }

    public Student getById(int id){
        Optional<Student> student = studentRepository.findById(id);
        return student.orElse(null);
    }

    public ResponseEntity<Student> addStudent(Student student){
        Student s = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(s);
    }

    public ResponseEntity<?> updateStudent(int id, UpdateStudentDto dto){
        Optional<Student> findById = studentRepository.findById(id);

        if(findById.isPresent()){
            Student student = findById.get();
            if(studentRepository.existsByEmail(dto.getEmail())
                    && !(student.getEmail().equalsIgnoreCase(dto.getEmail()))){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse(false,"Student already registered"));
            }
            student.setFirstName(dto.getFirstName());
            student.setLastName(dto.getLastName());
            student.setEmail(dto.getEmail());
            studentRepository.save(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(student);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIResponse(false,"No student with that id"));
    }

    public ResponseEntity<?> deleteStudent(int id){
        Optional<Student> findById = studentRepository.findById(id);

        if(findById.isPresent()){
            studentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse(true,"Student was deleted"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIResponse(false, "No student with that id"));
    }
}
