package com.example.onlinegradebook.model.binding.json;

import com.google.gson.annotations.Expose;

public class MaterialImportBindingModel {
    @Expose
    private String subject;
    @Expose
    private String material;
    @Expose
    private String classes;
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
