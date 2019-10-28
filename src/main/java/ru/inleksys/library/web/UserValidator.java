package ru.inleksys.library.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.inleksys.library.model.User;
import ru.inleksys.library.repository.UserRepository;

public class UserValidator implements Validator {

    private UserRepository userRepo;
    private String lastName;

    public UserValidator(UserRepository ur, String name) {
        this.userRepo = ur;
        this.lastName= name;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            userRepo.findUserByName(user.getUsername());
            if (!username.equals(lastName)) {
                errors.rejectValue("username", "Duplicate",  "Пользователь с таким именем уже существует");
            }
        }
        catch (Exception exc) {
        }
        if (username.length()<5 | username.length()>14) {
            errors.rejectValue("username", "size", "Длинна поля Имя от 5 до 14 знаков");
        }
        if (password.length()<8 | password.length()>32) {
            errors.rejectValue("password", "size", "Длинна поля Пароль от 8 до 32 знаков");
        }

    }
}
