package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.StudentWithCourseDto;
import com.truextend.s4.model.Student;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentToStudentWCourseDto implements Converter<Student, StudentWithCourseDto> {
    private final CourseToCourseDto courseToCourseDto;

    @Synchronized
    @Nullable
    @Override
    public StudentWithCourseDto convert(Student source) {
        if (source == null) {
            return null;
        }
        StudentWithCourseDto dto = new StudentWithCourseDto();
        dto.setStudentId(source.getStudentId());
        dto.setFirstName(source.getFirstName());
        dto.setLastName(source.getLastName());
        dto.setCourses(source.getCourses().stream().map(courseToCourseDto::convert).collect(Collectors.toSet()));
        return dto;
    }
}
