package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.CourseRequest;
import com.truextend.s4.model.Course;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CourseReqToCourse implements Converter<CourseRequest, Course> {

    @Synchronized
    @Nullable
    @Override
    public Course convert(CourseRequest source) {
        if (source == null) {
            return null;
        }
        final Course course = new Course();
        course.setCode(source.getCode());
        course.setTitle(source.getTitle());
        course.setDescription(source.getDescription());
        return course;
    }
}
