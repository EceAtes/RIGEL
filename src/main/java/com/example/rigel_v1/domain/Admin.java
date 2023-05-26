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
    private String semesterFolderKey;
    private String folderDomain = "https://drive.google.com/drive/folders/";

    private String semesterFirstDay;   // yyyy-mm-dd
    private String semesterLastDay;    // yyyy-mm-dd
    private String addDropDeadline;    // yyyy-mm-dd
    private String withdrawDeadline;   // yyyy-mm-dd

    private List<String> reportFolderKeys; // [0]: CS [1]: Internship Reports, [2]: Criteria Reports,  [3]: Grade Forms
                                           // [4]: EE [5]: Internship Reports, [6]: Criteria Reports,  [7]: Grade Forms
                                           // [8]: ME [9]: Internship Reports, [10]: Criteria Reports, [11]: Grade Forms
                                           // [12]: IE [13]: Internship Reports, [14]: Criteria Reports, [15]: Grade Forms

    public Admin(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.ADMIN, department);
        semesterFolderKey = "";
        reportFolderKeys = new ArrayList<>();
        semesterStarted = false;
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
            secretary.addFolderKeys(reportFolderKeys.get(3));
        }
        else if( secretary.getDepartment().getName().equals("EE") ){
            secretary.setDepartmentFolderKey(reportFolderKeys.get(4));
            secretary.addFolderKeys(reportFolderKeys.get(5));
            secretary.addFolderKeys(reportFolderKeys.get(6));
            secretary.addFolderKeys(reportFolderKeys.get(7));
        }
        else if( secretary.getDepartment().getName().equals("ME") ){
            secretary.setDepartmentFolderKey(reportFolderKeys.get(8));
            secretary.addFolderKeys(reportFolderKeys.get(9));
            secretary.addFolderKeys(reportFolderKeys.get(10));
            secretary.addFolderKeys(reportFolderKeys.get(11));
        }
        else {
            secretary.setDepartmentFolderKey(reportFolderKeys.get(12));
            secretary.addFolderKeys(reportFolderKeys.get(13));
            secretary.addFolderKeys(reportFolderKeys.get(14));
            secretary.addFolderKeys(reportFolderKeys.get(15));
        }
    }
}
