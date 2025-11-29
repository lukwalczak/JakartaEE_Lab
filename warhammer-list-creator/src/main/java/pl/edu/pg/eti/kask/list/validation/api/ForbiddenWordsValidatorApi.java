package pl.edu.pg.eti.kask.list.validation.api;

import jakarta.validation.Payload;

public @interface ForbiddenWordsValidatorApi {

    String message() default "Forbidden words detected";

    Class<?>[] groups() default {};

    Class<?  extends Payload>[] payload() default {};

    String[] forbiddenWords() default {"Dupa", "Kupa", "Chuj", "Pizda"};
}
