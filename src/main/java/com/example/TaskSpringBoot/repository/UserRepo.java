package com.example.TaskSpringBoot.repository;


import com.example.TaskSpringBoot.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepo extends CrudRepository<User, Long> {
    User findByFirstName(String firstName);
    User getUserById(Long id);
    User getOne(Long id);
}
