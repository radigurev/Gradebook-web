package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.entity.Country;

public interface CountryService {
    Country findByName(String countryName);
}
