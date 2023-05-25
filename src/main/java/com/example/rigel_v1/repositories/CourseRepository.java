package com.example.rigel_v1.repositories;

import com.example.rigel_v1.domain.StudentCourse;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<StudentCourse, Long> {
}
