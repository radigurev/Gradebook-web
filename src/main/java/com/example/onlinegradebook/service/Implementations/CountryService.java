package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Country;
import com.example.onlinegradebook.repository.CountryRepository;
import com.example.onlinegradebook.service.CountriesService;
import org.springframework.stereotype.Service;

@Service
public class CountryService implements CountriesService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country findByName(String countryName) {
        return countryRepository.findByCountry(countryName).orElse(null);
    }
}
