package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.rigel_v1.domain.enums.Role;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter @Getter @NoArgsConstructor
public class Admin extends Users {

    private boolean semesterStarted;
    private String semesterFolderID;
    private String folderDomain = "https://drive.google.com/drive/folders/";

    private Date semesterFirstDay;
    private Date semesterLastDay;

    private List<String> reportFolderIDs; // VIEV ONLY | [0]: Internship Reports, [1]: Criteria Reports, [2]: Grade Forms

    public Admin(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.ADMIN, department);
        semesterFolderID = "";
        reportFolderIDs = new ArrayList<>();
        semesterStarted = false;
    }

    public void saveReportFolderID(String reportFolderID){
        reportFolderIDs.add(reportFolderID);
    }
}
