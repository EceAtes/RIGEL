package com.example.rigel_v1.repositories;

import com.example.rigel_v1.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<Users, Long> {
}


