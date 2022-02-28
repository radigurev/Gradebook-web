package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.City;

public interface CitiesService {
    City findCityByName(String cityName);
}
