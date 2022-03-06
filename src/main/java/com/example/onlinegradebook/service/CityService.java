package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.City;

public interface CityService {
    City findCityByName(String cityName);
}
