package com.example.TaskSpringBoot.repository;


import com.example.TaskSpringBoot.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepo extends CrudRepository<User, Long> {
    User findByName(String name);
    User getUserById(Long id);
    User getOne(Long id);
}
