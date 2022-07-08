package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.admin.AdminProgramBindingModel;
import com.example.onlinegradebook.model.entity.Program;
import com.example.onlinegradebook.repository.ProgramRepository;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.ProgramService;
import com.example.onlinegradebook.service.SubjectService;
import com.example.onlinegradebook.service.UserService;
import org.springframework.stereotype.Service;

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
        program.setDateHours(model.getDateHours());

        programRepository.saveAndFlush(program);
    }
}
