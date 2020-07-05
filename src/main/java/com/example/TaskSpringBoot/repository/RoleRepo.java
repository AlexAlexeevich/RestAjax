package com.example.TaskSpringBoot.repository;


import com.example.TaskSpringBoot.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Long> {
    //Role findRoleByRole(String role);
}
