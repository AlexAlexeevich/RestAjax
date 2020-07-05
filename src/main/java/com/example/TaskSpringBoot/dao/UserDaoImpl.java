package com.example.TaskSpringBoot.dao;

import com.example.TaskSpringBoot.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.createQuery("delete from User u where u.id = : id").setParameter("id", id).executeUpdate();
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    @Override
    public User findByUserName(String name) {
        TypedQuery<User> q = entityManager.createQuery("select u from User u where u.name = :name", User.class);
        q.setParameter("name", name);
        return q.getResultList().stream().findAny().orElse(null);
    }
}
