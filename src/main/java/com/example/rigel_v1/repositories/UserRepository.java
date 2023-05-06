package com.example.rigel_v1.repositories;

import com.example.rigel_v1.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<Users, Long> {
    /*int insertPerson(Long id, Users users);

    default int insertPerson(Users users){
        return insertPerson(users.getId(), users);
    }

    List<Users> getAllUser();

    Optional<Users> selectUserById(Long id);

    int deleteUserById(Long id);

    int updateUserById(Long id, Users users);*/
}
