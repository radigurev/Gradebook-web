package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.submodels.adminSubModels.AdminDaysViewModel;
import com.example.onlinegradebook.model.binding.admin.AdminProgramBindingModel;
import com.example.onlinegradebook.model.binding.submodels.adminSubModels.ProgramDayViewModel;
import com.example.onlinegradebook.model.entity.Program;
import com.example.onlinegradebook.repository.ProgramRepository;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.ProgramService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProgramsService implements ProgramService {

    private final ProgramRepository programRepository;
    private final SubjectService subjectService;
    private final UserService userService;
    private final ClassService classService;

    public ProgramsService(ProgramRepository programRepository, SubjectService subjectService, UserService userService, ClassService classService) {
        this.programRepository = programRepository;
        this.subjectService = subjectService;
        this.userService = userService;
        this.classService = classService;
    }

    @Override
    public void saveProgram(String id, AdminProgramBindingModel model) {
        Program program=new Program();
        String update= model.getClasses();
        String number, letter;
        if (update.length() == 3) {
            number = update.substring(0, 2);
            letter = update.substring(2, 3);
        } else {
            number = update.substring(0, 1);
            letter = update.substring(1, 2);
        }

        program.setRoom(model.getRoom());
        program.setNumClass(model.getNumOfClass());
        program.setDay(model.getDay());
        program.setSubject(subjectService.getSubjectByName(model.getSubject()));
        program.setTeacher(userService.getById(id));
        program.setClasses(classService.getClassesSchoolWithLetter(number,letter));
        program.setSchool(userService.getUser().getSchool());
        programRepository.saveAndFlush(program);
    }

    @Override
    public String getAllPrograms() {
        Map<String,AdminDaysViewModel> classes=new HashMap<>();
        programRepository.findAllBySchool(userService.getUser().getSchool()).forEach(p -> {
            System.out.println();
            String schoolClass=String.format("%s%s",p.getClasses().getClasses().getClassNumber(),p.getClasses().getLetter());
            if (!classes.containsKey(schoolClass))
                classes.put(schoolClass,new AdminDaysViewModel());

                switch (p.getDay()) {
                    case "Понеделник":
                        classes.put(schoolClass, addNewList(classes.get(schoolClass),p.getRoom(),p.getSubject().getName(),classes.get(schoolClass).getMonday(),"monday"));
                        break;
                    case "Вторник":
                        classes.put(schoolClass, addNewList(classes.get(schoolClass),p.getRoom(),p.getSubject().getName(),classes.get(schoolClass).getTuesday(),"tuesday"));
                        break;
                    case "Сряда":
                        classes.put(schoolClass, addNewList(classes.get(schoolClass),p.getRoom(),p.getSubject().getName(),classes.get(schoolClass).getWednesday(),"wednesday"));

                        break;
                    case "Четвъртък":
                        classes.put(schoolClass, addNewList(classes.get(schoolClass),p.getRoom(),p.getSubject().getName(),classes.get(schoolClass).getThursday(),"thursday"));

                        break;
                    case "Петък":
                        classes.put(schoolClass, addNewList(classes.get(schoolClass),p.getRoom(),p.getSubject().getName(),classes.get(schoolClass).getFriday(),"friday"));
                        break;
                }
        });
        Gson gson=new Gson();
        System.out.println();
        return gson.toJson(classes);
    }

    private AdminDaysViewModel addNewList(AdminDaysViewModel adminDaysViewModel, String room, String name,List<ProgramDayViewModel> day,String weekDay) {

        day.add(new ProgramDayViewModel(room,name));
        switch (weekDay) {
            case "monday"->adminDaysViewModel.setMonday(day);
            case "thursday"->adminDaysViewModel.setThursday(day);
            case "wednesday"->adminDaysViewModel.setWednesday(day);
            case "tuesday"->adminDaysViewModel.setTuesday(day);
            case "friday"->adminDaysViewModel.setFriday(day);
        }
        return adminDaysViewModel;
    }
}
