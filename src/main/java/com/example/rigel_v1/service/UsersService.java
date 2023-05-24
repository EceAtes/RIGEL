package com.example.rigel_v1.service;

import com.example.rigel_v1.domain.*;
import com.example.rigel_v1.repositories.CourseRepository;
import com.example.rigel_v1.repositories.UserRepository;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsersService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public UsersService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void automatch(Department department){
        List<Instructor> instructors = new ArrayList<>();
        for(int i = 0; i < userRepository.count(); i++){
            Optional<Users> optional = userRepository.findById(Long.valueOf(i+1));
            if(optional.isPresent() && optional.get().getRole() == Users.Role.INSTRUCTOR && optional.get().getDepartment().equals(department)){
                instructors.add((Instructor) optional.get());
            }
        }
        System.out.println(instructors);
        int counter = 0;
        for(int i = 0; i < courseRepository.count(); i++){
            Optional<StudentCourse> optional = courseRepository.findById(Long.valueOf(i+1));
            /*if(optional.isPresent()){
                System.out.println(optional.get().getCourseTaker());
            }*/
            counter = counter % instructors.size();
            if(optional.isPresent() && optional.get().getCourseTaker().getDepartment().equals(department)){
                instructors.get(counter).addCourse(optional.get());
                instructors.get(counter).setName("OwO UwU");
                userRepository.save(instructors.get(counter));
                courseRepository.save(optional.get());
                counter++;
            }
        }
    }

    public Users createUser(String name, String email, String password, boolean notifToMail, Users.Role role, Department department, int studentId){
        if (role == Users.Role.STUDENT){ //equals()??
            Student student = new Student(name,email,password, notifToMail, department, studentId);
            userRepository.save(student);
            return student;
        }
        else if (role == Users.Role.INSTRUCTOR) {
            Instructor instructor = new Instructor(name, email, password, notifToMail, department);
            userRepository.save(instructor);
            return instructor;
        }
        else if (role == Users.Role.SECRETARY) {
            Secretary secretary = new Secretary(name,email,password, notifToMail, department);
            userRepository.save(secretary);
            return secretary;
        }
        return new Users();
    }

    /*
   private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int addUser(Users user){
        return this.userRepository.insertPerson(user);
    }*/
}
