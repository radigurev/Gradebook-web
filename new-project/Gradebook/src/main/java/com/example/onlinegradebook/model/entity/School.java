package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "school")
@Entity
public class School extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
