package com.truextend.s4.repos;

import com.truextend.s4.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepo extends CrudRepository<Student, Long> {
}
