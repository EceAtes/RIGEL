package com.example.rigel_v1.repositories;

import com.example.rigel_v1.domain.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
