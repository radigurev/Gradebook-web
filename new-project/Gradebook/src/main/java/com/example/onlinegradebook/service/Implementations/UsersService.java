package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.ChangeMiddleName;
import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.entity.*;
import com.example.onlinegradebook.model.view.GradeViewModel;
import com.example.onlinegradebook.model.view.StudentAndGradesViewModel;
import com.example.onlinegradebook.model.view.admin.*;
import com.example.onlinegradebook.model.view.DashboardInfoText;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

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

    private final Gson gson;
    private final GradeService gradeService;

    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, SchoolService schoolservice, ClassService classService, ModelMapper modelMapper, SubjectService subjectService, UsersSubjectsService usersSubjectsService, Gson gson, @Lazy GradeService gradeService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.schoolservice = schoolservice;
        this.classService = classService;
        this.modelMapper = modelMapper;
        this.subjectService = subjectService;
        this.usersSubjectsService = usersSubjectsService;
        this.gson = gson;
        this.gradeService = gradeService;
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
        return new DashboardInfoText(String.format("%s %s", user.getFirstName(), user.getLastName()), user.getSchool().getName(), user.getEmail(), user.getUserClass().getClasses().getClassNumber());
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

            user.setPassword(passwordEncoder.encode("NewUser1234"));

            user.setMiddleName("");

//        user.setUserClass(classService.getClass("None"));

            assert admin != null;

            user.setSchool(schoolservice.findSchool(admin.getSchool().getName()));
            user.setUserClass(classService.getClassesSchool("None"));
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
                .getAllBySchoolAndUserClassAndRoleInOrderByFirstName(getUser().getSchool(), classService.getClassesSchool("None"), roles)
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

    @Override
    public List<AdminGetStudentsWithIdModelView> getUsersByClass(String id) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getStudentRole());
        List<AdminGetStudentsWithIdModelView> students=new ArrayList<>();
        userRepository
                .getAllBySchoolAndUserClassAndRoleInOrderByFirstName(getUser().getSchool(), classService.getClassesSchoolById(id),roles)
                .forEach(u ->{
                AdminGetStudentsWithIdModelView user = new AdminGetStudentsWithIdModelView();
                user.setId(u.getId());
                if(u.getMiddleName().isBlank())
                        user.setName(String.format("%s %s",u.getFirstName(),u.getLastName()));
                    else
                        user.setName(String.format("%s %s. %s",u.getFirstName(),u.getMiddleName().charAt(0),u.getLastName()));

                    students.add(user);
        });
        return students;
    }

    @Override
    public List<User> getStudentsBySchoolAndClassAndRole(String id) {
        ClassesSchool classesSchool=classService.getClassesSchoolById(id);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getStudentRole());
        return userRepository.getAllBySchoolAndUserClassAndRoleInOrderByFirstName(getUser().getSchool(), classesSchool,roles);
    }

    @Override
    public List<StudentAndGradesViewModel> getStudentsWithGrades(String id, String subject) {
        List<StudentAndGradesViewModel> model=new ArrayList<>();

       getStudentsBySchoolAndClassAndRole(id).forEach(s -> {
           StudentAndGradesViewModel entry=new StudentAndGradesViewModel();

          List<GradeViewModel> gradesFirst=new ArrayList<>();
          List<GradeViewModel> gradesSecond=new ArrayList<>();


          gradeService.getGradesByUser(s).forEach(g -> {

              if(g.getSubject().getId().equals(subject)) {

                  GradeViewModel map = modelMapper.map(g, GradeViewModel.class);

                  map.setTeacher(String.format("%s %s",g.getTeacher().getFirstName(),g.getTeacher().getLastName()));

                  map.setSubject(g.getSubject().getName());

                  switch (map.getType()){
                      case "first-semester"->gradesFirst.add(map);
                      case "final-first-semester"->entry.setGradesFirstFinal(map);
                      case "second-semester"->gradesSecond.add(map);
                      case "final-second-semester"->entry.setGradesSecondFinal(map);
                      case "final"->entry.setFinalGrades(map);
                  }
              }
          });
        if(!s.getMiddleName().isEmpty())
            entry.setStudent(String.format("%s %s. %s",s.getFirstName(),s.getMiddleName().charAt(0),s.getLastName()));
        else
            entry.setStudent(String.format("%s %s",s.getFirstName(),s.getLastName()));
        entry.setGradesFirst(gradesFirst);
        entry.setGradesSecond(gradesSecond);
        model.add(entry);
       });

        return model;
    }

    @Override
    public Boolean hasMiddleName() {
        return getUser().getMiddleName() != null;
    }

    @Override
    public void changeMiddleName(ChangeMiddleName middleName) {
        User user = getUser();
        user.setMiddleName(middleName.getMiddleName());

        userRepository.save(user);

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
