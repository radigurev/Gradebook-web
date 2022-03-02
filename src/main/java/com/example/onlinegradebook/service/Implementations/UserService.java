package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.UserCompleteInformationBindingModel;
import com.example.onlinegradebook.model.entity.*;
import com.example.onlinegradebook.model.entity.enums.AccountType;
import com.example.onlinegradebook.model.service.UserRegistrationService;
import com.example.onlinegradebook.repository.RoleRepository;
import com.example.onlinegradebook.repository.SchoolRepository;
import com.example.onlinegradebook.repository.UserRepository;
import com.example.onlinegradebook.service.CitiesService;
import com.example.onlinegradebook.service.CountriesService;
import com.example.onlinegradebook.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UsersService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CitiesService citiesService;
    private final CountriesService countriesService;
    private final SchoolRepository schoolRepository;
    public UserService(RoleRepository roleRepository, ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, CitiesService citiesService, CountriesService countriesService, SchoolRepository schoolRepository) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.citiesService = citiesService;
        this.countriesService = countriesService;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public void saveUser(UserRegistrationService map) {
        LocalDate localDate=LocalDate.now();
        User user=modelMapper.map(map, User.class);
        user.setCreatedAt(java.sql.Date.valueOf(localDate));
        Set<Roles> roles=new HashSet<Roles>();
        roles.add(roleRepository.findByAccountType(AccountType.STUDENT));
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(map.getPassword()));
        userRepository.saveAndFlush(user);
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
        School school=schoolRepository.findBySchool(user.getSchool()).orElse(null);

        userRepository.updateUserInformation(user.getBirthDate(),user.getMiddleName(),user.getPhoneNumber(),school, user.getSsn(), user.getAddress(),user.getZip(),city,country,email);

    }
}
