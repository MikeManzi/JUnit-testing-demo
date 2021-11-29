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

    public Student addStudent(Student student){

        return studentRepository.save(student);
    }

    public Student updateStudent(int id, UpdateStudentDto dto){
        Optional<Student> findById = studentRepository.findById(id);

        if(findById.isPresent()){
            Student student = findById.get();
            student.setFirstName(dto.getFirstName());
            student.setLastName(dto.getLastName());
            student.setEmail(dto.getEmail());
            return studentRepository.save(student);
        }
        return null;
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

    public boolean existsByEmail(String email){
        return studentRepository.existsByEmail(email);
    }
}
