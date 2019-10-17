package ru.inleksys.library.model;

public class User {

    private String Username;
    private String Password;

    public User(String username) {
        this.Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
