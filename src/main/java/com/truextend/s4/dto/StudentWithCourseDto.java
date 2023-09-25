package com.truextend.s4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithCourseDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private Set<CourseDto> courses = new HashSet<>();
}
