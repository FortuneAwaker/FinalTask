package by.popovich.last.validator;

import by.popovich.last.entity.Price;
import by.popovich.last.exception.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class PriceValidator implements Validator<Price> {
    @Override
    public Price validate(HttpServletRequest request) throws IncorrectFormDataException {
        return null;
    }
}
