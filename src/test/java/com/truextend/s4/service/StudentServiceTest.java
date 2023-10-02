package com.truextend.s4.service;

import com.truextend.s4.dto.AddStudentToCourseRequest;
import com.truextend.s4.dto.StudentDto;
import com.truextend.s4.dto.StudentRequest;
import com.truextend.s4.dto.StudentWithCourseDto;
import com.truextend.s4.dto.converter.StudentReqToStudent;
import com.truextend.s4.dto.converter.StudentToStudentDto;
import com.truextend.s4.dto.converter.StudentToStudentWCourseDto;
import com.truextend.s4.exceptions.NotFoundException;
import com.truextend.s4.model.Course;
import com.truextend.s4.model.Student;
import com.truextend.s4.repos.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepo studentRepo;
    @Mock
    StudentReqToStudent studentReqToStudent;
    @Mock
    StudentToStudentDto studentToStudentDto;
    @Mock
    CourseService courseService;
    @Mock
    StudentToStudentWCourseDto studentToStudentWCourseDto;

    @InjectMocks
    StudentService underTest;

    Student returnStudent;
    StudentRequest studentRequest;

    @BeforeEach
    void setUp() {
        returnStudent = new Student("John", "Doe");
        studentRequest = new StudentRequest("John", "Doe");
    }

    @Test
    void save() {

        when(studentReqToStudent.convert(studentRequest)).thenReturn(returnStudent);
        when(studentRepo.save(any())).thenReturn(returnStudent);

        Long idSaved = underTest.save(studentRequest);

        assertEquals(returnStudent.getStudentId(), idSaved);
        verify(studentReqToStudent).convert(any());
        verify(studentRepo).save(any());
    }

    @Test
    void update() {

        Student existingStudent = new Student("Alice", "Cooper");
        Long id = 1l;

        when(studentReqToStudent.convert(studentRequest)).thenReturn(returnStudent);
        when(studentRepo.findById(id)).thenReturn(Optional.of(existingStudent));

        underTest.update(id, studentRequest);

        assertEquals(returnStudent.getFirstName(), existingStudent.getFirstName());
        assertEquals(returnStudent.getLastName(), existingStudent.getLastName());
        verify(studentReqToStudent).convert(any());
        verify(studentRepo).findById(anyLong());
        verify(studentRepo).save(any());
    }

    @Test
    void updateNotFound() {
        Long id = 2l;
        when(studentReqToStudent.convert(studentRequest)).thenReturn(returnStudent);
        when(studentRepo.findById(id)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            underTest.update(id, studentRequest);});
        assertEquals("Item not found - " + id, exception.getMessage());
        verify(studentRepo, never()).save(any());
    }

    @Test
    void delete() {
        Long id = 1l;

        underTest.delete(id);
        verify(studentRepo).deleteById(anyLong());

    }

    @Test
    void getAll() {
        Set<Student> students = new HashSet<>();
        students.add(new Student());
        students.add(new Student());

        when(studentRepo.findAll()).thenReturn(students);
        when(studentToStudentDto.convert(any())).thenAnswer(invocation -> {
            StudentDto dto = new StudentDto();
            return dto;
        });

        Set<StudentDto> result = underTest.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void addToCourse() {
        AddStudentToCourseRequest request = new AddStudentToCourseRequest(1l, "Mat-101");
        Course course = new Course();
        course.setCode("Mat-101");
        course.setTitle("Algebra");
        course.setDescription("You will not pass");
        when(courseService.findById(request.getCourseCode())).thenReturn(course);
        when(studentRepo.findById(request.getStudentId())).thenReturn(Optional.of(returnStudent));

        underTest.addToCourse(request);

        assertTrue(returnStudent.getCourses().contains(course));
        assertEquals(returnStudent.getCourses().size(), 1);
        verify(studentRepo).save(any());
    }

    @Test
    void getStudent() {
        long studentId = 1l;
        returnStudent.setStudentId(studentId);

        StudentWithCourseDto dto = new StudentWithCourseDto();
        dto.setStudentId(studentId);

        when(studentRepo.findById(studentId)).thenReturn(Optional.of(returnStudent));
        when(studentToStudentWCourseDto.convert(any())).thenReturn(dto);

        StudentWithCourseDto result = underTest.getStudent(studentId);
        assertNotNull(result);
        assertEquals(studentId, result.getStudentId());
    }

    @Test
    void findById() {
        long studentId = 1l;
        when(studentRepo.findById(studentId)).thenReturn(Optional.of(returnStudent));

        Student result = underTest.findById(studentId);
        assertNotNull(result);
    }

    @Test
    void getStudentInCourse() {
        String courseCode = "Mat-101";
        Long studentId = 1L;

        Course course = new Course();

        returnStudent.setStudentId(studentId);
        course.getStudents().add(returnStudent);

        when(courseService.findById(courseCode)).thenReturn(course);
        when(studentToStudentDto.convert(returnStudent)).thenReturn(new StudentDto());

        StudentDto result = underTest.getStudentInCourse(courseCode, studentId);

        assertNotNull(result);
    }

    @Test
    void getStudentInCourseGetNull() {
        Long studentId = 2l;
        String code = "Mat-101";

        Course course = new Course();

        when(courseService.findById(code)).thenReturn(course);
        when(studentToStudentDto.convert(null)).thenReturn(null);

        StudentDto result = underTest.getStudentInCourse(code, studentId);

        assertNull(result);

    }
}