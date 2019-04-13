package com.suntyra.pip.lab3.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name = "messageBean", eager = true)
@SessionScoped
public class MessageBean implements Serializable {
    private String errorMessage = "";

    public String getErrorMessage() {
        return errorMessage;
    }

    void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
