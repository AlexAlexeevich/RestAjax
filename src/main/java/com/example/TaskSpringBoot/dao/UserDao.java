package com.example.TaskSpringBoot.dao;


import com.example.TaskSpringBoot.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    User getUserById(Long id);

    List<User> getAllUsers();

    User findByUserEmail(String login);
}
