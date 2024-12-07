package com.quanh1524.bookshop.controller;

import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.repository.UserRepository;
import com.quanh1524.bookshop.service.UserService;
import jakarta.servlet.http.PushBuilder;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String showHomePage(Model model) {
        return "Hello";
    }

    @RequestMapping("/admin/user")
    public String showTableUser(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("usersFromView", users);
        return "admin/dashboard/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String detailUserPage(Model model, @PathVariable long id) {
        User userDetail = this.userService.getUserById(id);
        model.addAttribute("userDetails", userDetail);
        return "admin/dashboard/user/details";
    }

    @RequestMapping("/admin/user/create")
    public String createUserPage(Model model) {
        model.addAttribute("createForm", new User());
        return "admin/dashboard/user/CreateUser";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUser(Model model, @ModelAttribute("createForm") User user) {
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/update/{id}")
    public String updateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("updateForm", currentUser);
        return "admin/dashboard/user/UpdateUser";
    }

    @PostMapping(value = "/admin/user/update")
    public String updateUser(@ModelAttribute("updateForm") User user) {
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping(value = "/admin/user/delete/{id}")
    public String deleteUserPage(Model model, @PathVariable long id) {
        User deleteUser = this.userService.getUserById(id);
        model.addAttribute("deleteForm", deleteUser);
        return "admin/dashboard/user/DeleteUser";
    }

    @PostMapping(value = "/admin/user/delete")
    public String deleteUser(Model model, @ModelAttribute("deleteForm") User user) {
        this.userService.deleteUserById(user.getId());
        return "redirect:/admin/user";
    }

}

