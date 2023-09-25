package com.truextend.s4.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;
    @NotNull
    @Column(name = "first_name")
    private String firstName;
    @NotNull
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="student_course",
    joinColumns = @JoinColumn(name="student_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses = new HashSet<>();

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
