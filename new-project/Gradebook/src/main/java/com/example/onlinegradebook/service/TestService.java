package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.AddTestBindingModel;
import com.example.onlinegradebook.model.view.TestViewModel;

import java.util.List;

public interface TestService {
    void saveTest(AddTestBindingModel model, String id);

    List<TestViewModel> getAll();
}
