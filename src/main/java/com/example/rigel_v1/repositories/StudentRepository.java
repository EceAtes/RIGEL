package com.example.rigel_v1.repositories;

import com.example.rigel_v1.domain.Report;
import com.example.rigel_v1.domain.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
