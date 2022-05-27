package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Country;
import com.example.onlinegradebook.repository.CountryRepository;
import com.example.onlinegradebook.service.CountryService;
import org.springframework.stereotype.Service;

@Service
public class CountriesService implements CountryService {

    private final CountryRepository countryRepository;

    public CountriesService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country findByName(String countryName) {
        return countryRepository.findByCountry(countryName).orElse(null);
    }
}
