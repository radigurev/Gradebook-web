package com.example.onlinegradebook.model.view.AdminAndTeachers;

import com.example.onlinegradebook.model.entity.AbsenceStudent;

import java.util.List;

public class StudentsAbsenceViewModel {

    private String name;
    private List<AbsenceStudent> absences;
    private List<AbsenceStudent> late;
    private List<AbsenceStudent> forgiven;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AbsenceStudent> getAbsences() {
        return absences;
    }

    public void setAbsences(List<AbsenceStudent> absences) {
        this.absences = absences;
    }

    public List<AbsenceStudent> getLate() {
        return late;
    }
    public double getLateSize() {
        return (double) late.size()/2;
    }
    public void setLate(List<AbsenceStudent> late) {
        this.late = late;
    }

    public List<AbsenceStudent> getForgiven() {
        return forgiven;
    }

    public void setForgiven(List<AbsenceStudent> forgiven) {
        this.forgiven = forgiven;
    }
}
