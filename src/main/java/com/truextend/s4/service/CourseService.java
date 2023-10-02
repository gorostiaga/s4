package com.truextend.s4.service;

import com.truextend.s4.dto.CourseDto;
import com.truextend.s4.dto.CourseRequest;
import com.truextend.s4.dto.CourseWithStudentDto;
import com.truextend.s4.dto.converter.CourseReqToCourse;
import com.truextend.s4.dto.converter.CourseToCourseDto;
import com.truextend.s4.dto.converter.CourseToCourseWStudDto;
import com.truextend.s4.exceptions.NotFoundException;
import com.truextend.s4.model.Course;
import com.truextend.s4.model.Student;
import com.truextend.s4.repos.CourseRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepo courseRepo;
    private final CourseReqToCourse courseReqToCourse;
    private final CourseToCourseDto courseToCourseDto;
    private final CourseToCourseWStudDto courseToCourseWStudDto;

    public String save(CourseRequest request) {
        Course course = courseReqToCourse.convert(request);
        Course save = courseRepo.save(course);
        return save.getCode();
    }

    public void update(String id, CourseRequest request) {
        Course updatedCourse = courseReqToCourse.convert(request);
        Course updatingCourse = courseRepo
                .findById(id)
                .orElseThrow(()-> new NotFoundException("Item not found - " + id));
        updatingCourse.setCode(updatedCourse.getCode());
        updatingCourse.setTitle(updatedCourse.getTitle());
        updatingCourse.setDescription(updatedCourse.getDescription());
        courseRepo.save(updatingCourse);
    }

    public void delete(String id) {
        courseRepo.deleteById(id);
    }

    public Set<CourseDto> getAll() {
        Set<CourseDto> courses = StreamSupport.stream(courseRepo.findAll()
                .spliterator(), false)
                .map(courseToCourseDto::convert)
                .collect(Collectors.toSet());
        return courses;
    }

    public Course findById(String id){
        return courseRepo
                .findById(id)
                .orElseThrow(()-> new NotFoundException("Item not found - " + id));
    }

    public CourseWithStudentDto getCourse(String id) {
        Course course = courseRepo
                .findById(id)
                .orElseThrow(()-> new NotFoundException("Item not found - " + id));
        return courseToCourseWStudDto.convert(course);
    }

    public CourseDto getCourseInStudent(String courseCode, Long studentId) {
        Course course = courseRepo
                .findById(courseCode)
                .orElseThrow(()-> new NotFoundException("Item not found - " + courseCode));
        Student studentResult = course.getStudents().stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst().orElse(null);
        if (studentResult == null) return null;
        else return courseToCourseDto.convert(course);

    }
}
