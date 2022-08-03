package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.GetUserGradesBindingModel;
import com.example.onlinegradebook.model.binding.submodels.IdAndGrade;
import com.example.onlinegradebook.model.entity.Grades;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.view.GradesTableViewModel;
import com.example.onlinegradebook.repository.GradeRepository;
import com.example.onlinegradebook.service.GradeService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class GradesService implements GradeService {

    private final GradeRepository gradeRepository;
    private final UserService userService;
    private final SubjectService subjectService;
    private final ModelMapper modelMapper;
    public GradesService(GradeRepository gradeRepository, UserService userService, SubjectService subjectService, ModelMapper modelMapper) {
        this.gradeRepository = gradeRepository;
        this.userService = userService;
        this.subjectService = subjectService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveGrades(GetUserGradesBindingModel model,String id) {
        List<Grades> grades = new ArrayList<>();

        List<User> studentsInClass=userService.getStudentsBySchoolAndClassAndRole(id);

        IntStream.range(0, model.getGrades().size()).forEach(inx -> {
//            Grades grade = new Grades();
            IdAndGrade idAndGrade = model.getGrades().get(inx);

            if(!idAndGrade.getGrade().equals("")) {
                Grades grade=modelMapper.map(idAndGrade,Grades.class);
                grade.setStudent(studentsInClass.get(inx));
                grade.setTeacher(userService.getUser());
                grade.setDate(LocalDateTime.now());
                grade.setSubject(subjectService.getSubjectById(model.getSubject()));
                grades.add(grade);
            }

        });
        gradeRepository.saveAllAndFlush(grades);
    }

    @Override
    public List<Grades> getGradesByUser(User user) {
        return gradeRepository.getAllByStudent(user);
    }

    @Override
    public List<GradesTableViewModel> getGradesForUser(User user) {
        List<GradesTableViewModel> grades = new ArrayList<>();

        List<Grades> gradesByUser = getGradesByUser(user);

        List<String> subjects= new ArrayList<>();

        gradesByUser.forEach(g-> {
            if(!subjects.contains(g.getSubject().getName())) {
                subjects.add(g.getSubject().getName());
                GradesTableViewModel gradesTableViewModel=new GradesTableViewModel();
                gradesTableViewModel.setSubject(g.getSubject().getName());
                grades.add(gradesTableViewModel);
            }
        });

        gradesByUser.forEach(g -> {
            grades.forEach(gr-> {
                if (gr.getSubject().equals(g.getSubject().getName())) {
                    switch (g.getType()){
                        case "first-semester"->gr.addGradesFirst(g);
                        case "final-first-semester"->gr.setFirstGradeFinal(g);
                        case "second-semester"->gr.addGradesSecond(g);
                        case "final-second-semester"->gr.setSecondGradeFinal(g);
                        case "final"->gr.setGradeFinal(g);
                    }
                }
            });
        });



        return grades;
    }
}
