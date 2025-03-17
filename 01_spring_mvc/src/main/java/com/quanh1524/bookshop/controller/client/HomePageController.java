package com.quanh1524.bookshop.controller.client;

import com.quanh1524.bookshop.domain.*;
import com.quanh1524.bookshop.domain.dto.RegisterDTO;
import com.quanh1524.bookshop.repository.UserRepository;
import com.quanh1524.bookshop.service.CartService;
import com.quanh1524.bookshop.service.ProductService;
import com.quanh1524.bookshop.service.SecurityUtil;
import com.quanh1524.bookshop.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final CartService cartService;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository, SecurityUtil securityUtil, CartService cartService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String HomePage(Model model, HttpSession session,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "4") int size,
                           @RequestParam(defaultValue = "id") String sortBy) {

        Page<Product> productPage = productService.getProductPaged(page, size, sortBy);
        model.addAttribute("productsFromView", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        SecurityUtil.UserInfo userInfo = securityUtil.getCurrentUserInfo();
        int cartItemCount = 0;
        if (userInfo.isLoggedIn()) {
            Cart cart = cartService.getOrCreateCart(userService.findByEmail(userInfo.getEmail()).getFirst(), session);
            cartItemCount = cart.getSum();
        }
        model.addAttribute("cartItemCount", cartItemCount);
        model.addAttribute("isLoggedIn", userInfo.isLoggedIn());
        model.addAttribute("userEmail", userInfo.getEmail());
        model.addAttribute("isAdmin", userInfo.isAdmin());
        return "client/homePage/show";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(value = "logout", required = false) String logout, HttpSession session) {
        if (logout != null) {
            model.addAttribute("message", "Log out successfully");
        }
        //sau khi dang nhap thanh cong, ktra pendingProductId
        SecurityUtil.UserInfo userInfo = securityUtil.getCurrentUserInfo();
        if (userInfo.isLoggedIn() && session.getAttribute("pendingProductId") != null) {
            Long productId = (Long) session.getAttribute("pendingProductId");
            cartService.addProductToCart(productId, 1, userService.findByEmail(userInfo.getEmail()).getFirst(), session);
            session.removeAttribute("pendingProductId");
            return "redirect:/";
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

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpSession session) {
        SecurityUtil.UserInfo userInfo = securityUtil.getCurrentUserInfo();
        if (!userInfo.isLoggedIn()) {
            return "redirect:/login";
        }
        Cart cart = cartService.getOrCreateCart(userService.findByEmail(userInfo.getEmail()).getFirst(), session);
        int cartItemCount = cart.getSum();
        model.addAttribute("cart", cart);
        model.addAttribute("isLoggedIn", userInfo.isLoggedIn());
        model.addAttribute("userEmail", userInfo.getEmail());
        model.addAttribute("isAdmin", userInfo.isAdmin());
        model.addAttribute("cartItemCount", cartItemCount);
        return "client/cart/cart";
    }

}
