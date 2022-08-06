package com.example.onlinegradebook.model.view;

import java.util.ArrayList;

public class DashboardInfoText {

    //TODO -> Array for message table should be after adding messages to the DB and website

    private String name;
    private String school;
    private String phoneNumber;
    private String userClass;

    private String avrGrade;

    private String allGrades;

    private String allResponses;

    private String allAbsences;

    private String tests;

    public DashboardInfoText() {
    }

    public DashboardInfoText(String name, String school, String phoneNumber, String userClass, String avrGrade, String allGrades, String allResponses, String allAbsences, String tests) {
        this.name = name;
        this.school = school;
        this.phoneNumber = phoneNumber;
        this.userClass = userClass;
        this.avrGrade = avrGrade;
        this.allGrades = allGrades;
        this.allResponses = allResponses;
        this.allAbsences = allAbsences;
        this.tests = tests;
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

    public String getAvrGrade() {
        return avrGrade;
    }

    public void setAvrGrade(String avrGrade) {
        this.avrGrade = avrGrade;
    }

    public String getAllGrades() {
        return allGrades;
    }

    public void setAllGrades(String allGrades) {
        this.allGrades = allGrades;
    }

    public String getAllResponses() {
        return allResponses;
    }

    public void setAllResponses(String allResponses) {
        this.allResponses = allResponses;
    }

    public String getAllAbsences() {
        return allAbsences;
    }

    public void setAllAbsences(String allAbsences) {
        this.allAbsences = allAbsences;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }
}
