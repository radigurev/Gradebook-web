package Gradebook.web;

import Gradebook.model.binding.LoginUserBindingModel;
import Gradebook.model.binding.UserRegisterBindingModel;
import Gradebook.model.entity.Users;
import Gradebook.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginRegisterController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    public LoginRegisterController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public LoginUserBindingModel loginUserBindingModel() {
        return new LoginUserBindingModel();
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/")
    public String getLoginAndRegisterPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@Valid LoginUserBindingModel loginUserBindingModel) {
        System.out.println(loginUserBindingModel.getEmail());
        System.out.println(loginUserBindingModel.getPassword());
        return "login";
    }

    @PostMapping("/register")
    public String registerProcess(@Valid UserRegisterBindingModel userRegisterBindingModel) {
        userService.userRegister(modelMapper.map(userRegisterBindingModel, Users.class));
        return "login";
    }
}
