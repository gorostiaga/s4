package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.StudentDto;
import com.truextend.s4.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentToStudentDtoTest {

    StudentToStudentDto underTest = new StudentToStudentDto();

    @Test
    void convert() {
        String firstName = "Jimmy";
        String lastName = "Page";
        Student source = new Student(firstName, lastName);
        long studentId = 1l;
        source.setStudentId(studentId);

        StudentDto result = underTest.convert(source);
        assertNotNull(result);
        assertEquals(studentId, result.getStudentId());
        assertEquals(firstName, source.getFirstName());
        assertEquals(lastName,source.getLastName());
    }

    @Test
    void convertGetNull() {
        StudentDto result = underTest.convert(null);
        assertNull(result);
    }
}