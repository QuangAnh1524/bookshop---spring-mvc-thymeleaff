package com.quanh1524.bookshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/admin/order")
    public String getOrderDashboard() {
        return "admin/dashboard/order/showOrder";
    }
}
