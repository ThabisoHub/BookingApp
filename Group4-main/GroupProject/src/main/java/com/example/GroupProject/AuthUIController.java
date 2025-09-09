/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.GroupProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Studio20-10
 */
@Controller
public class AuthUIController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage() {
        return "HomePage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute User user, Model model) {
        try {
            userService.register(user.getUsername(), user.getEmail(), user.getPassword());
            return "redirect:/login?signupSuccess";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPasswordSubmit(@RequestParam("email") String email, Model model) {
        try {
            userService.processForgotPassword(email);
            return "redirect:/forgot-password?success";
        } catch (Exception e) {
            return "redirect:/forgot-password?error=" + e.getMessage();
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPasswordSubmit(@RequestParam("token") String token,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/reset-password?token=" + token + "&error=Passwords do not match";
        }
        try {
            userService.processResetPassword(token, password);
            return "redirect:/login?resetSuccess";
        } catch (Exception e) {
            return "redirect:/reset-password?token=" + token + "&error=" + e.getMessage();
        }
    }

    @GetMapping("/website")
    public String home() {
        return "website";
    }

    @GetMapping("/doctorsite")
    public String doctor() {
        return "doctorsite";
    }
}
