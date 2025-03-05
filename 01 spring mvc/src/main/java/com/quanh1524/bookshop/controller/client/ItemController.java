package com.quanh1524.bookshop.controller.client;

import com.quanh1524.bookshop.domain.Product;
import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.service.CartService;
import com.quanh1524.bookshop.service.ProductService;
import com.quanh1524.bookshop.service.SecurityUtil;
import com.quanh1524.bookshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ItemController {

    private final ProductService productService;
    private final SecurityUtil securityUtil;
    private final CartService cartService;
    private final UserService userService;

    public ItemController(ProductService productService, SecurityUtil securityUtil, CartService cartService, UserService userService) {
        this.productService = productService;
        this.securityUtil = securityUtil;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("productDetails", product);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpSession session, Model model) {
        SecurityUtil.UserInfo userInfo = securityUtil.getCurrentUserInfo();
        if (!userInfo.isLoggedIn()) {
            //them vao session de khi dang nhap lai thi co o gio hang
            session.setAttribute("pendingProductId", id);
            return "redirect:/login";
        }
        //neu da dang nhap, them vao gio hang
        cartService.addProductToCart(id, 1, userService.findByEmail(userInfo.getEmail()).getFirst(), session);
        return "redirect:/";
    }
}
