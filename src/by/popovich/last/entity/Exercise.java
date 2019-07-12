package by.popovich.last.entity;

import java.util.Objects;

/**
 * Class for description of existing exercises.
 *
 * @author Alexander Popovich.
 */
public class Exercise extends Entity {
    /**
     * String value for the name of exercise.
     */
    private String typeOfExercises;

    /**
     * Overrode method equals.
     *
     * @param o comparing object.
     * @return true if equals and false otherwise.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        return Objects.equals(typeOfExercises, exercise.typeOfExercises);
    }

    /**
     * Overrode method hashCode.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), typeOfExercises);
    }

    /**
     * Getter for type of exercises.
     *
     * @return string with the name.
     */
    public String getTypeOfExercises() {
        return typeOfExercises;
    }

    /**
     * Setter for type of exercises.
     *
     * @param newName new name of exercise.
     */
    public void setTypeOfExercises(final String newName) {
        this.typeOfExercises = newName;
    }

    /**
     * Overrode method toString.
     *
     * @return string with info about an instance.
     */
    @Override
    public String toString() {
        return "id = " + getIdentity()
                + ", typeOfExercises = "
                + typeOfExercises
                + ';';
    }
}
