package com.example.rigel_v1.repositories;

import com.example.rigel_v1.domain.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepository extends CrudRepository<Feedback, Long> {
}
