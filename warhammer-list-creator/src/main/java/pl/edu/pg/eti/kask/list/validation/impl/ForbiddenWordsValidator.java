package pl.edu.pg.eti.kask.list.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.edu.pg.eti.kask.list.validation.api.ForbiddenWordsValidatorApi;

import java.util.Arrays;
import java.util.List;

public class ForbiddenWordsValidator implements ConstraintValidator<ForbiddenWordsValidatorApi, String> {
    private static final List<String> DEFAULT_FORBIDDEN_WORDS = Arrays.asList(
            "spam", "test123", "xxx", "admin", "password", "null"
    );

    private List<String> forbiddenWords;

    @Override
    public void initialize(ForbiddenWordsValidatorApi constraintAnnotation) {
        String[] customWords = constraintAnnotation.forbiddenWords();
        if (customWords.length > 0) {
            forbiddenWords = Arrays.asList(customWords);
        } else {
            forbiddenWords = DEFAULT_FORBIDDEN_WORDS;
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        String lowerValue = value.toLowerCase();

        for (String forbiddenWord : forbiddenWords) {
            if (lowerValue.contains(forbiddenWord.toLowerCase())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "Field consists of forbidden word: '" + forbiddenWord + "'"
                ).addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
