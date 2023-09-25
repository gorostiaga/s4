package com.truextend.s4.dto.converter;

import com.truextend.s4.dto.StudentRequest;
import com.truextend.s4.model.Student;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StudentReqToStudent implements Converter<StudentRequest, Student> {

    @Synchronized
    @Nullable
    @Override
    public Student convert(StudentRequest source) {
        if (source == null) {
            return null;
        }
        final Student student = new Student();
        student.setFirstName(source.getFirstName());
        student.setLastName(source.getLastName());
        return student;
    }
}
