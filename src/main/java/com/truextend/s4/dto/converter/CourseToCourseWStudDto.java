package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.CourseWithStudentDto;
import com.truextend.s4.model.Course;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CourseToCourseWStudDto implements Converter<Course, CourseWithStudentDto> {
    private final StudentToStudentDto studentToStudentDto;

    @Synchronized
    @Nullable
    @Override
    public CourseWithStudentDto convert(Course source) {
        if (source == null) {
            return null;
        }
        CourseWithStudentDto dto = new CourseWithStudentDto();
        dto.setCourseCode(source.getCode());
        dto.setTitle(source.getTitle());
        dto.setStudents(source.getStudents().stream()
                .map(studentToStudentDto::convert)
                .collect(Collectors.toSet()));
        return dto;
    }
}
