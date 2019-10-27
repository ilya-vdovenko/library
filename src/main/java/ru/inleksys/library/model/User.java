package ru.inleksys.library.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class User {

    @NotEmpty(message = "Поле Имя не может быть пустым")
    @Size(min = 5, max = 30, message = "Длинна поля Имя от {min} до {max} знаков")
    private String Username;
    @NotEmpty(message = "Поле Пароль не может быть пустым")
    @Size(min = 8, max = 16, message = "Длинна поля Пароль от {min} до {max} знаков")
    private String Password;

    public User() {
    }

    public User(String username, String password) {
        this.Username = username;
        this.Password = password;
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
