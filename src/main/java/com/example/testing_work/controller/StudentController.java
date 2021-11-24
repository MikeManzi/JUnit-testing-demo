package com.example.testing_work.controller;

import com.example.testing_work.model.Student;

import com.example.testing_work.service.StudentService;
import com.example.testing_work.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    StudentService studentService;

   @GetMapping("/all")
    public List<Student> getAll(){
      return studentService.getAll();
   }

   @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id){
       Student student = studentService.getById(id);
       if(student != null) return ResponseEntity.status(HttpStatus.OK).body(student);

       return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(new APIResponse(false, "Student not found"));
   }



}
