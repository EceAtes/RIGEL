package com.example.rigel_v1.repositories;

import com.example.rigel_v1.domain.Department;
import com.example.rigel_v1.domain.Instructor;
import org.springframework.data.repository.CrudRepository;

public interface InstructorRepository extends CrudRepository<Instructor, Long> {
}
