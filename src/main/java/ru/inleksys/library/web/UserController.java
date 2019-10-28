package ru.inleksys.library.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.inleksys.library.model.User;
import ru.inleksys.library.repository.UserRepository;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class UserController {

    private final UserRepository ur;

    @Autowired
    public UserController(UserRepository ur) {
        this.ur = ur;
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("user_list", ur.getAllUsers());
        return "users";
    }

    @GetMapping("/users/new")
    public String initCreationForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping("/users/new")
    public String processCreationForm(@Valid User new_user, BindingResult result, Model model) {
        try {
            ur.findUserByName(new_user.getUsername());
            result.rejectValue("username", "Пользователь с таким именем уже существует");
            model.addAttribute("user", new_user);
            return "user_form";
        }
        catch (Exception exc) {
            if (result.hasErrors()) {
                model.addAttribute("user", new_user);
                return "user_form";
            }
        }
        ur.addUser(new_user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete")
    @ResponseStatus(OK)
    public void deleteUser(@RequestParam String name) {
        ur.delUser(name);
    }

    private String lastName = "";
    private String lastPass = "";

    @GetMapping("/users/edit")
    public String initUpdateBookForm(@RequestParam String name, Model model) {
        User edit_user = ur.findUserByName(name);
        lastName = name;
        lastPass = edit_user.getPassword();
        model.addAttribute(edit_user);
        return "user_form";
    }

    //TODO попробовать проверку тут из list
    @PostMapping("/users/edit")
    public String processUpdateBookForm(@Valid User edit_user, BindingResult result, Model model) {
        try {
            ur.findUserByName(edit_user.getUsername());
            if (!edit_user.getUsername().equals(lastName)) {
                result.rejectValue("username", "Duplicate",  "Пользователь с таким именем уже существует");
                model.addAttribute("user", edit_user);
                return "user_form";
            }
        }
        catch (Exception exc) {
            if (result.hasErrors()) {
                model.addAttribute("user", edit_user);
                return "user_form";
            }
        }
        ur.editUser(edit_user, lastName, lastPass);
        return "redirect:/users";
    }
}
