package by.popovich.last.validator;

import by.popovich.last.entity.Exercise;
import by.popovich.last.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class ExerciseValidator implements Validator<Exercise> {
    @Override
    public Exercise validate(HttpServletRequest request) throws IncorrectFormDataException {
        return null;
    }
}
