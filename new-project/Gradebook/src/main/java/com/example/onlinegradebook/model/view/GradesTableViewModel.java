package com.example.onlinegradebook.model.view;


import com.example.onlinegradebook.model.entity.Grades;

import java.util.ArrayList;
import java.util.List;

public class GradesTableViewModel {

    private List<Grades> firstGrade;
    private List<Grades> secondGrade;

    private Grades firstGradeFinal;
    private Grades secondGradeFinal;
    private Grades gradeFinal;

    private String subject;

    public GradesTableViewModel() {
        this.firstGrade=new ArrayList<>();
        this.secondGrade= new ArrayList<>();
    }



    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Grades> getFirstGrade() {
        return firstGrade;
    }

    public void setFirstGrade(List<Grades> firstGrade) {
        this.firstGrade = firstGrade;
    }

    public List<Grades> getSecondGrade() {
        return secondGrade;
    }

    public void addGradesFirst(Grades grade) {
        this.firstGrade.add(grade);
    }


    public void setSecondGrade(List<Grades> secondGrade) {
        this.secondGrade = secondGrade;
    }

    public Grades getFirstGradeFinal() {
        return firstGradeFinal;
    }

    public void addGradesSecond(Grades grade) {
        this.secondGrade.add(grade);
    }


    public void setFirstGradeFinal(Grades firstGradeFinal) {
        this.firstGradeFinal = firstGradeFinal;
    }

    public Grades getSecondGradeFinal() {
        return secondGradeFinal;
    }

    public void setSecondGradeFinal(Grades secondGradeFinal) {
        this.secondGradeFinal = secondGradeFinal;
    }

    public Grades getGradeFinal() {
        return gradeFinal;
    }

    public void setGradeFinal(Grades gradeFinal) {
        this.gradeFinal = gradeFinal;
    }
}
