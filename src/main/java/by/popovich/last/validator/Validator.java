package by.popovich.last.validator;

import by.popovich.last.controller.DispatcherServlet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;

public class Validator {

    /**
     * Logger.
     */
    private static Logger LOGGER = LogManager.getLogger(Validator.class);

    public boolean validateLoginOrPassword(final String data,
                                           final int minLength,
                                           final int maxLength) {
        if (data.length() < minLength || data.length() > maxLength) {
            return false;
        }
        LOGGER.info("Валидация логина или пароля прошла успешно!");
        return Pattern.matches("[A-za-z0-9]+", data);
    }

    public boolean validateStringData(final String data,
                                      final int minLength,
                                      final int maxLength) {
        if (data.length() < minLength || data.length() > maxLength) {
            return false;
        }
        LOGGER.info("Валидация строчных данных прошла успешно!");
        return Pattern.matches("[A-Za-z\\s]+", data);
    }

    public boolean validateNumber(final String data,
                                  final int minLength,
                                  final int maxLength) {
        if (data.length() < minLength || data.length() > maxLength) {
            return false;
        }
        LOGGER.info("Валидация числовых данных прошла успешно!");
        return Pattern.matches("[0-9]+", data);
    }
}
