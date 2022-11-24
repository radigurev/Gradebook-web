package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.globals.GlobalConstraints;
import com.example.onlinegradebook.model.binding.ChangeMiddleName;
import com.example.onlinegradebook.model.binding.ChangePasswordModel;
import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.binding.UserRegisterBindingModel;
import com.example.onlinegradebook.model.binding.superAdmin.AdminAndSchoolBindingModel;
import com.example.onlinegradebook.model.entity.*;
import com.example.onlinegradebook.model.view.GradeViewModel;
import com.example.onlinegradebook.model.view.StudentAndGradesViewModel;
import com.example.onlinegradebook.model.view.SuperAdmin.AdminAndSchoolViewModel;
import com.example.onlinegradebook.model.view.SuperAdmin.DashboardViewModel;
import com.example.onlinegradebook.model.view.admin.*;
import com.example.onlinegradebook.model.view.DashboardInfoText;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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
    private final AbsenceService absenceService;
    private final ResponseService responseService;
    private final UsersSubjectsService usersSubjectsService;
    private final SpecialityService specialityService;
    private final Gson gson;
    private final GradeService gradeService;
    private final TestService testService;
    private final MailService mailService;

    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, SchoolService schoolservice, ClassService classService, ModelMapper modelMapper, SubjectService subjectService, AbsenceService absenceService, @Lazy ResponseService responseService, UsersSubjectsService usersSubjectsService, SpecialityService specialityService, Gson gson, @Lazy GradeService gradeService, @Lazy TestService testService, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.schoolservice = schoolservice;
        this.classService = classService;
        this.modelMapper = modelMapper;
        this.subjectService = subjectService;
        this.absenceService = absenceService;
        this.responseService = responseService;
        this.usersSubjectsService = usersSubjectsService;
        this.specialityService = specialityService;
        this.gson = gson;
        this.gradeService = gradeService;
        this.testService = testService;
        this.mailService = mailService;
    }

    //Saving new users
    //TODO -> profile pictures
    @Override
    public void saveUser(User user) {
        try {
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getStudentRole());
            user.setRole(roles);
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            user.setSchool(schoolservice.findSchool("None"));
            user.setMiddleName("");
            user.setUserClass(classService.getClassesSchool("None"));
            userRepository.saveAndFlush(user);

            String body = String.format("Беше ви създаден ученически акаунт в нашата система! \n Username: %s \n Password: %s", user.getEmail(), password);

            mailService.sendMail(user.getEmail(), GlobalConstraints.newUserAccount, body);
        } catch (Exception e) {
        }
    }

    //Information for users dashboard
    @Override
    public DashboardInfoText getUserInformationForDashboard(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        assert user != null;
        double sum = 0;
        List<Grades> grades;
        List<AbsenceStudent> absences;
        List<ResponseStudents> responses;
        List<Test> tests;
        if (!user.getRole().contains(roleService.getStudentRole())) {
            grades = gradeService.getAllSchoolGrades();
            absences = absenceService.getAllAbsences();
            responses = responseService.getAllResponses();
            tests = testService.getAllTests();
        } else {
            grades = gradeService.getGradesByUser(user);
            absences = absenceService.getUserAbsences();
            responses = responseService.getUserResponses();
            tests = testService.getUserTests();
        }

        for (Grades grade : grades) {
            sum += Double.parseDouble(grade.getGrade());
        }
        sum /= grades.size();
        assert user != null;
        return new DashboardInfoText(String.format("%s %s", user.getFirstName(), user.getLastName()),
                user.getSchool().getName(), user.getEmail(),
                user.getUserClass().getClasses().getClassNumber(),
                String.format("%.2f", sum), Integer.toString(grades.size()),
                Integer.toString(responses.size()), Integer.toString(absences.size()),
                Integer.toString(tests.size()));
//        return new DashboardInfoText();
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

            String password = generatePassword();

            user.setPassword(passwordEncoder.encode(password));

            user.setMiddleName("");

//        user.setUserClass(classService.getClass("None"));

            assert admin != null;

            user.setSchool(schoolservice.findSchool(admin.getSchool().getName()));
            user.setUserClass(classService.getClassesSchool("None"));
            userRepository.saveAndFlush(user);

            String body = String.format("Беше ви създаден учителски акаунт в нашата система! \n Училище: %s \n Username: %s \n Password: %s", user.getSchool().getName(), user.getEmail(), user.getPassword());

            mailService.sendMail(user.getEmail(), GlobalConstraints.newTeacherAccount, body);
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
        User user = getById(id);
        mailService.sendMail(user.getEmail(), "Добавени към ново училище", String.format("Вие бяхте добавени към %s!", user.getSchool().getName()));
    }

    @Override
    public void removeUserFromSchool(String id) {
        userRepository.updateSchool(schoolservice.findSchool("None"), id);

        User user = getById(id);
        mailService.sendMail(user.getEmail(), "Премахнат от училище", String.format("Вие бяхте премахнати от %s!", user.getSchool().getName()));
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
        List<AdminTeacherProgramTableViewModel> teachersList = new ArrayList<>();
        userRepository.getAllBySchoolAndRoleIn(getUser().getSchool(), roles).forEach(t -> {
            AdminTeacherProgramTableViewModel tableView = new AdminTeacherProgramTableViewModel();
            if (t.getMiddleName().equals(""))
                tableView.setName(String.format("%s %s", t.getFirstName(), t.getLastName()));
            else
                tableView.setName(String.format("%s %s. %s", t.getFirstName(), t.getMiddleName().charAt(0), t.getLastName()));
            List<Subjects> subjects = new ArrayList<>();
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
        List<AdminGetStudentsWithIdModelView> students = new ArrayList<>();
        userRepository
                .getAllBySchoolAndUserClassAndRoleInOrderByFirstName(getUser().getSchool(), classService.getClassesSchoolById(id), roles)
                .forEach(u -> {
                    AdminGetStudentsWithIdModelView user = new AdminGetStudentsWithIdModelView();
                    user.setId(u.getId());
                    if (u.getMiddleName().isBlank())
                        user.setName(String.format("%s %s", u.getFirstName(), u.getLastName()));
                    else
                        user.setName(String.format("%s %s. %s", u.getFirstName(), u.getMiddleName().charAt(0), u.getLastName()));

                    students.add(user);
                });
        return students;
    }

    @Override
    public List<User> getStudentsBySchoolAndClassAndRole(String id) {
        ClassesSchool classesSchool = classService.getClassesSchoolById(id);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getStudentRole());
        return userRepository.getAllBySchoolAndUserClassAndRoleInOrderByFirstName(getUser().getSchool(), classesSchool, roles);
    }

    @Override
    public List<StudentAndGradesViewModel> getStudentsWithGrades(String id, String subject) {
        List<StudentAndGradesViewModel> model = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        getStudentsBySchoolAndClassAndRole(id).forEach(s -> {
            StudentAndGradesViewModel entry = new StudentAndGradesViewModel();

            List<GradeViewModel> gradesFirst = new ArrayList<>();
            List<GradeViewModel> gradesSecond = new ArrayList<>();


            gradeService.getGradesByUser(s).forEach(g -> {

                if (g.getSubject().getId().equals(subject)) {

                    GradeViewModel map = modelMapper.map(g, GradeViewModel.class);

                    map.setTeacher(String.format("%s %s", g.getTeacher().getFirstName(), g.getTeacher().getLastName()));

                    map.setSubject(g.getSubject().getName());

                    map.setDate(formatter.format(g.getDate()));

                    switch (map.getType()) {
                        case "first-semester" -> gradesFirst.add(map);
                        case "final-first-semester" -> entry.setGradesFirstFinal(map);
                        case "second-semester" -> gradesSecond.add(map);
                        case "final-second-semester" -> entry.setGradesSecondFinal(map);
                        case "final" -> entry.setFinalGrades(map);
                    }
                }
            });
            if (!s.getMiddleName().isEmpty())
                entry.setStudent(String.format("%s %s. %s", s.getFirstName(), s.getMiddleName().charAt(0), s.getLastName()));
            else
                entry.setStudent(String.format("%s %s", s.getFirstName(), s.getLastName()));

            if (entry.getGradesFirstFinal() == null) {
                entry.setGradesFirstFinal(new GradeViewModel());
                entry.getGradesFirstFinal().setGrade("");
            }
            if (entry.getGradesSecondFinal() == null) {
                entry.setGradesSecondFinal(new GradeViewModel());
                entry.getGradesSecondFinal().setGrade("");
            }
            if (entry.getFinalGrades() == null) {
                entry.setFinalGrades(new GradeViewModel());
                entry.getFinalGrades().setGrade("");
            }
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

    @Override
    public boolean emailIsAlreadyTaken(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null;
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

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public DashboardViewModel getUserInformationForDashboardAdmin() {
        int schoolCount = schoolservice.getSchoolCount();
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getAdminRole());
        int adminCount = (int) userRepository.getAllByRoleIn(roles).stream().count();

        int subjectCount = subjectService.getSubjectCount();

        int specialitiesCount = specialityService.getCount();

        int usersCount = (int) userRepository.count() - 1;

        return new DashboardViewModel(Integer.toString(schoolCount), Integer.toString(adminCount)
                , Integer.toString(subjectCount), Integer.toString(subjectCount)
                , Integer.toString(specialitiesCount), Integer.toString(usersCount));
    }

    @Override
    public List<AdminAndSchoolViewModel> getSchoolWithTeachers() {

        List<AdminAndSchoolViewModel> schools = new ArrayList<>();

        schoolservice.getAllSchools().forEach(s -> {
            if (!s.getName().equals("None")) {
                AdminAndSchoolViewModel school = new AdminAndSchoolViewModel();

                school.setSchool(s);
                school.setUser(userRepository.getUserBySchoolAndMainAdmin(s, true));

                schools.add(school);
            }
        });

        return schools;
    }

    @Override
    public void saveUserAndSchool(AdminAndSchoolBindingModel model) {
        User user = new User();
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setEmail(model.getEmail());
        String password = generatePassword();
        user.setPassword(passwordEncoder.encode(password));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getAdminRole());
        user.setRole(roles);
        user.setMiddleName("");
        user.setMainAdmin(true);
        user.setUserClass(classService.getClassesSchool("None"));

        School school = new School();
        school.setName(model.getSchool());
        schoolservice.saveSchool(school);
        school = schoolservice.findSchool(model.getSchool());
        user.setSchool(school);

        userRepository.saveAndFlush(user);

        String body = String.format("Беше ви създаден директорски акаунт в нашата система! \n Училище: %s \n Username: %s \n Password: %s", user.getSchool().getName(), user.getEmail(), password);

        mailService.sendMail(user.getEmail(), GlobalConstraints.newAdminAccount, body);
    }

    @Override
    public void deleteAdminsAndSchool(String id) {
        userRepository.deleteAdminsBySchool(id);

        schoolservice.deleteSchoolById(id);

        classService.deleteClassesBySchool(id);

        subjectService.removeSubjectSchoolsBySchoolId(id);
    }

    @Override
    public void addAdminToSchool(String id) {


    }

    @Override
    public List<AdminAndSchoolViewModel> getAllAdmins() {
        List<AdminAndSchoolViewModel> schools = new ArrayList<>();

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getAdminRole());

        userRepository.getAllByRoleIn(roles).forEach(u -> {
            AdminAndSchoolViewModel school = new AdminAndSchoolViewModel();

            school.setUser(u);
            school.setSchool(u.getSchool());

            schools.add(school);
        });

        return schools;

    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        if (user.isMainAdmin())
            deleteAdminsAndSchool(user.getSchool().getId());
        else
            userRepository.delete(user);
    }

    @Override
    public String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        return RandomStringUtils.random(15, characters);
    }

    @Override
    public void saveAdminToSchool(String id, UserRegisterBindingModel model) {
        User user = new User();
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        user.setEmail(model.getEmail());
        String password = generatePassword();
        user.setPassword(passwordEncoder.encode(password));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getAdminRole());
        user.setRole(roles);
        user.setMiddleName("");
        user.setUserClass(classService.getClassesSchool("None"));

        user.setSchool(schoolservice.findSchoolById(id));

        userRepository.saveAndFlush(user);

        String body = String.format("Беше ви създаден директорски акаунт в нашата система! \n Училище: %s \n Username: %s \n Password: %s", user.getSchool().getName(), user.getEmail(), user.getPassword());

        mailService.sendMail(user.getEmail(), GlobalConstraints.newAdminAccount, body);
    }

    @Override
    public List<AdminAndSchoolViewModel> getAllUsers() {
        List<AdminAndSchoolViewModel> schools = new ArrayList<>();

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getStudentRole());
        roles.add(roleService.getTeacherRole());
        roles.add(roleService.getAdminRole());

        userRepository.getAllByRoleIn(roles).forEach(u -> {
            AdminAndSchoolViewModel school = new AdminAndSchoolViewModel();

            school.setUser(u);
            school.setSchool(u.getSchool());

            schools.add(school);
        });

        return schools;
    }

    @Override
    public void updateUserPassword(ChangePasswordModel changePasswordModel) {
        User user = getUser();

        user.setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));

        userRepository.save(user);

        mailService.sendMail(user.getEmail(), "Сменена парола", "Вашата парола беше сменена. Ако не сте я сменили вие моля свържете с нашия съпорт.");
    }

    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user.getFirstName(), user.getMiddleName(), user.getLastName(),
                user.getEmail(), user.getPhoneNumber(), user.getId());
    }

    @Override
    public void saveImage(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        User user = getUser();
        String fileLocation = new File("src\\main\\resources\\static\\images\\").getAbsolutePath();
        Path path = Paths.get(fileLocation + "\\" + user.getId() + multipartFile.getOriginalFilename());
        Files.write(path, bytes);

        userRepository.updateUserImage("/images/" + user.getId() + multipartFile.getOriginalFilename(), user.getId());
    }


}
