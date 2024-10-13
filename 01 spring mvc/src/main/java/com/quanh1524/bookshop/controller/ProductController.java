package com.quanh1524.bookshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    @GetMapping("/admin/product")
    public String getProductDashboard() {
        return "admin/dashboard/product/showProduct";
    }
}
