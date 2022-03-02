package com.example.onlinegradebook.init;

import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.repository.CityRepository;
import com.example.onlinegradebook.repository.CountryRepository;
import com.example.onlinegradebook.repository.SchoolRepository;
import com.example.onlinegradebook.service.RolesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final RolesService rolesService;
   /* private final SchoolRepository schoolRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    */
    public DBInit(RolesService rolesService, SchoolRepository schoolRepository, CityRepository cityRepository, CountryRepository countryRepository) {
        this.rolesService = rolesService;
        /*this.schoolRepository = schoolRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;

         */
    }

    @Override
    public void run(String... args) throws Exception {
        rolesService.initRoles();
       /* School school=new School();
        school.setCity(cityRepository.getById(1L));
        school.setCountry(countryRepository.getById(1L));
        school.setSchool("SPGE John Atanasov");
        school.setCapacity(123152);
        schoolRepository.saveAndFlush(school);
        */
    }
}
