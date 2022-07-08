package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.entity.*;
import com.example.onlinegradebook.model.entity.enums.Roles;
import com.example.onlinegradebook.model.view.admin.*;
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
import java.util.stream.Stream;

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
        Set<Role> roles = new HashSet<>();
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
        return new DashboardInfoText(String.format("%s %s", user.getFirstName(), user.getLastName()), user.getSchool().getName(), user.getPhoneNumber(), user.getUserClass().getClasses().getClassNumber());
    }

    //Saving new Teachers
    @Override
    public void saveTeacher(TeacherBindingModel teacherBindingModel) {
        User user1 = userRepository.findByEmail(teacherBindingModel.getEmail()).orElse(null);
        if (user1 != null) {
            Set<Role> roles = user1.getRole();
            roles.add(roleService.getTeacherRole());
            user1.setRole(roles);
            userRepository.save(user1);
        } else {
            User admin = getUser();

            User user = modelMapper.map(teacherBindingModel, User.class);

            Set<Role> roles = new HashSet<>();

            roles.add(roleService.getTeacherRole());

            user.setRole(roles);

            user.setPassword(passwordEncoder.encode("newUser123"));

            user.setMiddleName("");

//        user.setUserClass(classService.getClass("None"));

            assert admin != null;

            user.setSchool(schoolservice.findSchool(admin.getSchool().getName()));

            userRepository.saveAndFlush(user);
        }
    }


    //Getting all teachers from current user school

    @Override
    public List<AdminTeacherTableViewModel> getAllTeacherNames() {
        User admin = getUser();

        assert admin != null;

        //Listing all teachers from the current user school
        Set<Role> roles = new HashSet<>();

        roles.add(roleService.getTeacherRole());

        return userRepository
                .getAllBySchoolAndRoleIn(admin.getSchool(), roles)
                .stream()
//                .filter(u -> u.getRole().contains(roleService.getTeacherRole()))
                .map(u -> modelMapper.map(u, AdminTeacherTableViewModel.class))
                .toList();

    }

    @Override
    public void addClassToUser(String id, String update) {
        String number, letter;
        if (update.length() == 3) {
            number = update.substring(0, 2);
            letter = update.substring(2, 3);
        } else {
            number = update.substring(0, 1);
            letter = update.substring(1, 2);
        }
        userRepository.updateClass(id, classService.getClassesSchoolWithLetter(number, letter));
    }




    @Override
    public void removeTeacher(String id) {
        //removing user subjects
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        System.out.println();
        List<Role> roles = new ArrayList<>(user.getRole());
        if (user.getRole().stream().map(r -> r.getRole().name()).toList().contains("admin")) {
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).getRole().name().equals("teacher")) {
                    roles.remove(i);
                }
            }
            user.setRole(new HashSet<>(roles));
            userRepository.save(user);
        } else {
            usersSubjectsService.removeUser(userRepository.findById(id));
            //removing user
            userRepository.deleteById(id);
        }
    }

    //Get all unassigned users
    @Override
    public List<AdminStudentsTableView> getUsersBySchool(String school) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getStudentRole());

        System.out.println();
        return userRepository.getAllBySchoolAndRoleIn(schoolservice.findSchool(school), roles)
                .stream()
                .map(u -> modelMapper.map(u, AdminStudentsTableView.class)).toList();
    }

    @Override
    public void updateUserSchool(String id) {
        userRepository.updateSchool(getUser().getSchool(), id);
    }

    @Override
    public void removeUserFromSchool(String id) {
        userRepository.updateSchool(schoolservice.findSchool("None"), id);
    }

    @Override
    public String getUsersBySchoolInJson(School school) {
        JsonArray names = new JsonArray();
        JsonArray id = new JsonArray();
        JsonArray classes = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getStudentRole());
        userRepository.getAllBySchoolAndRoleIn(school, roles)
                .forEach(s -> {
                    String name;
                    if (s.getMiddleName() == null)
                        name = String.format("%s %s", s.getFirstName(), s.getLastName());
                    else
                        name = String.format("%s %s. %s", s.getFirstName(), s.getLastName().charAt(0), s.getLastName());

                    if (!(s.getUserClass().getClasses().getClassNumber().equals("None"))) {
                        id.add(s.getId());
                        names.add(name);
                        classes.add(String.format("%s%s", s.getUserClass().getClasses().getClassNumber(), s.getUserClass().getLetter()));
                    }
                });
        jsonObject.add("ids", id);
        jsonObject.add("names", names);
        jsonObject.add("classes", classes);

        return jsonObject.toString();
    }

    @Override
    public List<AdminGetNonAssignedStudentsViewModel> getUsersBySchoolAndClass() {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getStudentRole());
        return userRepository
                .getAllBySchoolAndUserClassAndRoleIn(getUser().getSchool(), classService.getClassesSchool("None"), roles)
                .stream()
                .map(s -> modelMapper.map(s, AdminGetNonAssignedStudentsViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AdminGetClassesWithTeacher> getClassWithTeacher() {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getTeacherRole());

        return userRepository
                .getAllBySchoolAndRoleIn(getUser().getSchool(), roles)
                .stream()
                .filter(t -> !t.getUserClass().getClasses().getClassNumber().equals("None"))
                .map(t -> modelMapper.map(t, AdminGetClassesWithTeacher.class))
                .collect(Collectors.toList());

    }


    @Override
    public void removeUserFromClass(String id) {
        userRepository.updateClass(id, classService.getClassesSchool("None"));
    }

    @Override
    public List<AdminTeacherProgramTableViewModel> getAllTeacherNamesAndSubjects() {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getTeacherRole());
        List<AdminTeacherProgramTableViewModel> teachersList=new ArrayList<>();
        userRepository.getAllBySchoolAndRoleIn(getUser().getSchool(), roles).forEach(t -> {
            AdminTeacherProgramTableViewModel tableView=new AdminTeacherProgramTableViewModel();
            if(t.getMiddleName().equals(""))
                tableView.setName(String.format("%s %s",t.getFirstName(),t.getLastName()));
            else
                tableView.setName(String.format("%s %s. %s",t.getFirstName(),t.getMiddleName().charAt(0),t.getLastName()));
            List<Subjects> subjects=new ArrayList<>();
            usersSubjectsService.getUserSubjects(t).forEach(s -> {
             subjects.add(s.getSubject());
         });
                tableView.setSubjects(subjects);
                tableView.setId(t.getId());
                teachersList.add(tableView);
        });
        return teachersList;
    }

    @Override
    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }


    //Getting current user email
    @Override
    public User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {

            username = ((UserDetails) principal).getUsername();
        } else {

            username = principal.toString();
        }
        return userRepository.findByEmail(username).orElse(null);
    }
}
