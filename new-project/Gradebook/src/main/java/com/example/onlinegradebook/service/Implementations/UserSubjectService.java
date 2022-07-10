package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Subjects;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.UsersSubjects;
import com.example.onlinegradebook.repository.UsersSubjectsRepository;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import com.example.onlinegradebook.service.UsersSubjectsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserSubjectService implements UsersSubjectsService {

    private final UsersSubjectsRepository usersSubjectsRepository;
    private final UserService userService;
    private final SubjectService subjectService;

    public UserSubjectService(UsersSubjectsRepository usersSubjectsRepository,@Lazy UserService userService,SubjectService subjectService) {
        this.usersSubjectsRepository = usersSubjectsRepository;
        this.userService = userService;
        this.subjectService = subjectService;
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

    @Override
    public void addSubjectToUser(String update, String id) {
        UsersSubjects us=new UsersSubjects();
        us.setUser(userService.getById(id));
        us.setSubject(subjectService.getSubjectByName(update));
        usersSubjectsRepository.saveAndFlush(us);
    }
}
