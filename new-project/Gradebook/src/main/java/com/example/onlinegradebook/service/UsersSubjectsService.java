package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.UsersSubjects;

import java.util.Optional;
import java.util.Set;

public interface UsersSubjectsService {

    public Set<UsersSubjects> getUserSubjects(User user);

    void saveNewSubjects(User user,Subjects subjects);

    void removeUser(Optional<User> byId);
}
