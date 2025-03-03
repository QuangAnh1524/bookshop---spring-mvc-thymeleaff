package com.quanh1524.bookshop.controller.client;

import com.quanh1524.bookshop.domain.Product;
import com.quanh1524.bookshop.domain.Role;
import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.domain.dto.RegisterDTO;
import com.quanh1524.bookshop.repository.UserRepository;
import com.quanh1524.bookshop.service.ProductService;
import com.quanh1524.bookshop.service.SecurityUtil;
import com.quanh1524.bookshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository, SecurityUtil securityUtil) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    @GetMapping("/")
    public String HomePage(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productsFromView", productList);
        SecurityUtil.UserInfo userInfo = securityUtil.getCurrentUserInfo();
        model.addAttribute("isLoggedIn", userInfo.isLoggedIn());
        model.addAttribute("userEmail", userInfo.getEmail());
        model.addAttribute("isAdmin", userInfo.isAdmin());
        return "client/homePage/show";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(value = "logout", required = false) String logout) {
        if (logout != null) {
            model.addAttribute("message", "Log out successfully");
        }
        return "client/auth/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("userForm", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("userForm") @Valid RegisterDTO registerDTO, BindingResult result, Model model) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            result.rejectValue("email", "error.email", "Email này đã tồn tại.");
        }
        if (!registerDTO.isPasswordMatching()) {
            result.rejectValue("confirmPassword", "error.userForm", "Mật khẩu xác nhận không khớp");
        }
        if (result.hasErrors()) {
            return "client/auth/register";
        }
        User user = this.userService.registerDTOtoUser(registerDTO);
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        Role role = this.userService.getRoleByName("USER");
        user.setRole(role);
        this.userService.handleSaveUser(user);

        return "redirect:/login";
    }
}
