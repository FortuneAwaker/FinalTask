package by.popovich.last.validator;

import by.popovich.last.entity.Subscription;
import by.popovich.last.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class SubscriptionValidator implements Validator<Subscription> {
    @Override
    public Subscription validate(HttpServletRequest request) throws IncorrectFormDataException {
        return null;
    }
}
