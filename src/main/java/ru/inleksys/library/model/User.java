package ru.inleksys.library.model;

import javax.validation.constraints.Size;

public class User {

    @Size(min = 5, max = 30, message = "Длинна поля Имя от {min} до {max} знаков")
    private String username;

    @Size(min = 8, max = 32, message = "Длинна поля Пароль от {min} до {max} знаков")
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
}
