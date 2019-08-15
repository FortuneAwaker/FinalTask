package by.popovich.last.entity;

/**
 * Price.
 */
public class Price extends Entity {
    /**
     * Id of exercise.
     */
    private int typeOfExercise;
    /**
     * Number of visits.
     */
    private int numberOfVisits;
    /**
     * Number of days.
     */
    private int numberOfDays;
    /**
     * Name of exercise.
     */
    private String nameOfExercise;
    /**
     * Price.
     */
    private double price;

    /**
     * Getter.
     * @return name of exercise.
     */
    public String getNameOfExercise() {
        return nameOfExercise;
    }

    /**
     * Setter.
     * @param name name of exercise.
     */
    public void setNameOfExercise(final String name) {
        this.nameOfExercise = name;
    }

    /**
     * Getter.
     * @return id of exercise.
     */
    public int getTypeOfExercise() {
        return typeOfExercise;
    }

    /**
     * Setter.
     * @param id id of exercise.
     */
    public void setTypeOfExercise(final int id) {
        this.typeOfExercise = id;
    }

    /**
     * Getter.
     * @return number of visits.
     */
    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    /**
     * Setter.
     * @param number number of visits.
     */
    public void setNumberOfVisits(final int number) {
        this.numberOfVisits = number;
    }

    /**
     * Getter.
     * @return number of days.
     */
    public int getNumberOfDays() {
        return numberOfDays;
    }

    /**
     * Setter.
     * @param number number of days.
     */
    public void setNumberOfDays(final int number) {
        this.numberOfDays = number;
    }

    /**
     * Getter.
     * @return price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter.
     * @param p price.
     */
    public void setPrice(final double p) {
        this.price = p;
    }

    /**
     * To string.
     * @return string.
     */
    @Override
    public String toString() {
        return "id = " + getIdentity()
                + ", typeOfExerciseId = " + typeOfExercise
                + ", numberOfVisits = " + numberOfVisits
                + ", numberOfDays = " + numberOfDays
                + ", price=" + price;
    }
}
