package com.example.onlinegradebook.model.view.admin;

import com.example.onlinegradebook.model.entity.Subjects;

import java.util.List;
import java.util.Set;

public class AdminTeacherProgramTableViewModel {
    private String id;
    private String name;

    private List<Subjects> subjects;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }
}
