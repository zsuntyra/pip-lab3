package com.suntyra.pip.lab3.repository;

import com.suntyra.pip.lab3.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "usersRepository")
@ApplicationScoped
public class UserRepository extends Repository {

    public User findByUsername(String username) throws NullPointerException {
        try (Session session = getSession()) {
            Query<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);
            return getSingleResultOrNull(query);
        }
    }
}
