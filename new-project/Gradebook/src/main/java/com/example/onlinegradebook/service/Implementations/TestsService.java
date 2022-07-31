package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.AddTestBindingModel;
import com.example.onlinegradebook.model.entity.Test;
import com.example.onlinegradebook.model.view.TestViewModel;
import com.example.onlinegradebook.repository.TestRepository;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.TestService;
import com.example.onlinegradebook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestsService implements TestService {

    private final UserService userService;
    private final TestRepository testRepository;
    private final SubjectService subjectService;
    private final ModelMapper modelMapper;
    private final ClassService classService;

    public TestsService(UserService userService, TestRepository testRepository, SubjectService subjectService, ModelMapper modelMapper, ClassService classService) {
        this.userService = userService;
        this.testRepository = testRepository;
        this.subjectService = subjectService;
        this.modelMapper = modelMapper;
        this.classService = classService;
    }

    @Override
    public void saveTest(AddTestBindingModel model, String id) {
        model.setTeacher(userService.getUser());
        Test test = modelMapper.map(model,Test.class);
        test.setSubject(subjectService.getSubjectById(model.getSubject()));
        test.setSchoolClass(classService.getClassesSchoolById(id));
        test.setSchool(userService.getUser().getSchool());
        testRepository.saveAndFlush(test);
    }

    @Override
    public List<TestViewModel> getAll() {
        List<TestViewModel> tests=new ArrayList<>();

        testRepository.getAllBySchoolOrderByDateDesc(userService.getUser().getSchool()).forEach(t -> {
            TestViewModel test=new TestViewModel();
            test.setClassWithLetter(String.format("%s%s",t.getSchoolClass().getClasses().getClassNumber(),t.getSchoolClass().getLetter()));
            test.setDate(t.getDate());
            String teacher;
            if (t.getTeacher().getMiddleName().isBlank())
                teacher=String.format("%s %s",t.getTeacher().getFirstName(),t.getTeacher().getLastName());
            else
                teacher=String.format("%s %s. %s",t.getTeacher().getFirstName(),t.getTeacher().getMiddleName().charAt(0),t.getTeacher().getLastName());

            test.setTeacher(teacher);
            test.setSubject(t.getSubject().getName());

            if(t.getTeacher() == userService.getUser()) {
                tests.add(0,test);
            }else
                tests.add(test);
        });



        return tests;
    }
}
