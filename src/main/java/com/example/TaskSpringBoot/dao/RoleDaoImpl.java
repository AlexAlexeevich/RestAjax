package com.example.TaskSpringBoot.dao;

import com.example.TaskSpringBoot.model.Role;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Role> getAllRoles() {
        TypedQuery<Role> query = entityManager.createQuery("select r from Role r", Role.class);
        return query.getResultList();
    }

    @Override
    public Role getRoleByName(String role) {
        TypedQuery<Role> q = entityManager.createQuery("select r from Role r where r.role = :role", Role.class);
        q.setParameter("role", role);
        return q.getResultList().stream().findAny().orElse(null);
    }
}
