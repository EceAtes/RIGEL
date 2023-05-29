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

    private boolean semesterCreated;
    private String semesterFolderKey;
    private String folderDomain = "https://drive.google.com/drive/folders/";

    private String semesterFirstDay;   // yyyy-mm-dd
    private String semesterLastDay;    // yyyy-mm-dd
    private String addDropDeadline;    // yyyy-mm-dd
    private String withdrawDeadline;   // yyyy-mm-dd

    private List<String> reportFolderKeys; // [0]: CS [1]: Internship Reports, [2]: Summer Training Grade Form
                                           // [3]: EE [4]: Internship Reports, [5]: Summer Training Grade Forms
                                           // [6]: ME [7]: Internship Reports, [8]: Summer Training Grade Forms
                                           // [9]: IE [10]: Internship Reports, [11]: Summer Training Grade Forms

    public Admin(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.ADMIN, department);
        semesterFolderKey = "";
        reportFolderKeys = new ArrayList<>();
        semesterCreated = false;
    }

    public void saveReportFolderID(String reportFolderID){
        reportFolderKeys.add(reportFolderID);
    }

    public void informSecretary(Secretary secretary){
        secretary.setAddDropDeadline(addDropDeadline);
        secretary.setWithdrawDeadline(withdrawDeadline);
        secretary.getDepartment().setSemesterStarted(true);
        if( secretary.getDepartment().getName().equals("CS") ){
            secretary.setDepartmentFolderKey(reportFolderKeys.get(0));
            secretary.addFolderKeys(reportFolderKeys.get(1));
            secretary.addFolderKeys(reportFolderKeys.get(2));
        }
        else if( secretary.getDepartment().getName().equals("EE") ){
            secretary.setDepartmentFolderKey(reportFolderKeys.get(3));
            secretary.addFolderKeys(reportFolderKeys.get(4));
            secretary.addFolderKeys(reportFolderKeys.get(5));
        }
        else if( secretary.getDepartment().getName().equals("ME") ){
            secretary.setDepartmentFolderKey(reportFolderKeys.get(6));
            secretary.addFolderKeys(reportFolderKeys.get(7));
            secretary.addFolderKeys(reportFolderKeys.get(8));
        }
        else {
            secretary.setDepartmentFolderKey(reportFolderKeys.get(9));
            secretary.addFolderKeys(reportFolderKeys.get(10));
            secretary.addFolderKeys(reportFolderKeys.get(11));
        }
    }
}
