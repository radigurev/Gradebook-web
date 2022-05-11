package com.example.onlinegradebook.model.binding;

import java.util.List;

public class GradesBindingModel {
    private List<String> grades;

    public List<String> getGrades() {
        return grades;
    }

    public void setGrades(List<String> grades) {
        this.grades = grades;
    }

    public void addGrade(String grade) {
        this.grades.add(grade);
    }
}
