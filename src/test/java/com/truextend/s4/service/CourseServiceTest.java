package com.truextend.s4.service;

import com.truextend.s4.dto.CourseDto;
import com.truextend.s4.dto.CourseRequest;
import com.truextend.s4.dto.CourseWithStudentDto;
import com.truextend.s4.dto.converter.CourseReqToCourse;
import com.truextend.s4.dto.converter.CourseToCourseDto;
import com.truextend.s4.dto.converter.CourseToCourseWStudDto;
import com.truextend.s4.exceptions.NotFoundException;
import com.truextend.s4.model.Course;
import com.truextend.s4.model.Student;
import com.truextend.s4.repos.CourseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    CourseRepo courseRepo;
    @Mock
    CourseReqToCourse courseReqToCourse;
    @Mock
    CourseToCourseDto courseToCourseDto;
    @Mock
    CourseToCourseWStudDto courseToCourseWStudDto;

    @InjectMocks
    CourseService underTest;

    Course returnCourse;
    CourseRequest courseRequest;

    @BeforeEach
    void setUp() {
        returnCourse = new Course();
        returnCourse.setCode("Mat-101");
        returnCourse.setTitle("Algebra");
        returnCourse.setDescription("You will never be the same after this");

        courseRequest = new CourseRequest("Mat-101", "Algebra", "You will never be the same after this");

    }

    @Test
    void save() {
        when(courseReqToCourse.convert(courseRequest)).thenReturn(returnCourse);
        when(courseRepo.save(any())).thenReturn(returnCourse);

        String result = underTest.save(courseRequest);
        assertEquals("Mat-101", result);
        verify(courseReqToCourse).convert(any());
        verify(courseRepo).save(any());
    }

    @Test
    void update() {
        Course existingCourse = new Course();
        existingCourse.setCode("Phy-101");
        existingCourse.setTitle("Physics I");
        existingCourse.setDescription("You will not have a life");

        when(courseReqToCourse.convert(courseRequest)).thenReturn(returnCourse);
        when(courseRepo.findById("Phy-101")).thenReturn(Optional.of(existingCourse));

        underTest.update("Phy-101", courseRequest);

        assertEquals(existingCourse.getCode(), courseRequest.getCode());
        assertEquals(existingCourse.getTitle(), courseRequest.getTitle());
        assertEquals(existingCourse.getDescription(), courseRequest.getDescription());

        verify(courseReqToCourse).convert(any());
        verify(courseRepo).findById(any());
        verify(courseRepo).save(any());
    }

    @Test
    void updateNotFound() {
        String code = "Mat-203";
        when(courseReqToCourse.convert(courseRequest)).thenReturn(returnCourse);
        when(courseRepo.findById(code)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            underTest.update(code, courseRequest);
        });
        assertEquals("Item not found - " + code, exception.getMessage());

        verify(courseRepo, never()).save(any());

    }

    @Test
    void delete() {
        String code = "Mat-101";

        underTest.delete(code);
        verify(courseRepo).deleteById(anyString());
    }

    @Test
    void getAll() {
        Set<Course> courses = new HashSet<>();
        courses.add(new Course());
        courses.add(new Course());

        when(courseRepo.findAll()).thenReturn(courses);
        when(courseToCourseDto.convert(any())).thenAnswer(invocation -> {
            CourseDto dto = new CourseDto();
            return dto;
        });
        Set<CourseDto> result = underTest.getAll();
        assertEquals(2, result.size());
    }

    @Test
    void findById() {
        String code = "Mat-101";
        when(courseRepo.findById(code)).thenReturn(Optional.of(returnCourse));

        Course result = underTest.findById(code);
        assertNotNull(result);
    }

    @Test
    void getCourse() {
        String code = "Mat-101";

        CourseWithStudentDto dto = new CourseWithStudentDto();
        dto.setCourseCode(code);

        when(courseRepo.findById(code)).thenReturn(Optional.of(returnCourse));
        when(courseToCourseWStudDto.convert(any())).thenReturn(dto);

        CourseWithStudentDto result = underTest.getCourse(code);
        assertNotNull(result);
        assertEquals(code, result.getCourseCode());

    }

    @Test
    void getCourseInStudent() {
        String code = "Mat-101";
        Long studentId = 1L;
        Student student = new Student();
        student.setStudentId(studentId);
        CourseDto dto = new CourseDto();
        dto.setCode(code);

        returnCourse.getStudents().add(student);

        when(courseRepo.findById(code)).thenReturn(Optional.of(returnCourse));
        when(courseToCourseDto.convert(any())).thenReturn(dto);

        CourseDto result = underTest.getCourseInStudent(code, studentId);
        assertNotNull(result);

    }

    @Test
    void getCourseInStudentGetNull() {
        Long studentId = 2L;
        String code = "Mat-101";
        when(courseRepo.findById(code)).thenReturn(Optional.of(new Course()));

        CourseDto result = underTest.getCourseInStudent(code, studentId);

        assertNull(result);

    }
}