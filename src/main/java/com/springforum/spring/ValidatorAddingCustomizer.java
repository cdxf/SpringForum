package com.springforum.spring;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.Map;

@Component
public class ValidatorAddingCustomizer implements HibernatePropertiesCustomizer {

    private final ObjectProvider<Validator> provider;

    public ValidatorAddingCustomizer(ObjectProvider<javax.validation.Validator> provider) {
        this.provider = provider;
    }

    public void customize(Map<String, Object> hibernateProperties) {
        Validator validator = provider.getIfUnique();
        if (validator != null) {
            hibernateProperties.put("javax.persistence.validation.validator", validator);
        }
    }
}