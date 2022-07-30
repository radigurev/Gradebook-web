package com.example.onlinegradebook.model.binding;

import com.example.onlinegradebook.model.binding.submodels.IdAndGrade;

import java.util.ArrayList;
import java.util.List;

public class GetUserGradesBindingModel {
    private List<IdAndGrade> grades;

    private String subject;
    public GetUserGradesBindingModel() {
        this.grades = new ArrayList<>();
    }

    public List<IdAndGrade> getGrades() {
        return grades;
    }

    public void addGrades(String grade) {
        IdAndGrade idAndGrade=new IdAndGrade();
        idAndGrade.setGrade(grade);
        grades.add(idAndGrade);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
