package com.example.TaskSpringBoot.dao;


import com.example.TaskSpringBoot.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getAllRoles();

    Role getRoleByName(String role);
}
