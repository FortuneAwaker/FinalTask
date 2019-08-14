package by.popovich.last.validator;

import java.util.regex.Pattern;

public class Validator {
    public boolean validateLoginOrPassword(final String data,
                                           final int minLength,
                                           final int maxLength) {
        if (data.length() < minLength || data.length() > maxLength) {
            return false;
        }
        return Pattern.matches("[A-za-z0-9]+", data);
    }

    public boolean validateStringData(final String data,
                                      final int minLength,
                                      final int maxLength) {
        if (data.length() < minLength || data.length() > maxLength) {
            return false;
        }
        return Pattern.matches("[A-Za-z]+", data);
    }

    public boolean validateNumber(final String data,
                                  final int minLength,
                                  final int maxLength) {
        if (data.length() < minLength || data.length() > maxLength) {
            return false;
        }
        return Pattern.matches("[0-9]+", data);
    }
}
