package com.example.onlinegradebook.model.view;

import java.util.ArrayList;

public class DashboardInfoText {

    //TODO -> Array for message table should be after adding messages to the DB and website

    private String name;
    private String school;
    private String phoneNumber;
    private String userClass;

    public DashboardInfoText() {
    }

    public DashboardInfoText(String name, String school, String phoneNumber, String userClass) {
        this.name = name;
        this.school = school;
        this.phoneNumber = phoneNumber;
        this.userClass = userClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }
}
