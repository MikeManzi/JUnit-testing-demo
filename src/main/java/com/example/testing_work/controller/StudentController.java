package com.example.testing_work.controller;

import com.example.testing_work.dto.UpdateStudentDto;
import com.example.testing_work.model.Student;
import com.example.testing_work.service.StudentService;
import com.example.testing_work.utils.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    StudentService studentService;

//    @Autowired
//    StudentRepository studentRepository;

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

   @PostMapping("/new")
    public ResponseEntity<?> saveStudent(@Valid Student student){
       if(studentService.existsByEmail(student.getEmail())){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body(new APIResponse(false, "Student already registered"));
       }
       Student s = studentService.addStudent(student);
       return ResponseEntity.status(HttpStatus.CREATED).body(s);
   }


   @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable(name = "id") int id,
                                           @Valid UpdateStudentDto dto){
       Student student = studentService.getById(id);
       if(student != null){
           if(studentService.existsByEmail(dto.getEmail())
                   && !(student.getEmail().equalsIgnoreCase(dto.getEmail()))){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body(new APIResponse(false,"Student already registered"));
           }
           Student updatedStudent = studentService.updateStudent(id, dto);
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedStudent);
       }

       return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body(new APIResponse(false,"Student Not found"));

   }

   @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(name = "id") int id){
       return studentService.deleteStudent(id);
   }

}
