package net.pfiers.ipm_pe.dto;

import javax.validation.constraints.NotBlank;

public class UserDtoSignup {
    @NotBlank
    private String username;
    @NotBlank
    private String passwordRaw;
    @NotBlank
    private String passwordRawRepeated;
    private boolean isAdmin;


    public UserDtoSignup() {
        setUsername("");
        setPasswordRaw("");
        setPasswordRawRepeated("");
        setAdmin(false);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordRaw() {
        return passwordRaw;
    }

    public void setPasswordRaw(String passwordRaw) {
        this.passwordRaw = passwordRaw;
    }

    public String getPasswordRawRepeated() {
        return passwordRawRepeated;
    }

    public void setPasswordRawRepeated(String passwordRawRepeated) {
        this.passwordRawRepeated = passwordRawRepeated;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
