package com.suntyra.pip.lab3.repository;

import com.suntyra.pip.lab3.HibernateUtil;
import com.suntyra.pip.lab3.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

public class UserRepository {

    public User findByUsername(String username) throws NullPointerException {
        try(Session session = getSession()) {
            Query query = session.createQuery("from User where username = :username");
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Session getSession() {
        try {
            return HibernateUtil.getSessionFactory().openSession();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long save(User user) {
        Transaction transaction = null;
        try(Session session = getSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(user);
            transaction.commit();
            return id;
        } catch (RollbackException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public void saveOrUpdate(User user) {
        Transaction transaction = null;
        try(Session session = getSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (RollbackException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    public void delete(User user) {
        Transaction transaction = null;
        try(Session session = getSession()) {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (RollbackException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
