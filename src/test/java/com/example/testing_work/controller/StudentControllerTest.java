package com.example.testing_work.controller;

import com.example.testing_work.model.Student;
import com.example.testing_work.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
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
        when(studentServiceMock.getById(1)).thenReturn(null);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/students/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"Student not found\"}"))
                .andReturn();
    }

//    @Test
//    public void updateStudent_success() throws Exception{
//        UpdateStudentDto dto = new UpdateStudentDto( "firstName2", "lastName2", "email2@mail.com");
//        Student updatedStudent = new Student(4, "firstName2", "lastName2", "emassil2@mail.com");
//        Student student = new Student(4, "MANZI", "Mike", "mike@gmail.com");
//        when(studentServiceMock.getById(4)).thenReturn(student);
//        when(studentServiceMock.updateStudent(anyInt(),any(UpdateStudentDto.class))).thenReturn(updatedStudent);
//
//        mockMvc.perform( MockMvcRequestBuilders
//                        .put("/api/students/{id}", 4)
//                        .content(asJsonString(new UpdateStudentDto( "firstName2", "lastName2", "emassil2@mail.com")))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isCreated())
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("firstName2"))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastName2"))
//                        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("emassil2@mail.com"));
//
//    }

    @Test
    public void addStudent_success() throws Exception{

        when(studentServiceMock.addStudent(any(Student.class))).thenReturn(new Student(1, "Kaisa","ubufu", "so@gmail.com"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/students/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Kaisa\",\"lastName\": \"ubufu\",\"email\": \"so@gmail.com\"}");

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(" {\"id\":1,\"firstName\":\"Kaisa\",\"lastName\": \"ubufu\",\"email\":\"so@gmail.com\"}"))
                .andReturn();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
