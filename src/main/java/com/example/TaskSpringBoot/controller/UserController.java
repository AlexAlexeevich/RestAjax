package com.example.TaskSpringBoot.controller;

import com.example.TaskSpringBoot.dao.UserDao;
import com.example.TaskSpringBoot.model.Role;
import com.example.TaskSpringBoot.model.User;
import com.example.TaskSpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String showUsers(ModelMap model) {
        model.addAttribute("currentUser", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("user", new User());

        Iterable<User> persons = userService.listUsers();
        model.addAttribute("persons", persons);
        model.addAttribute("listOfRoles", userService.listRoles());
        return "admin";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin";
    }

    @PostMapping("/admin/save")
    public String addUser(@ModelAttribute("user") User user, String[] idRoles) {
        Set<Role> tempRole = new HashSet<>();
        for (int i = 0; i < idRoles.length; i++) {
            tempRole.add(userService.getRoleByName(idRoles[i]));
        }
        user.setRoles(tempRole);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/editUser")
    public String editUser(@ModelAttribute("user") User user, String[] idRoles) {
        Set<Role> tempRole = new HashSet<>();
        if (idRoles == null) {
            tempRole.add(new Role(2L, "ROLE_USER"));
        } else {
            for (int i = 0; i < idRoles.length; i++) {
                tempRole.add(userService.getRoleByName(idRoles[i]));
            }
        }
        user.setRoles(tempRole);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userHome(ModelMap model) {
        model.addAttribute("currentUser", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        return "user";
    }
}