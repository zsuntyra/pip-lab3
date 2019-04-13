package com.suntyra.pip.lab3.bean;

import com.suntyra.pip.lab3.model.User;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ManagedBean(name = "userBean", eager = true)
@ApplicationScoped
public class UserBean implements Serializable {
    private Map<String, User> usersMap = new HashMap<>();

    public Map<String, User> getUsersMap() {
        return usersMap;
    }
}
