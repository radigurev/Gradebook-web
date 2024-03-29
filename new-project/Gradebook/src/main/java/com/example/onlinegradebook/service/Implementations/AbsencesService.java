package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.AddAbsencesBindingModel;
import com.example.onlinegradebook.model.binding.submodels.Absences;
import com.example.onlinegradebook.model.entity.Absence;
import com.example.onlinegradebook.model.entity.AbsenceStudent;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.enums.AbsenceType;
import com.example.onlinegradebook.model.view.AbsenceViewModel;
import com.example.onlinegradebook.model.view.AdminAndTeachers.StudentsAbsenceViewModel;
import com.example.onlinegradebook.repository.AbsenceRepository;
import com.example.onlinegradebook.repository.AbsenceStudentRepository;
import com.example.onlinegradebook.service.AbsenceService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AbsencesService implements AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final AbsenceStudentRepository absenceStudentRepository;
    private final UserService userService;
    private final SubjectService subjectService;

    public AbsencesService(AbsenceRepository absenceRepository, AbsenceStudentRepository absenceStudentRepository, @Lazy UserService userService, SubjectService subjectService) {
        this.absenceRepository = absenceRepository;
        this.absenceStudentRepository = absenceStudentRepository;
        this.userService = userService;
        this.subjectService = subjectService;
    }


    public void saveUserAbsence(String id, String idSubject) {
        AbsenceStudent absence = new AbsenceStudent();
        absence.setSchool(userService.getUser().getSchool());
        absence.setStudent(userService.getById(id));
        absence.setDate(LocalDateTime.now());
        absence.setTeacher(userService.getUser());
        absence.setType(absenceRepository.findByType(AbsenceType.Absence));
        absence.setSubject(subjectService.getSubjectById(idSubject));
        absenceStudentRepository.saveAndFlush(absence);
    }

    @Override
    public void init() {
        Arrays.stream(AbsenceType.values()).forEach(a -> {
            Absence absence = new Absence();
            absence.setType(a);
            absenceRepository.saveAndFlush(absence);
        });
    }

    public void saveUserLate(String id, String idSubject) {
        AbsenceStudent absence = new AbsenceStudent();
        absence.setSchool(userService.getUser().getSchool());
        absence.setStudent(userService.getById(id));
        absence.setDate(LocalDateTime.now());
        absence.setTeacher(userService.getUser());
        absence.setType(absenceRepository.findByType(AbsenceType.Late));
        absence.setSubject(subjectService.getSubjectById(idSubject));
        absenceStudentRepository.saveAndFlush(absence);
    }

    @Override
    public List<StudentsAbsenceViewModel> getUsersWithAbsence(String id) {
        List<StudentsAbsenceViewModel> absencesView = new ArrayList<>();
        userService.getUsersByClass(id).forEach(u -> {
            List<AbsenceStudent> late = new ArrayList<>();
            List<AbsenceStudent> forgiven = new ArrayList<>();
            List<AbsenceStudent> absences = new ArrayList<>();
            StudentsAbsenceViewModel model = new StudentsAbsenceViewModel();

            model.setName(u.getName());
            absenceStudentRepository.getAbsenceStudentsByStudent(userService.getById(u.getId())).forEach(a -> {
                switch (a.getType().getType()) {
                    case Absence -> absences.add(a);
                    case Late -> late.add(a);
                    case Forgiven -> forgiven.add(a);
                }
            });
            model.setAbsences(absences);
            model.setForgiven(forgiven);
            model.setLate(late);
            absencesView.add(model);
        });


        return absencesView;
    }

    @Override
    public void removeAbsence(String id) {
        absenceStudentRepository.deleteAbsenceStudentById(id);
    }

    @Override
    public AbsenceStudent findById(String id) {

        return absenceStudentRepository.findById(id).orElse(null);
    }

    @Override
    public AbsenceViewModel getAbsencesForUser() {
        AbsenceViewModel absenceViewModel = new AbsenceViewModel();
        absenceStudentRepository.getAbsenceStudentsByStudent(userService.getUser()).forEach(a -> {

            switch (a.getType().getType()) {
                case Absence -> absenceViewModel.addAbsence(a);
                case Late -> absenceViewModel.addLate(a);
                case Forgiven -> absenceViewModel.addForgiven(a);
            }
        });

            return absenceViewModel;
        }

    @Override
    public List<AbsenceStudent> getAllAbsences() {

        return absenceStudentRepository.getAllBySchool(userService.getUser().getSchool());
    }

    @Override
    public List<AbsenceStudent> getUserAbsences() {
        return absenceStudentRepository.getAbsenceStudentsByStudent(userService.getUser());
    }

    @Override
    public void saveAbsences(String id, AddAbsencesBindingModel addAbsencesBindingModel) {
        List<User> students = userService.getStudentsBySchoolAndClassAndRole(id);

        for (int i = 0; i < addAbsencesBindingModel.getAbsences().size(); i++) {
            Absences absence = addAbsencesBindingModel.getAbsences().get(i);
            if(absence.getType().equals("absence")) {
                saveUserAbsence(students.get(i).getId(),addAbsencesBindingModel.getSubject());
            }else if(absence.getType().equals("late")) {
                saveUserLate(students.get(i).getId(),addAbsencesBindingModel.getSubject());
            }
        }
    }

    @Override
    public void changeToLate(String id) {
        AbsenceStudent absence = absenceStudentRepository.findById(id).orElse(null);

        absence.setType(absenceRepository.findByType(AbsenceType.Late));

        absenceStudentRepository.save(absence);
    }
}

