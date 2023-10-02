package com.truextend.s4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truextend.s4.dto.CourseDto;
import com.truextend.s4.dto.CourseRequest;
import com.truextend.s4.dto.CourseWithStudentDto;
import com.truextend.s4.service.CourseService;
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


@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    CourseRequest courseRequest;
    String code;

    @BeforeEach
    void setUp() {
        courseRequest = new CourseRequest("Mat-101", "Algebra I", "You will be  tired");
        code = "Mat-101";
    }

    @Test
    void save() throws Exception {
        when(courseService.save(any())).thenReturn("Mat-101");
        mockMvc.perform(post("/apiv1/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(courseRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Course Created"))
                .andExpect(jsonPath("$.id").value(courseRequest.getCode()));
    }

    @Test
    void update() throws Exception{

        mockMvc.perform(put("/apiv1/courses/{id}", code)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(courseRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Course Updated"))
                .andExpect(jsonPath("$.id").value(code));

    }

    @Test
    void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/apiv1/courses/{id}", code)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Course deleted"));
    }

    @Test
    void getAll() throws Exception {
        Set<CourseDto> courseDtos = new HashSet<>(Arrays.asList(new CourseDto(), new CourseDto()));
        when(courseService.getAll()).thenReturn(courseDtos);

        mockMvc.perform(get("/apiv1/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courses").isArray())
                .andExpect(jsonPath("$.courses.length()").value(2));
    }

    @Test
    void getCourse() throws Exception{
        CourseWithStudentDto courseWithStudentDto = new CourseWithStudentDto();
        when(courseService.getCourse(code)).thenReturn(courseWithStudentDto);

        mockMvc.perform(get("/apiv1/courses/{id}", code)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course").exists());
    }

    @Test
    void getCourseInStudent() throws Exception{
        Long studentId = 1L;
        CourseDto courseDto = new CourseDto();
        when(courseService.getCourseInStudent(code, studentId)).thenReturn(courseDto);

        mockMvc.perform(get("/apiv1/courses/isCourse")
                        .param("courseCode", code)
                        .param("studentId", studentId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course").exists());
    }
}