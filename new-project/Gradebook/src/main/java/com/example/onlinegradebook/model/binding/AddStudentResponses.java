package com.example.onlinegradebook.model.binding;

import java.util.List;

public class AddStudentResponses {
    private List<StudentResponseBindingModel> responses;

    private String subject;

    public List<StudentResponseBindingModel> getResponses() {
        return responses;
    }

    public void setResponses(List<StudentResponseBindingModel> responses) {
        this.responses = responses;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
