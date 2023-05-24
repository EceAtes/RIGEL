package com.example.rigel_v1.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.rigel_v1.domain.enums.Role;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter @Getter @NoArgsConstructor
public class Admin extends Users {

    private String semesterFolderID;
    private String folderDomain = "https://drive.google.com/drive/folders/";

    private List<String> reportFolderIDs; //VIEV ONLY | [0]: Internship Reports, [1]: Criteria Reports, [2]: Grade Forms
    //UNNECESSARY I GUESS, EDITABLES | [0]: Internship Reports, [1]: Criteria Reports, [2]: Grade Forms                                         

    public Admin(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.ADMIN, department);
        semesterFolderID = "";
        reportFolderIDs = new ArrayList<>();
    }

    public void distributeFolderLinks(){
        
    }

    public void saveReportFolderID(String reportFolderID){
        reportFolderIDs.add(reportFolderID);
    }
}
