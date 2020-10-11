package com.aperez.exercise.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {
    String message() default "Contraseña debe tener al menos una letra Mayúscula, minusculas y al menos 2 numeros";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
