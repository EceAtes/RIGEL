package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.LinkedList;

import com.example.rigel_v1.domain.enums.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter @NoArgsConstructor
public class Secretary extends Users{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String semesterFolderLink;

    private ArrayList<String> reportsFolderLinks; // ====EDITABLES==== | [0]: Internship Reports, [1]: Criteria Reports, [2]: Grade Forms
                                                  // ====VIEV ONLY==== | [3]: Internship Reports, [4]: Criteria Reports, [5]: Grade Forms

    public Secretary(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.SECRETARY, department);
        semesterFolderLink = "";
        reportsFolderLinks = new ArrayList<>();
    }

    public void addFolderLinks(String subfolderLink){
        reportsFolderLinks.add(subfolderLink);
    }
}
