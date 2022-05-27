package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.UserCompleteInformationBindingModel;
import com.example.onlinegradebook.model.entity.*;
import com.example.onlinegradebook.model.entity.enums.AccountType;
import com.example.onlinegradebook.model.service.UserRegistrationService;
import com.example.onlinegradebook.model.view.TeacherEnterGradesTableView;
import com.example.onlinegradebook.repository.StudentRepository;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService implements UserService {

    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CityService citiesService;
    private final CountryService countriesService;
    private final SchoolService schoolService;
    private final StudentRepository studentRepository;

    public UsersService(RoleService roleService, ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, CityService citiesService, CountryService countriesService, SchoolService schoolService, StudentRepository studentRepository) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.citiesService = citiesService;
        this.countriesService = countriesService;
        this.schoolService = schoolService;
        this.studentRepository = studentRepository;
    }

    @Override
    public void saveUser(UserRegistrationService map) {
        LocalDate localDate=LocalDate.now();
        User user=modelMapper.map(map, User.class);
        user.setCreatedAt(java.sql.Date.valueOf(localDate));
        Set<Roles> roles=new HashSet<Roles>();
        roles.add(roleService.findByAccountType(AccountType.STUDENT));
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(map.getPassword()));
        Student student=new Student();
        student.setUser(user);
        userRepository.saveAndFlush(user);
        studentRepository.saveAndFlush(student);
    }

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username).orElse(null);
    }

    @Override
    public void saveAdditionalInformation(UserCompleteInformationBindingModel user) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        City city=citiesService.findCityByName(user.getCityName());
        Country country=countriesService.findByName(user.getCountryName());
        School school=schoolService.findBySchool(user.getSchool());

        userRepository.updateUserInformation(user.getBirthDate(),user.getMiddleName(),user.getPhoneNumber(),school, user.getSsn(), user.getAddress(),user.getZip(),city,country,email);

    }

    @Override
    public List<TeacherEnterGradesTableView> findAll() {
       List<TeacherEnterGradesTableView> tableView=new ArrayList<>();
       List<User> users=userRepository.findAll();
        users.forEach(u -> {
            TeacherEnterGradesTableView view=new TeacherEnterGradesTableView();
            if(u.getMiddleName()!=null)
            view.setName(String.format("%s %s. %s",u.getFirstName(),u.getMiddleName().charAt(0),u.getLastName()));
            else
                view.setName(String.format("%s %s",u.getFirstName(),u.getLastName()));


            tableView.add(view);
        });

        return tableView;
    }
}
