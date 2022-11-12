package com.example.onlinegradebook.model.view.SuperAdmin;

public class DashboardViewModel {

    private String schools;
    private String admins;
    private String subjects;
    private String profiles;

    private String users;

    private String specialities;

    public DashboardViewModel(String schools, String admins, String subjects, String profiles, String specialities, String users) {
        this.schools = schools;
        this.admins = admins;
        this.subjects = subjects;
        this.profiles = profiles;
        this.users = users;
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

    public String getProfiles() {
        return profiles;
    }

    public void setProfiles(String profiles) {
        this.profiles = profiles;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }
}
