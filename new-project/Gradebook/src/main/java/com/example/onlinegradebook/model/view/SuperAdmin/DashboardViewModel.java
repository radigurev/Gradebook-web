package com.example.onlinegradebook.model.view.SuperAdmin;

public class DashboardViewModel {

    private String schools;
    private String admins;
    private String subjects;
    private String specialities;

    public DashboardViewModel(String schools, String admins, String subjects, String specialities) {
        this.schools = schools;
        this.admins = admins;
        this.subjects = subjects;
        this.specialities = specialities;
    }

    public String getSchools() {
        return schools;
    }

    public void setSchools(String schools) {
        this.schools = schools;
    }

    public String getAdmins() {
        return admins;
    }

    public void setAdmins(String admins) {
        this.admins = admins;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }
}
