package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Absence;
import com.example.onlinegradebook.model.entity.AbsenceStudent;
import com.example.onlinegradebook.model.entity.enums.AbsenceType;
import com.example.onlinegradebook.model.view.AdminAndTeachers.StudentsAbsenceViewModel;
import com.example.onlinegradebook.repository.AbsenceRepository;
import com.example.onlinegradebook.repository.AbsenceStudentRepository;
import com.example.onlinegradebook.service.AbsenceService;
import com.example.onlinegradebook.service.UserService;
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
    public AbsencesService(AbsenceRepository absenceRepository, AbsenceStudentRepository absenceStudentRepository, UserService userService) {
        this.absenceRepository = absenceRepository;
        this.absenceStudentRepository = absenceStudentRepository;
        this.userService = userService;
    }

    @Override
    public void saveUserAbsence(String id) {
        AbsenceStudent absence=new AbsenceStudent();
        absence.setSchool(userService.getUser().getSchool());
        absence.setStudent(userService.getById(id));
        absence.setDate(LocalDateTime.now());
        absence.setTeacher(userService.getUser());
        absence.setType(absenceRepository.findByType(AbsenceType.Absence));
        absenceStudentRepository.saveAndFlush(absence);
    }

    @Override
    public void init() {
        Arrays.stream(AbsenceType.values()).forEach(a -> {
            Absence absence=new Absence();
            absence.setType(a);
            absenceRepository.saveAndFlush(absence);
        });
    }

    @Override
    public void saveUserLate(String id) {
        AbsenceStudent absence=new AbsenceStudent();
        absence.setSchool(userService.getUser().getSchool());
        absence.setStudent(userService.getById(id));
        absence.setDate(LocalDateTime.now());
        absence.setTeacher(userService.getUser());
        absence.setType(absenceRepository.findByType(AbsenceType.Late));
        absenceStudentRepository.saveAndFlush(absence);
    }

    @Override
    public List<StudentsAbsenceViewModel> getUsersWithAbsence(String id) {
        List<StudentsAbsenceViewModel> absencesView=new ArrayList<>();
        userService.getUsersByClass(id).forEach(u -> {
            List<AbsenceStudent> late= new ArrayList<>();
            List<AbsenceStudent> forgiven= new ArrayList<>();
            List<AbsenceStudent> absences= new ArrayList<>();
            StudentsAbsenceViewModel model=new StudentsAbsenceViewModel();

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
}
