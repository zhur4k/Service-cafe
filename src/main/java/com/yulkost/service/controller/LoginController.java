package com.yulkost.service.controller;

import com.yulkost.service.model.User;
import com.yulkost.service.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration"; }

    @PostMapping("/registration")
    public String registry(User user,Model model){
        if(userService.registryUser(user))
            return "redirect:/login";
        model.addAttribute("message","Пользователь с таким телефоном уже существует");
        return "registration";
    }
}
