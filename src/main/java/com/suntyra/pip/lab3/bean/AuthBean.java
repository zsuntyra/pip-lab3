package com.suntyra.pip.lab3.bean;

import com.suntyra.pip.lab3.ErrorMessage;
import com.suntyra.pip.lab3.model.User;
import com.suntyra.pip.lab3.repository.UserRepository;
import org.hibernate.HibernateException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

public class AuthBean implements Serializable {
    private String username;
    private String password;
    private UserRepository userRepository;

    @ManagedProperty("#{userBean}")
    private UserBean userBean;

    @ManagedProperty("#{messageBean}")
    private MessageBean messageBean;

    @ManagedProperty("#{pointBean}")
    private PointBean pointBean;

    public MessageBean getMessageBean() {
        return messageBean;
    }

    public void setMessageBean(MessageBean messageBean) {
        this.messageBean = messageBean;
    }

    public PointBean getPointBean() {
        return pointBean;
    }

    public void setPointBean(PointBean pointBean) {
        this.pointBean = pointBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    @PostConstruct
    public void postConstruct() {
        try {
            userRepository = new UserRepository();
        } catch (HibernateException e) {
            e.printStackTrace();
            messageBean.setErrorMessage(ErrorMessage.SERVER_UNAVAILABLE);
        }
    }

    private void authorizeUser(User user) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

        userBean.getUsersMap().entrySet().removeIf(element -> element.getValue().equals(user));

        userBean.getUsersMap().put(session.getId(), user);

        try {
            pointBean.replaceGraphPoints(user.getPoints());
        } catch (NullPointerException e) {
            messageBean.setErrorMessage(ErrorMessage.SERVER_UNAVAILABLE);
        }
    }

    public String signIn() {
        try {
            User receivedUser = userRepository.findByUsername(username);
            if (receivedUser == null) {
                messageBean.setErrorMessage(ErrorMessage.WRONG_CREDENTIALS);
                return null;
            }
            if (receivedUser.getPassword().equals(password)) {
                authorizeUser(receivedUser);
                return "graph?faces-redirect=true";
            } else {
                messageBean.setErrorMessage(ErrorMessage.WRONG_CREDENTIALS);
            }
        } catch (NullPointerException e) {
            messageBean.setErrorMessage(ErrorMessage.SERVER_UNAVAILABLE);
        }
        return null;
    }

    public boolean signUp() {
        try {
            User receivedUser = userRepository.findByUsername(username);
            if (receivedUser != null) {
                messageBean.setErrorMessage(ErrorMessage.LOGIN_EXISTS);
                return false;
            }
            User newUser = new User(username, password);
            userRepository.save(newUser);
        } catch (NullPointerException e) {
            messageBean.setErrorMessage(ErrorMessage.SERVER_UNAVAILABLE);
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
