package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.CourseWithStudentDto;
import com.truextend.s4.dto.StudentDto;
import com.truextend.s4.model.Course;
import com.truextend.s4.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseToCourseWStudDtoTest {

    @Mock
    StudentToStudentDto studentToStudentDto;

    @InjectMocks
    CourseToCourseWStudDto underTest;

    @Test
    void convert() {
        String code = "Mat-101";
        String title = "Algebra I";
        String description = "You will never be the same";
        Set<Student> students = new HashSet<>();
        students.add(new Student());
        students.add(new Student());
        Course source = new Course(code, title,description, students);
        when(studentToStudentDto.convert(any())).thenAnswer(invocation -> new StudentDto());

        CourseWithStudentDto result = underTest.convert(source);

        assertNotNull(result);
        assertEquals(code, result.getCourseCode());
        assertEquals(title, result.getTitle());
        assertNotNull(result.getStudents());
        assertEquals(2, result.getStudents().size());
    }

    @Test
    void convertGetNull() {
        CourseWithStudentDto result = underTest.convert(null);
        assertNull(result);
    }
}