package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.entity.Role;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.view.AdminTeacherTableViewModel;
import com.example.onlinegradebook.model.view.DashboardInfoText;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.ClassService;
import com.example.onlinegradebook.service.RoleService;
import com.example.onlinegradebook.service.SchoolService;
import com.example.onlinegradebook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final SchoolService schoolservice;
    private final ClassService classService;
    private final ModelMapper modelMapper;
    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, SchoolService schoolservice, ClassService classService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.schoolservice = schoolservice;
        this.classService = classService;
        this.modelMapper = modelMapper;
    }

    //Saving new users
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {

             username = ((UserDetails)principal).getUsername();
        } else {

             username = principal.toString();
        }

        User user= modelMapper.map(teacherBindingModel,User.class);
        User admin=userRepository.findByEmail(username).orElse(null);
        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getTeacherRole());
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode("newUser123"));
        assert admin != null;
        user.setSchool(schoolservice.findSchool(admin.getSchool().getName()));
        userRepository.saveAndFlush(user);
    }


    //Getting all teachers from admin school

    @Override
    public List<AdminTeacherTableViewModel> getAllTeacherNames() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {

            username = ((UserDetails)principal).getUsername();
        } else {

            username = principal.toString();
        }
        User admin=userRepository.findByEmail(username).orElse(null);

        assert admin != null;

        Set<Role> roles=new HashSet<>();
        roles.add(roleService.getTeacherRole());
        return userRepository
                .getAllBySchoolAndRoleIn(admin.getSchool(),roles)
                .stream()
//                .filter(u -> u.getRole().contains(roleService.getTeacherRole()))
                .map(u -> modelMapper.map(u, AdminTeacherTableViewModel.class))
                .toList();
    }


}
