package com.truextend.s4.controller;

import com.truextend.s4.dto.ApiResponse;
import com.truextend.s4.dto.CourseDto;
import com.truextend.s4.dto.CourseRequest;
import com.truextend.s4.dto.CourseWithStudentDto;
import com.truextend.s4.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//TODO check the CrossOrigin
@AllArgsConstructor
@RequestMapping("/apiv1/courses")
@RestController
public class CourseController {

    private final CourseService courseService;

    //save
    @PostMapping
    public ResponseEntity<ApiResponse> save (@RequestBody CourseRequest request){
        String id = courseService.save(request);
        return new ResponseEntity<>(new ApiResponse(true,"Course Created", id, HttpStatus.OK.value()), HttpStatus.OK);
    }

    //edit
    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") String id, @RequestBody CourseRequest request){
        courseService.update(id, request);
        return new ResponseEntity<>(new ApiResponse(true,"Course Updated", id, HttpStatus.OK.value()), HttpStatus.OK);
    }

    //delete
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") String id){
        courseService.delete(id);
        return new ResponseEntity<>(new ApiResponse(true, "Course deleted", HttpStatus.OK.value()), HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<Map<String, Set<CourseDto>>> getAll(){
        Set<CourseDto> studentDtos = courseService.getAll();
        Map<String, Set<CourseDto>> response = new HashMap<>();
        response.put("courses", studentDtos);
        return ResponseEntity.ok(response);
    }
    // get course + students
    @GetMapping(value = "/{id}")
    public ResponseEntity<Map<String, CourseWithStudentDto>> getCourse(@PathVariable("id") String id){
        CourseWithStudentDto courseWithStudentDto = courseService.getCourse(id);
        Map<String, CourseWithStudentDto> response = new HashMap<>();
        response.put("course", courseWithStudentDto);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "isCourse")
    public ResponseEntity<Map<String, CourseDto>> getCourseInStudent(@RequestParam("courseCode") String courseCode, @RequestParam("studentId") Long studentId){
        CourseDto courseDto = courseService.getCourseInStudent(courseCode, studentId);
        Map<String, CourseDto> response = new HashMap<>();
        response.put("course", courseDto);
        return ResponseEntity.ok(response);
    }
}
