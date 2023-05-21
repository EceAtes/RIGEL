package com.example.rigel_v1.repositories;

import com.example.rigel_v1.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
