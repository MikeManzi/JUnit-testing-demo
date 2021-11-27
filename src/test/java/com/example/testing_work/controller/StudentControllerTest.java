package com.example.testing_work.controller;

import com.example.testing_work.model.Student;
import com.example.testing_work.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class StudentControllerTest {

    @MockBean
    private StudentService studentServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAll() throws Exception {
        List<Student> students = Arrays.asList(new Student(1, "MANZI", "Mike", "mike@gmail.com"),
                new Student(2, "Taylor", "Swift", "tswift@gmail.com"));
        when(studentServiceMock.getAll()).thenReturn(students);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/students/all")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"firstName\": \"MANZI\",\"lastName\": \"Mike\",\"email\": \"mike@gmail.com\"},{\"id\":2,\"firstName\": \"Taylor\",\"lastName\": \"Swift\",\"email\": \"tswift@gmail.com\"}]"))
                .andReturn();
    }

    @Test
    public void getOne_success() throws Exception {
        Student student = new Student(1, "MANZI", "Mike", "mike@gmail.com");
        when(studentServiceMock.getById(student.getId())).thenReturn(student);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/students/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"firstName\": \"MANZI\",\"lastName\": \"Mike\",\"email\": \"mike@gmail.com\"}"))
                .andReturn();
    }

    @Test
    public void getOne_404() throws Exception{
        Student student = new Student(1, "MANZI", "Mike", "mike@gmail.com");
        when(studentServiceMock.getById(1)).thenReturn(null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/students/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                //.andExpect(content().string(""))
                .andExpect(content().json("{\"status\":false,\"message\":\"Student not found\"}"))
                .andReturn();
    }

}
