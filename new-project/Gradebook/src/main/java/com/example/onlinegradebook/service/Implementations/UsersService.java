package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.UsersSubjects;
import com.example.onlinegradebook.model.view.admin.AdminTeacherTableViewModel;
import com.example.onlinegradebook.model.view.DashboardInfoText;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final SchoolService schoolservice;
    private final ClassService classService;
    private final ModelMapper modelMapper;
    private final SubjectService subjectService;
    private final UsersSubjectsService usersSubjectsService;
    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, SchoolService schoolservice, ClassService classService, ModelMapper modelMapper, SubjectService subjectService, UsersSubjectsService usersSubjectsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.schoolservice = schoolservice;
        this.classService = classService;
        this.modelMapper = modelMapper;
        this.subjectService = subjectService;
        this.usersSubjectsService = usersSubjectsService;
    }

    //Saving new users
    //TODO -> profile pictures
    @Override
    public void saveUser(User user) {
        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getStudentRole());
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSchool(schoolservice.findSchool("None"));
        user.setUserClass(classService.getClass("None"));
        userRepository.saveAndFlush(user);
    }

    //Information for users dashboard
    @Override
    public DashboardInfoText getUserInformationForDashboard(String name) {
        User user = userRepository.findByEmail(name).orElse(null);

        assert user != null;
        return new DashboardInfoText(String.format("%s %s",user.getFirstName(),user.getLastName()),user.getSchool().getName(),user.getPhoneNumber(),user.getUserClass().getClassNumber());
    }

    //Saving new Teachers
    @Override
    public void saveTeacher(TeacherBindingModel teacherBindingModel) {

        User admin=getUserEmail();
        User user= modelMapper.map(teacherBindingModel,User.class);

        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getTeacherRole());
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode("newUser123"));
        user.setMiddleName("");
        assert admin != null;
        user.setSchool(schoolservice.findSchool(admin.getSchool().getName()));
        userRepository.saveAndFlush(user);
    }


    //Getting all teachers from current user school

    @Override
    public List<AdminTeacherTableViewModel> getAllTeacherNames() {
       User admin=getUserEmail();

        assert admin != null;

        //Listing all teachers from the current user school
        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getTeacherRole());
        return userRepository
                .getAllBySchoolAndRoleIn(admin.getSchool(),roles)
                .stream()
//                .filter(u -> u.getRole().contains(roleService.getTeacherRole()))
                .map(u -> modelMapper.map(u, AdminTeacherTableViewModel.class))
                .toList();
    }

    @Override
    public void updateTeacherSubject(String update,String id) {
        System.out.println();
        User user=userRepository.findById(id).orElse(null);
        usersSubjectsService.saveNewSubjects(user, subjectService.getSubjectByName(update));
    }

    @Override
    public void updateTeacherClass(String id, String update) {
        userRepository.updateClass(id,classService.getClass(update));
    }

    @Override
    public void removeTeacher(String id) {
        //removing user subjects
        usersSubjectsService.removeUser(userRepository.findById(id));
        //removing user
        userRepository.deleteById(id);
    }


    //Getting current user email

    private User getUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {

            username = ((UserDetails)principal).getUsername();
        } else {

            username = principal.toString();
        }
        return userRepository.findByEmail(username).orElse(null);
    }
}
