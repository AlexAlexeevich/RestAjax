package com.example.TaskSpringBoot.service;


import com.example.TaskSpringBoot.model.Role;
import com.example.TaskSpringBoot.model.User;

import java.util.List;

public interface UserService {
    User getUser(String login);
    User addUser(User user);
    List<User> listUsers();
    User getUserById(long id);
    void updateUser(User user);
    void deleteUser(long id);
    List<Role> listRoles();
    Role getRoleByName(String name);

}
