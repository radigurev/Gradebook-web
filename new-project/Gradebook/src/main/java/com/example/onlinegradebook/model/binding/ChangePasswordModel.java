package com.example.onlinegradebook.model.binding;

import javax.validation.constraints.Size;

public class ChangePasswordModel {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    @Size(min = 3, message = "Полето 'Стара Парола' е задължително да има поне 3 символа!")
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    @Size(min = 3, message = "Полето 'Нова Парола' е задължително да има поне 3 символа!")
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
