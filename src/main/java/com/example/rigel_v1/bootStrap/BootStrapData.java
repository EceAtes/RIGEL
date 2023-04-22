package com.example.rigel_v1.bootStrap;
import com.example.rigel_v1.domain.Student;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.domain.Department;
import com.example.rigel_v1.domain.Users.Role;
import com.example.rigel_v1.repositories.DepartmentRepository;
import com.example.rigel_v1.repositories.NotificationRepository;
import com.example.rigel_v1.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public BootStrapData(DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Department CS = new Department(0,0, "CS");
        Department IE = new Department(0,0, "IE");
        departmentRepository.save(CS);
        departmentRepository.save(IE);

        Student A = new Student("A", "a@gmail.com", "1234", true, null);
        A.setDepartment(CS);

        userRepository.save(A);
        departmentRepository.save(CS);

        Student B = new Student("B", "b@gmail.com", "1235", false, null);
        B.setDepartment(IE);

        userRepository.save(B);
        departmentRepository.save(IE);

        Student C = new Student("C", "c@gmail.com", "1236", true, null);
        C.setDepartment(IE);

        userRepository.save(C);
        departmentRepository.save(IE);

        System.out.println("Started");

        System.out.println("No of users: " + userRepository.count());
        System.out.println("No of departments: " + departmentRepository.count());
        System.out.println("No of CS students: " + CS.getTotalStuNo());
        System.out.println("No of IE students: " + IE.getTotalStuNo());
    }
}
