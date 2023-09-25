package com.truextend.s4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO to get the fields in order to create a new Student
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    private String firstName;
    private String lastName;
}
