package com.example.onlinegradebook.model.entity;

import com.example.onlinegradebook.model.entity.enums.ResponseType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table
public class Response extends BaseEntity {

    private ResponseType type;
    @Enumerated(EnumType.STRING)
    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }
}
