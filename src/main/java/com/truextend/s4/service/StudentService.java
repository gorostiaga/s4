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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;
    private final StudentReqToStudent studentReqToStudent;
    private final StudentToStudentDto studentToStudentDto;
    private final CourseService courseService;
    private final StudentToStudentWCourseDto studentToStudentWCourseDto;

    public Long save(StudentRequest request) {
        Student student = studentReqToStudent.convert(request);
        Student save = studentRepo.save(student);
        return save.getStudentId();

    }


    public void update(Long id, StudentRequest request) {
        Student updatedStudent = studentReqToStudent.convert(request);
        Student updatingStudent = studentRepo.findById(id).orElseThrow(()-> new NotFoundException("Item not found - " + id));
        updatingStudent.setFirstName(updatedStudent.getFirstName());
        updatingStudent.setLastName(updatedStudent.getLastName());
        studentRepo.save(updatingStudent);
    }

    public void delete(Long id) {
        studentRepo.deleteById(id);
    }

    public Set<StudentDto> getAll() {
        Set<StudentDto> students = StreamSupport.stream(studentRepo.findAll()
                .spliterator(),false)
                .map(studentToStudentDto::convert)
                .collect(Collectors.toSet());
        return students;
    }

    public void addToCourse(AddStudentToCourseRequest request) {
        Course course = courseService.findById(request.getCourseCode());
        Student student = studentRepo.findById(request.getStudentId()).orElseThrow(()-> new NotFoundException("Item not found - " + request.getStudentId()));
        student.getCourses().add(course);
        studentRepo.save(student);
    }

    public StudentWithCourseDto getStudent(Long id) {
        Student student = studentRepo.findById(id).orElseThrow(()-> new NotFoundException("Item not found - " + id));
        return studentToStudentWCourseDto.convert(student);
    }

    public Student findById(Long id){
        return studentRepo.findById(id).orElseThrow(()-> new NotFoundException("Item not found - " + id));
    }

    public StudentDto getStudentInCourse(String courseCode, Long studentId) {
        Course course = courseService.findById(courseCode);
        Student studentResult = course.getStudents().stream().filter(student -> student.getStudentId().equals(studentId)).findFirst().orElse(null);
        return studentToStudentDto.convert(studentResult);
    }
}
