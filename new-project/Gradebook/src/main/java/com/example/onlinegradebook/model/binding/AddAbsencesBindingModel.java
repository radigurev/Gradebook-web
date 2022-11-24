package com.example.onlinegradebook.model.binding;

import com.example.onlinegradebook.model.binding.submodels.Absences;

import java.util.List;

public class AddAbsencesBindingModel {
    List<Absences> absences;

    private String subject;

    public List<Absences> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absences> absences) {
        this.absences = absences;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
