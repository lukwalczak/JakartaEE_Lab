package pl.edu.pg.eti.kask.list.validation.api;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.edu.pg.eti.kask.list.validation.impl.ForbiddenWordsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType. FIELD, ElementType. METHOD, ElementType. PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ForbiddenWordsValidator.class)
public @interface ForbiddenWordsValidatorApi {

    String message() default "Forbidden words detected";

    Class<?>[] groups() default {};

    Class<?  extends Payload>[] payload() default {};

    String[] forbiddenWords() default {};
}
