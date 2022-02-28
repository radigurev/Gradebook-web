package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.City;
import com.example.onlinegradebook.repository.CityRepository;
import com.example.onlinegradebook.service.CitiesService;
import org.springframework.stereotype.Service;

@Service
public class CityService implements CitiesService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City findCityByName(String cityName) {
        return cityRepository.findByCity(cityName).orElse(null);
    }
}
