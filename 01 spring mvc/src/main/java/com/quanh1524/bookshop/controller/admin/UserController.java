package com.quanh1524.bookshop.controller.admin;

import com.quanh1524.bookshop.domain.Role;
import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.repository.RoleRepository;
import com.quanh1524.bookshop.service.UploadService;
import com.quanh1524.bookshop.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService, RoleRepository roleRepository, UploadService uploadService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

//    @RequestMapping("/")
//    public String showHomePage(Model model) {
//        return "Hello";
//    }

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
    public String createUser(Model model, @ModelAttribute("createForm") @Valid User user, BindingResult bindingResult,
                             @RequestParam("file") MultipartFile file, @RequestParam("roleId") Long roleId
    ) throws IOException {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        //validate
        if (bindingResult.hasErrors()) {
            return "admin/dashboard/user/CreateUser";
        }
        String fileName = this.uploadService.handleSaveFile(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setAvatar(fileName);
        user.setPassword(hashPassword);
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Invalid Role ID: " + roleId));
        user.setRole(role);
        this.userService.handleSaveUser(user);

        return "redirect:/admin/user";
    }

    @RequestMapping(value = "/admin/user/update/{id}")
    public String updateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("updateForm", currentUser);
        model.addAttribute("roles", roleRepository.findAll());
        return "admin/dashboard/user/UpdateUser";
    }

    @PostMapping(value = "/admin/user/update")
    public String updateUser(@ModelAttribute("updateForm") User user, @RequestParam("file") MultipartFile file,
                             @RequestParam("roleId") Long roleId) throws IOException {
        String fileName = this.uploadService.handleSaveFile(file, "avatar");
        user.setAvatar(fileName);
        Role role = roleRepository.findById(roleId).orElseThrow(null);
        user.setRole(role);
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
    public String deleteUser(@ModelAttribute("deleteForm") User user) {
        this.userService.deleteUserById(user.getId());
        return "redirect:/admin/user";
    }

}

