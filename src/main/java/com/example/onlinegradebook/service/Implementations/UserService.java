package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.binding.UserCompleteInformationBindingModel;
import com.example.onlinegradebook.model.entity.City;
import com.example.onlinegradebook.model.entity.Country;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.entity.enums.AccountType;
import com.example.onlinegradebook.model.service.UserRegistrationService;
import com.example.onlinegradebook.repository.RoleRepository;
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
@Service
public class UserService implements UsersService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CitiesService citiesService;
    private final CountriesService countriesService;
    public UserService(RoleRepository roleRepository, ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, CitiesService citiesService, CountriesService countriesService) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.citiesService = citiesService;
        this.countriesService = countriesService;
    }

    @Override
    public void saveUser(UserRegistrationService map) {
        LocalDate localDate=LocalDate.now();
        User user=modelMapper.map(map, User.class);
        user.setCreatedAt(java.sql.Date.valueOf(localDate));
        user.setRole(roleRepository.findByAccountType(AccountType.STUDENT));
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
        userRepository.updateUserInformation(user.getBirthDate(),user.getMiddleName(),user.getPhoneNumber(),user.getSchool(), user.getSsn(), user.getAddress(),user.getZip(),city,country,email);

    }
}
