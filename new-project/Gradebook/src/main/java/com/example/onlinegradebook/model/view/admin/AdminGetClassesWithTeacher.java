package com.example.onlinegradebook.model.view.admin;

import com.example.onlinegradebook.model.entity.ClassesSchool;

public class AdminGetClassesWithTeacher {

    private String firstName,lastName,middleName;
    private ClassesSchool userClass;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public ClassesSchool getUserClass() {
        return userClass;
    }

    public void setUserClass(ClassesSchool userClass) {
        this.userClass = userClass;
    }
}
