package com.example.testing_work.service;

import com.example.testing_work.dto.UpdateStudentDto;
import com.example.testing_work.model.Student;
import com.example.testing_work.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepositoryMock;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void getAllStudents(){
        when(studentRepositoryMock.findAll())
                .thenReturn(Arrays.asList(new Student(1,"MANZI","Mike","mike@gmail.com"),
                        new Student(2,"Taylor","Swift","tswift@gmail.com")));
        assertEquals("MANZI",studentService.getAll().get(0).getFirstName());
    }

    @Test
    public void getStudentById_success(){
        when(studentRepositoryMock.findById(1))
                .thenReturn(Optional.of(new Student(1, "MANZI", "Mike", "mike@gmail.com")));

        assertEquals("Mike",studentService.getById(1).getLastName());
    }

    @Test
    public void getStudentById_404(){
        when(studentRepositoryMock.findById(6)).thenReturn(Optional.empty());
        Student student = studentService.getById(6);
        assertNull(student);
    }

    @Test
    public void updateStudent_success(){
        UpdateStudentDto dto = new UpdateStudentDto("Miley","Cyrus","miley@gmail.com");
        Student student = new Student(104,"Ava","Max","avamax@gmail.com");

        when(studentRepositoryMock.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepositoryMock.existsByEmail(dto.getEmail())).thenReturn(false);
        when(studentRepositoryMock.save(student)).thenReturn(student);

        ResponseEntity<?> updatedStudent = studentService.updateStudent(104,dto);
        assertTrue(updatedStudent.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void updateStudent_404(){
        UpdateStudentDto dto = new UpdateStudentDto("Miley","Cyrus","miley@gmail.com");
        when(studentRepositoryMock.findById(104)).thenReturn(Optional.empty());

        ResponseEntity<?> updatedStudent = studentService.updateStudent(104,dto);
        assertEquals(404, updatedStudent.getStatusCodeValue());

    }

    @Test
    public void updateStudent_emailExists(){
        UpdateStudentDto dto = new UpdateStudentDto("Miley","Cyrus","avamax@gmail.com");
        Student student = new Student(104,"Ava","Max","avamax@gmail.com");
        when(studentRepositoryMock.existsByEmail(dto.getEmail())).thenReturn(true);
        ResponseEntity<?> updatedStudent = studentService.updateStudent(104,dto);
        assertEquals(404,updatedStudent.getStatusCodeValue());

    }

    @Test
    public void delete_success(){
        Student student = new Student(104,"Ava","Max","avamax@gmail.com");
        when(studentRepositoryMock.findById(104)).thenReturn(Optional.of(student));
        ResponseEntity<?> deletedStudent = studentService.deleteStudent(104);
        assertEquals(200, deletedStudent.getStatusCodeValue());
    }

    @Test
    public void delete_404(){
        when(studentRepositoryMock.findById(104)).thenReturn(Optional.empty());
        ResponseEntity<?> deletedStudent = studentService.deleteStudent(104);
        assertEquals(404,deletedStudent.getStatusCodeValue());
    }




}
