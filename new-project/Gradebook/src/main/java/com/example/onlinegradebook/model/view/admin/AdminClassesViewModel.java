package com.example.onlinegradebook.model.view.admin;

public class AdminClassesViewModel {
    private String id;
    private String name;
    private String studentClass;

    public AdminClassesViewModel() {
    }

    public AdminClassesViewModel(String name, String studentClass) {
        this.name = name;
        this.studentClass = studentClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}
