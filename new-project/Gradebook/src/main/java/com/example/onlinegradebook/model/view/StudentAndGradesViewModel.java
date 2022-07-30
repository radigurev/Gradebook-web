package com.example.onlinegradebook.model.view;

import java.util.List;

public class StudentAndGradesViewModel {
    private String student;
    private List<GradeViewModel> gradesFirst;
    private GradeViewModel gradesFirstFinal;
    private GradeViewModel gradesSecondFinal;
    private List<GradeViewModel> gradesSecond;
    private GradeViewModel finalGrades;

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public List<GradeViewModel> getGradesFirst() {
        return gradesFirst;
    }

    public void setGradesFirst(List<GradeViewModel> gradesFirst) {
        this.gradesFirst = gradesFirst;
    }

    public GradeViewModel getGradesFirstFinal() {
        return gradesFirstFinal;
    }

    public void setGradesFirstFinal(GradeViewModel gradesFirstFinal) {
        this.gradesFirstFinal = gradesFirstFinal;
    }

    public GradeViewModel getGradesSecondFinal() {
        return gradesSecondFinal;
    }

    public void setGradesSecondFinal(GradeViewModel gradesSecondFinal) {
        this.gradesSecondFinal = gradesSecondFinal;
    }

    public List<GradeViewModel> getGradesSecond() {
        return gradesSecond;
    }

    public void setGradesSecond(List<GradeViewModel> gradesSecond) {
        this.gradesSecond = gradesSecond;
    }

    public GradeViewModel getFinalGrades() {
        return finalGrades;
    }

    public void setFinalGrades(GradeViewModel finalGrades) {
        this.finalGrades = finalGrades;
    }
}
