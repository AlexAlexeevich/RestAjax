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

import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/admin")
    public String showUsers(ModelMap model) {
        Iterable<User> persons = userService.listUsers();
        model.addAttribute("persons", persons);
        return "admin";
    }

    @GetMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id, ModelMap model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    /////////////для кнопки
//@PostMapping(value = "/admin/deleteUser")
//public String deleteUser(@ModelAttribute("user") User user) {
//	System.out.println(user.getId());
//	//userService.delete(user);
//	return "redirect:/admin";
//}

    @GetMapping("/admin/addUser")
    public String formAddUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("listOfRoles", userService.listRoles());
        return "saveForm";
    }

    @GetMapping("/admin/updateUser/{id}")
    public String formUpdateUser(@PathVariable("id") String id, ModelMap model) {
        User temp = userService.getUserById(Long.parseLong(id));
        model.addAttribute("user", temp);
        model.addAttribute("listOfRoles", userService.listRoles());
        return "saveForm";
    }

    @PostMapping("/admin/save")
    public String addUser(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
                          @RequestParam(value = "password") String password, ModelMap model, @RequestParam("roles") String[] roles) {
        Set<Role> tempRole = new HashSet<>();
        for (int i = 0; i < roles.length; i++) {
            tempRole.add(userService.getRoleByName(roles[i]));
        }
        User user;
        if (id.isEmpty()) {
            userService.addUser(new User(name, password, tempRole));
        } else {
            user = new User(Long.valueOf(id), name, password, tempRole);
            userService.updateUser(user);
        }
        return "redirect:/admin";
    }

	@GetMapping("/home")
	public String userHome(ModelMap model) {
		model.addAttribute("user", SecurityContextHolder
				.getContext().getAuthentication().getPrincipal());
		return "home";
	}
}