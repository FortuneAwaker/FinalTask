package by.popovich.last.entity;

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
