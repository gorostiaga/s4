package com.truextend.s4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto implements Serializable {
    private Long studentId;
    private String fistName;
    private String lastName;
}
