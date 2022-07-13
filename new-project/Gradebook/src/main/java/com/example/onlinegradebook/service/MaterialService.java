package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.MaterialPageViewModel;
import com.example.onlinegradebook.model.binding.admin.AdminGetJsonMaterial;
import com.example.onlinegradebook.model.entity.User;

import java.util.List;

public interface MaterialService {
    void saveMaterials(AdminGetJsonMaterial materials);

    List<MaterialPageViewModel> getMaterialsByIfItsTaken(boolean taken);

    void takeMaterial(String id, boolean b, User user);

    void removeMaterial(String id);
}
