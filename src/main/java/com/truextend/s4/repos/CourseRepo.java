package com.truextend.s4.repos;

import com.truextend.s4.model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepo extends CrudRepository<Course, String> {
}
