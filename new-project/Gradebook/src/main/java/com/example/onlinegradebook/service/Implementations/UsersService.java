package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.entity.Classes;
import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.view.admin.AdminGetClassesWithTeacher;
import com.example.onlinegradebook.model.view.admin.AdminGetNonAssignedStudentsViewModel;
import com.example.onlinegradebook.model.view.admin.AdminStudentsTableView;
import com.example.onlinegradebook.model.view.admin.AdminTeacherTableViewModel;
import com.example.onlinegradebook.model.view.DashboardInfoText;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        user.setMiddleName("");
        user.setUserClass(classService.getClassesSchool("None"));
        userRepository.saveAndFlush(user);
    }

    //Information for users dashboard
    @Override
    public DashboardInfoText getUserInformationForDashboard(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        assert user != null;
        return new DashboardInfoText(String.format("%s %s",user.getFirstName(),user.getLastName()),user.getSchool().getName(),user.getPhoneNumber(),user.getUserClass().getClasses().getClassNumber());
    }

    //Saving new Teachers
    @Override
    public void saveTeacher(TeacherBindingModel teacherBindingModel) {

        User admin=getUser();
        User user= modelMapper.map(teacherBindingModel,User.class);

        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getTeacherRole());
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode("newUser123"));
        user.setMiddleName("");
//        user.setUserClass(classService.getClass("None"));
        assert admin != null;
        user.setSchool(schoolservice.findSchool(admin.getSchool().getName()));
        userRepository.saveAndFlush(user);
    }


    //Getting all teachers from current user school

    @Override
    public List<AdminTeacherTableViewModel> getAllTeacherNames() {
       User admin=getUser();

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

    //Get all unassigned users
    @Override
    public List<AdminStudentsTableView> getUsersBySchool(String school) {
        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getStudentRole());

        System.out.println();
        return userRepository.getAllBySchoolAndRoleIn(schoolservice.findSchool(school),roles)
                .stream()
                .map(u -> modelMapper.map(u, AdminStudentsTableView.class)).toList();
    }

    @Override
    public void updateUserSchool(String id) {
        userRepository.updateSchool(getUser().getSchool(),id);
    }

    @Override
    public void removeUserFromSchool(String id) {
        userRepository.updateSchool(schoolservice.findSchool("None"), id);
    }

    @Override
    public String getUsersBySchoolInJson(School school) {
        JsonArray names=new JsonArray();
        JsonArray id=new JsonArray();
        JsonArray classes=new JsonArray();
        JsonObject jsonObject=new JsonObject();
        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getStudentRole());
     userRepository.getAllBySchoolAndRoleIn(school, roles)
      .forEach(s -> {
            String name;
            if (s.getMiddleName()==null)
                name=String.format("%s %s",s.getFirstName(),s.getLastName());
            else
                name=String.format("%s %s. %s",s.getFirstName(),s.getLastName().charAt(0),s.getLastName());

           if (!(s.getUserClass().getClasses().getClassNumber().equals("None"))) {
               id.add(s.getId());
               names.add(name);
               classes.add(s.getUserClass().getClasses().getClassNumber());
           }
        });
        jsonObject.add("ids",id);
        jsonObject.add("names",names);
        jsonObject.add("classes",classes);

        return jsonObject.toString();
    }

    @Override
    public List<AdminGetNonAssignedStudentsViewModel> getUsersBySchoolAndClass() {
        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getStudentRole());
        return userRepository
                .getAllBySchoolAndUserClassAndRoleIn(getUser().getSchool(), classService.getClass("None"),roles)
                .stream()
                .map(s -> modelMapper.map(s, AdminGetNonAssignedStudentsViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminGetClassesWithTeacher> getClassWithTeacher() {
        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getTeacherRole());

        return userRepository
                .getAllBySchoolAndRoleIn(getUser().getSchool(), roles)
                .stream()
                .filter(t -> t.getUserClass()!=null)
                .map(t -> modelMapper.map(t,AdminGetClassesWithTeacher.class))
                .collect(Collectors.toList());

    }

    @Override
    public void addUserToClass(String id,String userClass) {
        System.out.println();
        userRepository.updateClass(id,classService.getClass(userClass));
    }

    @Override
    public void removeUserFromClass(String id) {
        userRepository.updateClass(id,classService.getClass("None"));
    }


    //Getting current user email
    @Override
    public User getUser() {
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
