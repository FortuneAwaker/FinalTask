package by.popovich.last.validator;

import by.popovich.last.entity.Entity;
import by.popovich.last.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public interface Validator<Type extends Entity> {
    Type validate(HttpServletRequest request) throws IncorrectFormDataException;
}
