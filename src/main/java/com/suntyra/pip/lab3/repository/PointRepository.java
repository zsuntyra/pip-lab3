package com.suntyra.pip.lab3.repository;

import com.suntyra.pip.lab3.model.Point;
import com.suntyra.pip.lab3.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.NoResultException;
import java.util.List;

@ManagedBean(name = "pointsRepository")
@ApplicationScoped
public class PointRepository extends Repository {

    public List<Point> findByUser(User user) throws NullPointerException {
        try (Session session = getSession()) {
            Query<Point> query = session.createQuery("from Point p where p.user.id = :id", Point.class);
            query.setParameter("id", user.getId());
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
