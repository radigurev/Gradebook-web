package com.example.onlinegradebook.service;

import com.example.onlinegradebook.model.binding.ChangeMiddleName;
import com.example.onlinegradebook.model.binding.ChangePasswordModel;
import com.example.onlinegradebook.model.binding.TeacherBindingModel;
import com.example.onlinegradebook.model.binding.UserRegisterBindingModel;
import com.example.onlinegradebook.model.binding.superAdmin.AdminAndSchoolBindingModel;
import com.example.onlinegradebook.model.entity.School;
import com.example.onlinegradebook.model.entity.User;
import com.example.onlinegradebook.model.view.StudentAndGradesViewModel;
import com.example.onlinegradebook.model.view.SuperAdmin.AdminAndSchoolViewModel;
import com.example.onlinegradebook.model.view.SuperAdmin.DashboardViewModel;
import com.example.onlinegradebook.model.view.admin.*;
import com.example.onlinegradebook.model.view.DashboardInfoText;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void saveUser(User user);

    DashboardInfoText getUserInformationForDashboard(String name);

    void saveTeacher(TeacherBindingModel teacherBindingModel);

    List<AdminTeacherTableViewModel> getAllTeacherNames();

    void addClassToUser(String id,String update);


    void removeTeacher(String id);

    User getUser();

    List<AdminStudentsTableView> getUsersBySchool(String school);

    void updateUserSchool(String id);

    void removeUserFromSchool(String id);

    String getUsersBySchoolInJson(School school);

    List<AdminGetNonAssignedStudentsViewModel> getUsersBySchoolAndClass();

    List<AdminGetClassesWithTeacher> getClassWithTeacher();

    void removeUserFromClass(String id);

    List<AdminTeacherProgramTableViewModel> getAllTeacherNamesAndSubjects();

    User getById(String id);

    List<AdminGetStudentsWithIdModelView> getUsersByClass(String id);


    List<User> getStudentsBySchoolAndClassAndRole(String id);

    List<StudentAndGradesViewModel> getStudentsWithGrades(String id, String subject);

    Boolean hasMiddleName();

    void changeMiddleName(ChangeMiddleName middleName);

    boolean emailIsAlreadyTaken(String email);

    public User loadUserByEmail(String email);

    DashboardViewModel getUserInformationForDashboardAdmin();

    List<AdminAndSchoolViewModel> getSchoolWithTeachers();

    void saveUserAndSchool(AdminAndSchoolBindingModel adminAndSchoolBindingModel);

    void deleteAdminsAndSchool(String id);

    void addAdminToSchool(String id);

    List<AdminAndSchoolViewModel> getAllAdmins();

    void deleteUser(String id);

    String generatePassword();

    void saveAdminToSchool(String id, UserRegisterBindingModel userRegisterBindingModel);

    List<AdminAndSchoolViewModel> getAllUsers();

    void updateUserPassword(ChangePasswordModel changePasswordModel);

    void updateUser(User user);

    void saveImage(MultipartFile multipartFile) throws IOException;
}
