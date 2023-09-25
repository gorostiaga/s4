package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.StudentDto;
import com.truextend.s4.model.Student;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StudentToStudentDto implements Converter<Student, StudentDto> {

    @Synchronized
    @Nullable
    @Override
    public StudentDto convert(Student source) {
        if (source == null) {
            return null;
        }
        final StudentDto dto = new StudentDto();
        dto.setStudentId(source.getStudentId());
        dto.setFistName(source.getFirstName());
        dto.setLastName(source.getLastName());
        return dto;
    }
}
