package com.example.onlinegradebook.model.view;

import com.example.onlinegradebook.model.entity.Absence;
import com.example.onlinegradebook.model.entity.AbsenceStudent;

import java.util.ArrayList;
import java.util.List;

public class AbsenceViewModel {
    private String subject;

    private List<AbsenceStudent> late;
    private List<AbsenceStudent> forgiven;
    private List<AbsenceStudent> absence;

    public AbsenceViewModel() {
        this.late = new ArrayList<>();
        this.absence = new ArrayList<>();
        this.forgiven = new ArrayList<>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<AbsenceStudent> getLate() {
        return late;
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

    public List<AbsenceStudent> getAbsence() {
        return absence;
    }

    public void setAbsence(List<AbsenceStudent> absence) {
        this.absence = absence;
    }

    public void addLate(AbsenceStudent absence) {
        this.late.add(absence);
    }

    public void addAbsence(AbsenceStudent absence) {
        this.absence.add(absence);
    }
    public void addForgiven(AbsenceStudent absence) {
        this.forgiven.add(absence);
    }

}
