package com.truextend.s4.controller;

import com.truextend.s4.dto.*;
import com.truextend.s4.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@RequestMapping("/apiv1/students")
@RestController
public class StudentController {

    private final StudentService studentService;

    //save
    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody StudentRequest request){
        Long id = studentService.save(request);
        return new ResponseEntity<>(new ApiResponse(true, "Student Created", id.toString(), HttpStatus.OK.value()), HttpStatus.OK);
    }

    //edit
    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody StudentRequest request){
        studentService.update(id, request);
        return new ResponseEntity<>(new ApiResponse(true, "Student Updated", id.toString(), HttpStatus.OK.value()), HttpStatus.OK);
    }

    //delete
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Long id){
        studentService.delete(id);
        return new ResponseEntity<>(new ApiResponse(true, "Student deleted", HttpStatus.OK.value()), HttpStatus.OK);
    }
    //get all
    @GetMapping
    public ResponseEntity<Map<String, Set<StudentDto>>> getAll(){
        Set<StudentDto> studentDtos = studentService.getAll();
        Map<String, Set<StudentDto>> response = new HashMap<>();
        response.put("students", studentDtos);
        return ResponseEntity.ok(response);
    }

    //add course to student
    @PostMapping(value = "/addCourse")
    public ResponseEntity<ApiResponse> addToCourse(@RequestBody AddStudentToCourseRequest request){
        studentService.addToCourse(request);
        return new ResponseEntity<>(new ApiResponse(true, "Student added to Course", HttpStatus.OK.value()), HttpStatus.OK);
    }

    //get student + courses
    @GetMapping(value = "/{id}")
    public ResponseEntity<Map<String, StudentWithCourseDto>> getStudent(@PathVariable("id") Long id){
        StudentWithCourseDto studentWithCourseDto = studentService.getStudent(id);
        Map<String, StudentWithCourseDto> response = new HashMap<>();
        response.put("student", studentWithCourseDto);
        return ResponseEntity.ok(response);
    }

    //Search student in a given course
    @GetMapping(value = "/isStudent")
    public ResponseEntity<Map<String, StudentDto>> getStudentInCourse(
            @RequestParam("courseCode") String courseCode, @RequestParam("studentId") Long studentId){
        StudentDto studentDto = studentService.getStudentInCourse(courseCode, studentId);
        Map<String, StudentDto> respose = new HashMap<>();
        respose.put("student", studentDto);
        return  ResponseEntity.ok(respose);
    }
}
