package net.pfiers.ipm_pe.dto;

import java.util.Locale;

public class UserDto {
    private String username;
    private String password;
    private boolean isAdmin;
    private Locale locale;


    public UserDto(String username, String password, boolean isAdmin, Locale locale) {
        setUsername(username);
        setPassword(password);
        setAdmin(isAdmin);
        setLocale(locale);
    }

    public UserDto(String username, String password, boolean isAdmin) {
        this(username, password, isAdmin, Locale.ENGLISH);
    }

    public UserDto() {
        this("", "", false);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
