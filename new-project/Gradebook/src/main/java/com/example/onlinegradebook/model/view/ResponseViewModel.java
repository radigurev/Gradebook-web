package com.example.onlinegradebook.model.view;

import com.example.onlinegradebook.model.entity.Response;
import com.example.onlinegradebook.model.entity.ResponseStudents;

import java.util.ArrayList;
import java.util.List;

public class ResponseViewModel {
    private String subject;

    private List<ResponseStudents> goodResponse;

    private List<ResponseStudents> badResponse;

    public ResponseViewModel() {
        this.goodResponse= new ArrayList<>();
        this.badResponse= new ArrayList<>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<ResponseStudents> getGoodResponse() {
        return goodResponse;
    }

    public void setGoodResponse(List<ResponseStudents> goodResponse) {
        this.goodResponse = goodResponse;
    }

    public void  addGoodResponse(ResponseStudents response) {
        this.goodResponse.add(response);
    }

    public List<ResponseStudents> getBadResponse() {
        return badResponse;
    }

    public void setBadResponse(List<ResponseStudents> badResponse) {
        this.badResponse = badResponse;
    }

    public void addBadResponse(ResponseStudents response) {
        this.badResponse.add(response);
    }
}
