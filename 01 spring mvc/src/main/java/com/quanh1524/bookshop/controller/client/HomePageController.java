package com.quanh1524.bookshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping("/")
    public String HomePage(Model model) {
        return "client/homePage/show";
    }
}
