package net.pfiers.ipm_pe.dto;

public class UserDto {
    private String username;
    private String password;
    private boolean isAdmin;


    public UserDto(String username, String password, boolean isAdmin) {
        setUsername(username);
        setPassword(password);
        setAdmin(isAdmin);
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
}
