package com.suntyra.pip.lab3.bean;

import com.suntyra.pip.lab3.ErrorMessage;
import com.suntyra.pip.lab3.model.User;
import com.suntyra.pip.lab3.repository.UserRepository;
import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@ManagedBean(name = "authBean", eager = true)
@SessionScoped
@Data
public class AuthBean implements Serializable {
    private String username;
    private String password;

    @ManagedProperty("#{usersRepository}")
    private UserRepository usersRepository;

    @ManagedProperty("#{userBean}")
    private UserBean userBean;

    @ManagedProperty("#{messageBean}")
    private MessageBean messageBean;

    @ManagedProperty("#{pointBean}")
    private PointBean pointBean;

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
            User receivedUser = usersRepository.findByUsername(username);
            if (receivedUser == null) {
                messageBean.setErrorMessage(ErrorMessage.WRONG_CREDENTIALS);
                return null;
            }
            if (receivedUser.getPassword().equals(password)) {
                authorizeUser(receivedUser);
                return "main?faces-redirect=true";
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
            User receivedUser = usersRepository.findByUsername(username);
            if (receivedUser != null) {
                messageBean.setErrorMessage(ErrorMessage.LOGIN_EXISTS);
                return false;
            }
            User newUser = new User(username, password);
            usersRepository.save(newUser);
        } catch (RuntimeException e) {
            messageBean.setErrorMessage(ErrorMessage.SERVER_UNAVAILABLE);
        }
        return false;
    }

}
