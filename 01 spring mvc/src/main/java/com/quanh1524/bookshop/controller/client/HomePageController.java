package com.quanh1524.bookshop.controller.client;

import com.quanh1524.bookshop.domain.Product;
import com.quanh1524.bookshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {

    private final ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String HomePage(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productsFromView", productList);
        return "client/homePage/show";
    }
}
