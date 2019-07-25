package by.popovich.last.validator;

import by.popovich.last.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {
    /**
     * Logger for creation notes to some appender.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ValidatorFactory.class);

    /**
     * Map which helps to create required validation class by entity class.
     */
    private static Map<Class<? extends Entity>, Class<? extends Validator>>
            validators = new HashMap<>();

    static {
        validators.put(Exercise.class, ExerciseValidator.class);
        validators.put(User.class, UserValidator.class);
        validators.put(Group.class, GroupValidator.class);
        validators.put(Person.class, PersonValidator.class);
        validators.put(Price.class, PriceValidator.class);
        validators.put(Subscription.class, SubscriptionValidator.class);
    }

    @SuppressWarnings("unchecked")
    public static <Type extends Entity> Validator<Type> createValidator(Class<Type> entity) {
        try {
            return (Validator<Type>)validators.get(entity).newInstance();
        } catch(InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
