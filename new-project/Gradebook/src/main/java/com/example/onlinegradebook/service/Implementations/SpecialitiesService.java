package com.example.onlinegradebook.service.Implementations;

import com.example.onlinegradebook.model.entity.Speciality;
import com.example.onlinegradebook.model.view.SuperAdmin.SpecialitiesViewModel;
import com.example.onlinegradebook.repository.SpecialityRepository;
import com.example.onlinegradebook.service.SpecialityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialitiesService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialitiesService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public List<Speciality> getAll() {
        return specialityRepository.findAll();
    }

    @Override
    public Speciality getSpeciality(String speciality) {
        return specialityRepository.getSpecialityByName(speciality);
    }

    @Override
    public int getCount() {
        return (int) specialityRepository.count();
    }

    @Override
    public List<SpecialitiesViewModel> getSpecialities() {

        List<SpecialitiesViewModel> specialities = new ArrayList<>();

        specialityRepository.findAll().forEach(x -> {
            SpecialitiesViewModel model = new SpecialitiesViewModel();

            model.setId(x.getId());

            model.setName(x.getName());

            specialities.add(model);
        });

        return specialities;
    }

    @Override
    public void removeSpeciality(String id) {

        specialityRepository.deleteById(id);

    }

    @Override
    public void saveSpeciality(SpecialitiesViewModel specialitiesViewModel) {
        Speciality speciality = new Speciality();

        speciality.setName(specialitiesViewModel.getName());

        specialityRepository.saveAndFlush(speciality);
    }
}
