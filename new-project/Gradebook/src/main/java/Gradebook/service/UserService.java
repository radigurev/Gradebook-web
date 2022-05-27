package Gradebook.service;

import Gradebook.model.binding.UserRegisterBindingModel;
import Gradebook.model.entity.Users;

public interface UserService {
    void userRegister(Users user);

}
