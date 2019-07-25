package by.popovich.last.validator;

import by.popovich.last.entity.Person;
import by.popovich.last.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class PersonValidator implements Validator<Person> {
    @Override
    public Person validate(HttpServletRequest request) throws IncorrectFormDataException {
        return null;
    }
}
