package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.CourseRequest;
import com.truextend.s4.model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseReqToCourseTest {


    CourseReqToCourse underTest = new CourseReqToCourse();
    @Test
    void convert() {
        String code = "Mat-101";
        String title = "Algebra I";
        String description = "You will never be the same";
        CourseRequest source = new CourseRequest(code, title, description);

        Course result = underTest.convert(source);
        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
    }

    @Test
    void convertGetNull() {
        Course result = underTest.convert(null);
        assertNull(result);
    }
}