package com.example.onlinegradebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.security.auth.Subject;
import java.time.LocalDateTime;

@Entity
@Table(name = "material_school")
public class MaterialSchool extends BaseEntity {

    private Material material;
    private School school;
    private User user;
    private ClassesSchool classes;
    private LocalDateTime date;
    private boolean isTaken;

    private Subjects subject;
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @ManyToOne
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @ManyToOne
    public Subjects getSubject() {
        return subject;
    }
    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    @ManyToOne
    public ClassesSchool getClasses() {
        return classes;
    }

    public void setClasses(ClassesSchool classes) {
        this.classes = classes;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
