package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.models.User;
import web.service.UserService;

@Controller
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/userCreate")
    public String createForm(User user) {
        return "userCreate";
}

    @PostMapping("/userCreate")
    public String createUser(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/userDelete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }
    @GetMapping("/userUpdate/{id}")
    public String updateUserForm (@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "userUpdate";
    }
    @PostMapping("/userUpdate")
    public String updateUser(Long id, User user) {
        userService.updateUser(id, user);
        return "redirect:/users";

    }
}
