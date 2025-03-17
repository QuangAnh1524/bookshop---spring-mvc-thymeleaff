package com.quanh1524.bookshop.controller.admin;

import com.quanh1524.bookshop.service.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping("/admin")
    public String getDashboard(Model model) {
        SecurityUtil.UserInfo userInfo = securityUtil.getCurrentUserInfo();
        if (!userInfo.isAdmin()) {
            return "redirect:/";
        }
        model.addAttribute("userEmail", userInfo.getEmail());
        return "admin/dashboard/show";
    }
}
