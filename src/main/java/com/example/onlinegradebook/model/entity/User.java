package com.example.onlinegradebook.model.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.management.relation.Role;
import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntityString {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private Date birthDate;
    private Date createdAt;
    private String school;
    private String ssn;
    private Integer zip;
    private boolean approved;
    private Set<Roles> role;
    private Country country;
    private City city;

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(unique = true)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Column(columnDefinition = "TEXT")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "birth_date")
    public Date getBirthDate() {
        return birthDate;
    }

    @Column(name = "zip")
    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSchool() {
        return school;
    }
    @Column(name = "ssn")
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Column(name = "is_approved")
    @ColumnDefault("false")
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @ManyToOne
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Roles> getRole() {
        return role;
    }

    public void setRole(Set<Roles> role) {
        this.role = role;
    }
}
