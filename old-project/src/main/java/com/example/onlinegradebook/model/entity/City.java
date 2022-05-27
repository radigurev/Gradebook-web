package com.example.onlinegradebook.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City extends BaseEntityLong {
    private String city;

    @Column(name = "city",nullable = false,unique = true)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
