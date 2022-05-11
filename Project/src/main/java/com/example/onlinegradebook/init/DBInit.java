package com.example.onlinegradebook.init;

import com.example.onlinegradebook.repository.CityRepository;
import com.example.onlinegradebook.repository.CountryRepository;
import com.example.onlinegradebook.repository.GradeRepository;
import com.example.onlinegradebook.repository.SchoolRepository;
import com.example.onlinegradebook.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {
    private final RoleService rolesService;
    /*
    private final SchoolRepository schoolRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final GradeRepository gradeRepository;
    */
    public DBInit(RoleService rolesService, SchoolRepository schoolRepository, CityRepository cityRepository, CountryRepository countryRepository, GradeRepository gradeRepository) {
        this.rolesService = rolesService;
        /*this.schoolRepository = schoolRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.gradeRepository = gradeRepository;
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
