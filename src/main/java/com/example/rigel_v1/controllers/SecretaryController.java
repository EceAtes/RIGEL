package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.Secretary;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.repositories.UserRepository;
import com.example.rigel_v1.service.UsersService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/secretary")
@RestController
public class SecretaryController {
    private final UsersService usersService;
    private final UserRepository userRepository;

    public SecretaryController(UsersService usersService, UserRepository userRepository) {
        this.usersService = usersService;
        this.userRepository = userRepository;
    }

    @RequestMapping("/automatch/{id}")
    public void automatch(@PathVariable Long id) {
        Optional<Users> optional = userRepository.findById(id);
        if(optional.isPresent() && optional.get() instanceof Secretary){
            ((Secretary) optional.get()).automatch(usersService);
        }
    }

    @RequestMapping("/rematch/{id}")
    public void rematch(@PathVariable Long id, @RequestBody RematchRequest req) {
        Optional<Users> optional = userRepository.findById(id);
        if(optional.isPresent() && optional.get() instanceof Secretary){
            ((Secretary) optional.get()).rematchStudent(usersService, req.getInstructorID(), req.getCourseID());
        }
    }

    @RequestMapping("/createFromFile/{id}")
    public void rematch(@PathVariable Long id) {
        Optional<Users> optional = userRepository.findById(id);
        if(optional.isPresent() && optional.get() instanceof Secretary){
            ((Secretary) optional.get()).createStudentsFromFile(usersService);
        }
    }



    //set deadline
}

@Getter
@Setter
@NoArgsConstructor
class RematchRequest{
    private Long InstructorID;
    private Long courseID;
}
