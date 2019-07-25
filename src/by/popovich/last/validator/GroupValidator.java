package by.popovich.last.validator;

import by.popovich.last.entity.Group;
import by.popovich.last.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class GroupValidator implements Validator<Group> {
    @Override
    public Group validate(HttpServletRequest request) throws IncorrectFormDataException {
        return null;
    }
}
