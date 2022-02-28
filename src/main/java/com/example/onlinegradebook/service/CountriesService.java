package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.Country;

public interface CountriesService {
    Country findByName(String countryName);
}
