package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.StudentResponseBindingModel;
import com.example.onlinegradebook.model.entity.Response;
import com.example.onlinegradebook.model.entity.ResponseStudents;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.enums.ResponseType;
import com.example.onlinegradebook.model.view.AdminAndTeachers.StudentsResponsesViewModel;
import com.example.onlinegradebook.model.view.ResponseViewModel;
import com.example.onlinegradebook.repository.ResponseRepository;
import com.example.onlinegradebook.repository.ResponseStudentsRepository;
import com.example.onlinegradebook.service.ResponseService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ResponsesService implements ResponseService {

    private final ResponseRepository responseRepository;
    private final UserService userService;
    private final SubjectService subjectService;
    private final ResponseStudentsRepository responseStudentsRepository;

    public ResponsesService(ResponseRepository responseRepository, UserService userService, SubjectService subjectService, ResponseStudentsRepository responseStudentsRepository) {
        this.responseRepository = responseRepository;
        this.userService = userService;
        this.subjectService = subjectService;
        this.responseStudentsRepository = responseStudentsRepository;
    }

    @Override
    public void init() {
        Arrays.stream(ResponseType.values()).forEach(r -> {
            Response response=new Response();
            response.setType(r);
            responseRepository.saveAndFlush(response);
        });
    }

    @Override
    public void saveResponseForStudent(String id, StudentResponseBindingModel model) {
        ResponseStudents response=new ResponseStudents();
        User user= userService.getUser();
        response.setSchool(user.getSchool());
        response.setStudent(userService.getById(id));
        response.setDescription(model.getDescription());
        response.setSubject(subjectService.getSubjectByNameAndSchool(user.getSchool(),model.getSubject()));
        response.setType(responseRepository.findByType(getEnumType(model.getType())));
        response.setTeacher(user);
        response.setDateTime(LocalDateTime.now());
        responseStudentsRepository.saveAndFlush(response);
    }

    @Override
    public List<StudentsResponsesViewModel> getResponses(String id) {
        List<StudentsResponsesViewModel> view=new ArrayList<>();
        userService.getUsersByClass(id).forEach(u -> {
            StudentsResponsesViewModel entry= new StudentsResponsesViewModel();
            List<ResponseStudents> goodResponses= new ArrayList<>();
            List<ResponseStudents> badResponses= new ArrayList<>();
            responseStudentsRepository.findAllByStudent(userService.getById(u.getId())).forEach(r -> {
                    if (r.getType().getType().equals(ResponseType.Good)) {
                        goodResponses.add(r);
                    }else
                        badResponses.add(r);
            });
            entry.setGoodResponses(goodResponses);
            entry.setBadResponses(badResponses);
            entry.setName(u.getName());
            view.add(entry);
        });
        System.out.println();
        return view;
    }

    @Override
    public List<ResponseViewModel> getResponsesForStudent() {
        List<ResponseViewModel> model = new ArrayList<>();

        List<String> subjects= new ArrayList<>();

        List<ResponseStudents> allByStudent = responseStudentsRepository.findAllByStudent(userService.getUser());

        allByStudent.forEach(s -> {
            if(!subjects.contains(s.getSubject().getSubject().getName())) {
                subjects.add(s.getSubject().getSubject().getName());
                ResponseViewModel responseViewModel = new ResponseViewModel();
                responseViewModel.setSubject(s.getSubject().getSubject().getName());
                model.add(responseViewModel);
            }
        });

        allByStudent.forEach(s -> {
            model.forEach(m -> {
                if (m.getSubject().equals(s.getSubject().getSubject().getName())) {
                    if(s.getType().getType().equals(ResponseType.Good))
                        m.addGoodResponse(s);
                    else
                        m.addBadResponse(s);
                }
            });
        });

        return model;
    }

    private ResponseType getEnumType(String type) {
        return ResponseType.Bad.name().equals(type) ? ResponseType.Bad : ResponseType.Good;
    }
}
