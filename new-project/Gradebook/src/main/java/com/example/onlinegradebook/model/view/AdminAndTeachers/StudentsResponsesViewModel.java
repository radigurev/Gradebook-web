package com.example.onlinegradebook.model.view.AdminAndTeachers;

import com.example.onlinegradebook.model.entity.Response;
import com.example.onlinegradebook.model.entity.ResponseStudents;

import java.util.List;

public class StudentsResponsesViewModel {
    private String name;
    private List<ResponseStudents> goodResponses;
    private List<ResponseStudents> badResponses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResponseStudents> getGoodResponses() {
        return goodResponses;
    }

    public void setGoodResponses(List<ResponseStudents> goodResponses) {
        this.goodResponses = goodResponses;
    }

    public List<ResponseStudents> getBadResponses() {
        return badResponses;
    }

    public void setBadResponses(List<ResponseStudents> badResponses) {
        this.badResponses = badResponses;
    }
}
