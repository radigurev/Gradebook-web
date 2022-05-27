package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.City;
import com.example.onlinegradebook.repository.CityRepository;
import com.example.onlinegradebook.service.CityService;
import org.springframework.stereotype.Service;

@Service
public class CitiesService implements CityService {

    private final CityRepository cityRepository;

    public CitiesService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City findCityByName(String cityName) {
        return cityRepository.findByCity(cityName).orElse(null);
    }
}
