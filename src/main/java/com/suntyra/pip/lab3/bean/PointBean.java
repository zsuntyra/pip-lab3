package com.suntyra.pip.lab3.bean;

import com.suntyra.pip.lab3.ErrorMessage;
import com.suntyra.pip.lab3.model.Point;
import com.suntyra.pip.lab3.model.User;
import com.suntyra.pip.lab3.repository.PointRepository;
import com.suntyra.pip.lab3.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "pointBean", eager = true)
@SessionScoped
public class PointBean implements Serializable {
    @Getter
    private double x = 0;
    @Getter
    private double y = 0;
    @Getter
    private double r = 4;
    @Getter
    private List<Point> graphPoints = new ArrayList<>();

    @ManagedProperty("#{userBean}")
    @Setter
    @Getter
    private UserBean userBean = null;

    @ManagedProperty("#{messageBean}")
    @Setter
    @Getter
    private MessageBean messageBean = null;

    @ManagedProperty("#{usersRepository}")
    @Setter
    @Getter
    private UserRepository userRepository;

    @ManagedProperty("#{pointsRepository}")
    @Setter
    @Getter
    private PointRepository pointRepository;

    public void setX(double x) {
        DecimalFormat df = new DecimalFormat("#.####");
        this.x = Double.parseDouble(df.format(x));
    }

    public void setY(double y) {
        DecimalFormat df = new DecimalFormat("#.####");
        this.y = Double.parseDouble(df.format(y));
    }

    public void setR(double r) {
        this.r = r;
        List<Point> newPoints = graphPoints.stream()
                .peek((element) -> {
                    element.setR(r);
                    element.setIsInArea(isInArea(element.getX(), element.getY(), r));
                })
                .collect(Collectors.toList());
        graphPoints.clear();
        graphPoints.addAll(newPoints);
    }

    public void replaceGraphPoints(List<Point> points) {
        // FIXME idk maybe call setR() here
        graphPoints.clear();
        graphPoints.addAll(points);
    }

    public void clearPoints() {
        graphPoints.clear();

        User user = getUserFromContext();
        user.getPoints().clear();

        user = userRepository.findByUsername(user.getUsername());
        user.getPoints().clear();
        try {
            userRepository.saveOrUpdate(user);
            List<Point> points = pointRepository.findByUser(user);
            points.forEach(point -> pointRepository.delete(point));
        } catch (RuntimeException e) {
            messageBean.setErrorMessage(ErrorMessage.SERVER_UNAVAILABLE);
        }
    }

    public boolean isInArea() {
        return isInArea(this.x, this.y, this.r);
    }

    private boolean isInArea(Double x, Double y, Double r) { // FIXME
        if ((x <= 0) && (y >= 0) && (sqr(r) >= sqr(x) + sqr(y))) {
            return true;
        }
        if (x >= 0 && y >= 0 && x <= r && y <= r / 2) {
            return true;
        }
        return ((x >= 0) && (y <= 0) && (y >= (x - r)));
    }

    public void addPoint() {
        User owner = getUserFromContext();
        Point point = new Point(x, y, r, isInArea(), owner);
        owner.getPoints().add(point);
        graphPoints.add(point);
        // FIXME debug mode on
        System.out.println("X = " + x);
        System.out.println("Y = " + y);
        System.out.println("R = " + r);
        System.out.println("IsInArea = " + isInArea());
        System.out.println("Username = " + owner.getUsername());

        try {
            userRepository.saveOrUpdate(owner);
        } catch (RuntimeException e) {
            messageBean.setErrorMessage(ErrorMessage.SERVER_UNAVAILABLE);
        }
    }

    private double sqr(double value) {
        return value * value;
    }

    public User getUserFromContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        return userBean.getUsersMap().get(session.getId());
    }
}
