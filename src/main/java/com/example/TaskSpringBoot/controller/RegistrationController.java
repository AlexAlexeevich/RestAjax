package com.example.TaskSpringBoot.controller;

import com.example.TaskSpringBoot.model.Role;
import com.example.TaskSpringBoot.model.User;
import com.example.TaskSpringBoot.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    private UserRepo userRepo;

    @Autowired
    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/registration")
    public String registration(ModelMap model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, ModelMap model) {
        User userFromDb = userRepo.findByFirstName(user.getFirstName());
        System.out.println(user.getFirstName() + " " + user.getPassword() + " " + user.getId());

        if(userFromDb != null) {
            System.out.println(" yes");
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        System.out.println("lastName " + user.getLastName());
        userRepo.save(user);
        return "redirect:/login";
    }
}
