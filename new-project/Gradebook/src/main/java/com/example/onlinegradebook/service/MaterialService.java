package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.MaterialPageViewModel;
import com.example.onlinegradebook.model.binding.json.MaterialImportBindingModel;
import com.example.onlinegradebook.model.entity.User;

import java.util.List;

public interface MaterialService {
    void saveMaterials(MaterialImportBindingModel[] materials);

    List<MaterialPageViewModel> getMaterialsByIfItsTaken(boolean taken);

    void takeMaterial(String id, boolean b, User user);

    void removeMaterial(String id);
}
