package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends BaseEntityLong{
    private Classes classes;
    private String numberInClass;
    private User user;

    @ManyToOne
    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public String getNumberInClass() {
        return numberInClass;
    }

    public void setNumberInClass(String numberInClass) {
        this.numberInClass = numberInClass;
    }
    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
