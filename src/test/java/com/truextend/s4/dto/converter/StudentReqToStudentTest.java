package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.StudentRequest;
import com.truextend.s4.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentReqToStudentTest {

    StudentReqToStudent underTest = new StudentReqToStudent();


    @Test
    void convert() {
        String name = "Jimmy";
        String lastName = "Page";
        StudentRequest source = new StudentRequest(name, lastName);

        Student result = underTest.convert(source);
        assertNotNull(result);
        assertEquals(name, result.getFirstName());
        assertEquals(lastName, source.getLastName());
    }

    @Test
    void convertGetNull() {
        Student result = underTest.convert(null);
        assertNull(result);
    }
}