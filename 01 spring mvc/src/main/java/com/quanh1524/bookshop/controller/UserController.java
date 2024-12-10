package com.quanh1524.bookshop.controller;

import com.quanh1524.bookshop.domain.Role;
import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.repository.RoleRepository;
import com.quanh1524.bookshop.repository.UserRepository;
import com.quanh1524.bookshop.service.RoleService;
import com.quanh1524.bookshop.service.UploadService;
import com.quanh1524.bookshop.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.PushBuilder;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UploadService uploadService;


    public UserController(UserService userService, RoleRepository roleRepository, UploadService uploadService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.uploadService = uploadService;
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

    @GetMapping("/admin/user/create")
    public String createUserPage(Model model) {
        User user = new User();
        user.setRole(new Role());
        model.addAttribute("createForm", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/dashboard/user/CreateUser";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUser(Model model, @ModelAttribute("createForm") User user, @RequestParam("roleId") Long roleId,
                             @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = this.uploadService.handleSaveFile(file, "avatar");
        System.out.println(fileName);

        // this.userService.handleSaveUser(user, roleId);

        return "redirect:/admin/user";  // Chuyển hướng sau khi tạo thành công
    }


    @RequestMapping(value = "/admin/user/update/{id}")
    public String updateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("updateForm", currentUser);
        return "admin/dashboard/user/UpdateUser";
    }

    @PostMapping(value = "/admin/user/update")
    public String updateUser(@ModelAttribute("updateForm") User user) {
//        this.userService.handleSaveUser(user);
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

