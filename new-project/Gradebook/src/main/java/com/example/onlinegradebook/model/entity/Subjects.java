package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
public class Subjects extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
