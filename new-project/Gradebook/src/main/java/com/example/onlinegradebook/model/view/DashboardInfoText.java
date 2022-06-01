package com.example.onlinegradebook.model.view;

public class DashboardInfoText {
    private String name;
    private String school;
    private String email;
    private String userClass;

    public DashboardInfoText(String name, String school, String email, String userClass) {
        this.name = name;
        this.school = school;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }
}
