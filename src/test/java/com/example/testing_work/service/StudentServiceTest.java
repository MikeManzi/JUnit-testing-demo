package com.example.testing_work.service;

import com.example.testing_work.model.Student;
import com.example.testing_work.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void getAllStudents(){
        when(studentRepository.findAll())
                .thenReturn(Arrays.asList(new Student(1,"MANZI","Mike","mike@gmail.com"),
                        new Student(2,"Taylor","Swift","tswift@gmail.com")));
        assertEquals("MANZI",studentService.getAll().get(0).getFirstName());
    }
}
