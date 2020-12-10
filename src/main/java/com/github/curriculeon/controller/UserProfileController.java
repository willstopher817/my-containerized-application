package com.github.curriculeon.controller;

import com.github.curriculeon.model.UserProfile;
import com.github.curriculeon.service.SecurityServiceImpl;
import com.github.curriculeon.service.UserProfileService;
import com.github.curriculeon.validator.UserProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserProfileController {
    private UserProfileService userService;
    private SecurityServiceImpl securityService;
    private UserProfileValidator userValidator;

    @Autowired
    public UserProfileController(UserProfileService userService, UserProfileValidator userValidator, SecurityServiceImpl securityService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.securityService = securityService;
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserProfile());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registration(@ModelAttribute("userForm") UserProfile userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username and password are invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have logged out successfully.");
        }

        return "login";
    }

    @GetMapping(value = {"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }
}
