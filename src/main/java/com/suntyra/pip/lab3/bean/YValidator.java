package com.suntyra.pip.lab3.bean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("yValidator")
public class YValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (o == null) throw new ValidatorException(new FacesMessage("Y is required!"));
        if (o.getClass() != Double.class) {
            throw new ValidatorException(new FacesMessage("Y must be double"));
        }
        Double value = (Double) o;
        if (value > 5 || value < -3) throw new ValidatorException(new FacesMessage("Y is out if bound"));
    }
}
