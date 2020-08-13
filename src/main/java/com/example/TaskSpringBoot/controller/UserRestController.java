package com.example.TaskSpringBoot.controller;

import com.example.TaskSpringBoot.model.User;
import com.example.TaskSpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> read() {
        List<User> users = userService.listUsers();
        System.out.println("Input GET");
        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") long id) {
        User user = userService.getUserById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping(value = "/users")
//    public ResponseEntity<User> create(@RequestBody User user) {
//        System.out.println(user.getId() + " " + user.getFirstName());
//        userService.addUser(user);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> create(@RequestBody User user) {
        userService.addUser(user);
        User u = userService.getUser(user.getEmail());
        return u != null
                ? new ResponseEntity<>(u, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<Void> update(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(@PathVariable(name = "id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
