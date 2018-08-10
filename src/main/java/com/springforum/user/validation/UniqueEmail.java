package com.springforum.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidatior.class)
public @interface UniqueEmail {
    String message() default "Email ${validatedValue} has been existed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
