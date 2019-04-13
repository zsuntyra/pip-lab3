package com.suntyra.pip.lab3.repository;

import com.suntyra.pip.lab3.HibernateUtil;
import com.suntyra.pip.lab3.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.RollbackException;

public class UserRepository {
    private Session session = HibernateUtil.getSessionFactory().openSession();

    public User findByUsername(String username) {
        Query query = session.createQuery("from User where username = :username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

    public Long save(User user) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(user);
            transaction.commit();
            return id;
        } catch (RollbackException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void saveOrUpdate(User user) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (RollbackException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(User user) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (RollbackException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
