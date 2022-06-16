package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.Classes;

import java.util.List;

public interface ClassService {
    Classes getClass(String number);
    List<Classes> getAll();
}
