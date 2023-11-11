package com.example.ultimavez.model.DTO;

public class RedefinePasswordDTO {

    private String email, newPassword, newPasswordConfirmation;

    public RedefinePasswordDTO(String email, String newPassword, String newPasswordConfirmation) {
        this.email = email;
        this.newPassword = newPassword;
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }
}
