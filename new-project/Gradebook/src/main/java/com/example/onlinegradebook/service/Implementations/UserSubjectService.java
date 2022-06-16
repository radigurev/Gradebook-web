package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.UsersSubjects;
import com.example.onlinegradebook.repository.UsersSubjectsRepository;
import com.example.onlinegradebook.service.UsersSubjectsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserSubjectService implements UsersSubjectsService {

    private final UsersSubjectsRepository usersSubjectsRepository;

    public UserSubjectService(UsersSubjectsRepository usersSubjectsRepository) {
        this.usersSubjectsRepository = usersSubjectsRepository;
    }

    @Override
    public Set<UsersSubjects> getUserSubjects(User user) {
        return usersSubjectsRepository.findAllByUser(user);
    }

    @Override
    public void saveNewSubjects(User user, Subjects subjects) {
        UsersSubjects us=usersSubjectsRepository.findByUserAndSubject(user,subjects).orElse(null);
        if(us==null) {
            UsersSubjects usersSubjects = new UsersSubjects();
            usersSubjects.setSubject(subjects);
            usersSubjects.setUser(user);
            usersSubjectsRepository.saveAndFlush(usersSubjects);

        }
    }

    @Override
    public void removeUser(Optional<User> byId) {
        usersSubjectsRepository.removeAllByUser(byId.orElse(null));
    }
}
