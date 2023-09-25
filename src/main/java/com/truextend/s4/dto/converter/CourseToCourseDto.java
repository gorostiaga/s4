package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.CourseDto;
import com.truextend.s4.model.Course;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CourseToCourseDto implements Converter<Course, CourseDto> {

    @Synchronized
    @Nullable
    @Override
    public CourseDto convert(Course source) {
        if (source == null) {
            return null;
        }
        final CourseDto dto = new CourseDto();
        dto.setCode(source.getCode());
        dto.setTitle(source.getTitle());
        dto.setDescription(source.getDescription());
        return dto;
    }
}
