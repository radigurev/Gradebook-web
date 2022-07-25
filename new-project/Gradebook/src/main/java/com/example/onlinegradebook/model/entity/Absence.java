package com.example.onlinegradebook.model.entity;

import com.example.onlinegradebook.model.entity.enums.AbsenceType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table
public class Absence extends BaseEntity{

    private AbsenceType type;

    @Enumerated(EnumType.STRING)
    public AbsenceType getType() {
        return type;
    }

    public void setType(AbsenceType type) {
        this.type = type;
    }
}
