package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.CourseDto;
import com.truextend.s4.dto.StudentWithCourseDto;
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
class StudentToStudentWCourseDtoTest {

    @Mock
    CourseToCourseDto courseToCourseDto;
    @InjectMocks
    StudentToStudentWCourseDto underTest;

    @Test
    void convert() {
        String firstName = "Jimmy";
        String lastName = "Page";
        Student source = new Student(firstName, lastName);
        long studentId = 1l;
        source.setStudentId(studentId);
        Set<Course> courses = new HashSet<>();
        courses.add(new Course());
        courses.add(new Course());
        source.setCourses(courses);
        when(courseToCourseDto.convert(any())).thenAnswer(invocation -> new CourseDto());

        StudentWithCourseDto result = underTest.convert(source);

        assertNotNull(result);
        assertEquals(studentId, result.getStudentId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertNotNull(result.getCourses());
        assertEquals(2, result.getCourses().size());

    }

    @Test
    void convertGetNull() {
        StudentWithCourseDto result = underTest.convert(null);
        assertNull(result);
    }
}