package com.example.rigel_v1.domain;

import java.util.Set;

public class Admin extends Users{
    public Admin() {
    }

    public Admin(String name, String email, String password, boolean notificationToMail, Department department) {
        super(name, email, password, notificationToMail, Role.ADMIN, department);
    }

    public Admin(String name, String email, String password, boolean notificationToMail, Department department, Set<Notification> notification) {
        super(name, email, password, notificationToMail, Role.ADMIN, department, notification);
    }
}
