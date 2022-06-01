package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "classes")
public class Classes extends BaseEntity{

    private String classNumber;

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}
