package ru.inleksys.library.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.inleksys.library.model.User;
import ru.inleksys.library.repository.UserRepository;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class UserController {

    private final UserRepository ur;
    private String lastName = "";
    private String lastPass = "";

    @Autowired
    public UserController(UserRepository ur) {
        this.ur = ur;
    }

    @InitBinder("user")
    public void initPetBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new UserValidator(ur, lastName));
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
        if (result.hasErrors()) {
            model.addAttribute("user", new_user);
            return "user_form";
        }
        ur.addUser(new_user);
        return "redirect:/users";
    }

    @GetMapping("/users/delete")
    @ResponseStatus(OK)
    public void deleteUser(@RequestParam String name) {
        ur.delUser(name);
    }

    @GetMapping("/users/edit")
    public String initUpdateBookForm(@RequestParam String name, Model model) {
        User edit_user = ur.findUserByName(name);
        lastName = name;
        lastPass = edit_user.getPassword();
        model.addAttribute(edit_user);
        return "user_form";
    }

    @PostMapping("/users/edit")
    public String processUpdateBookForm(@Valid User edit_user, BindingResult result, Model model) {
        if (result.hasErrors()) {
                model.addAttribute("user", edit_user);
                return "user_form";
        }
        ur.editUser(edit_user, lastName, lastPass);
        return "redirect:/users";
    }
}
