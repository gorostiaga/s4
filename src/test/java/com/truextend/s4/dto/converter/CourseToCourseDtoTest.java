package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.CourseDto;
import com.truextend.s4.model.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseToCourseDtoTest {

    CourseToCourseDto underTest = new CourseToCourseDto();

    @Test
    void convert() {
        String code = "Mat-101";
        String title = "Algebra I";
        String description = "You will never be the same";
        Course source = new Course();
        source.setCode(code);
        source.setTitle(title);
        source.setDescription(description);

        CourseDto result = underTest.convert(source);


        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertEquals(title, source.getTitle());
        assertEquals(description, source.getDescription());

    }

    @Test
    void convertGetNull() {
        CourseDto result = underTest.convert(null);
        assertNull(result);
    }
}