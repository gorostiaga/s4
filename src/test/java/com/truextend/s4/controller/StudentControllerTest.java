package com.truextend.s4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truextend.s4.dto.AddStudentToCourseRequest;
import com.truextend.s4.dto.StudentDto;
import com.truextend.s4.dto.StudentRequest;
import com.truextend.s4.dto.StudentWithCourseDto;
import com.truextend.s4.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    StudentRequest studentRequest;
    long studentId;

    @BeforeEach
    void setUp() {
        studentRequest = new StudentRequest("Lalo", "Landa");
        studentId = 1l;
    }

    @Test
    void save() throws Exception {

        when(studentService.save(any())).thenReturn(studentId);
        mockMvc.perform(post("/apiv1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(studentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student Created"))
                .andExpect(jsonPath("$.id").value(studentId));

    }

    @Test
    void update() throws Exception {
        StudentRequest updatedStudent = new StudentRequest("Kurt", "Cobain");
        mockMvc.perform(put("/apiv1/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedStudent)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student Updated"))
                .andExpect(jsonPath("$.id").value(studentId));

    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/apiv1/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student deleted"));
    }

    @Test
    void getAll() throws Exception {
        Set<StudentDto> studentDtos = new HashSet<>(Arrays.asList(new StudentDto(), new StudentDto()));
        when(studentService.getAll()).thenReturn(studentDtos);

        mockMvc.perform(get("/apiv1/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students").isArray())
                .andExpect(jsonPath("$.students.length()").value(2));
    }

    @Test
    void addToCourse() throws Exception{
        AddStudentToCourseRequest request = new AddStudentToCourseRequest(2l, "Mat-101");

        mockMvc.perform(post("/apiv1/students/addCourse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student added to Course"));

    }

    @Test
    void getStudent() throws Exception {
        StudentWithCourseDto studentWithCourseDto = new StudentWithCourseDto();
        when(studentService.getStudent(studentId)).thenReturn(studentWithCourseDto);

        mockMvc.perform(get("/apiv1/students/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student").exists());

    }

    @Test
    void getStudentInCourse() throws Exception {
        String courseCode = "Mat-101";
        StudentDto studentDto = new StudentDto();
        when(studentService.getStudentInCourse(courseCode, studentId)).thenReturn(studentDto);

        mockMvc.perform(get("/apiv1/students/isStudent")
                        .param("courseCode", courseCode)
                        .param("studentId", Long.toString(studentId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student").exists());
    }
}