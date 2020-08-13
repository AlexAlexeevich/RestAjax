package com.example.TaskSpringBoot.service;

import com.example.TaskSpringBoot.dao.RoleDao;
import com.example.TaskSpringBoot.dao.UserDao;
import com.example.TaskSpringBoot.model.Role;
import com.example.TaskSpringBoot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public User getUser(String login) {
        return userDao.findByUserEmail(login);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> listRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.getRoleByName(name);
    }

    @Override
    @Transactional
    public User addUser(User user) {
        User userFromDB = userDao.findByUserEmail(user.getEmail());
        if (userFromDB != null) {
            return userFromDB;
        }
        userDao.addUser(user);
        return userFromDB;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
