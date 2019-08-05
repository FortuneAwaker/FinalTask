package by.popovich.last.entity;

public class Price extends Entity {
    private int typeOfExercise;
    private int numberOfVisits;
    private int numberOfDays;
    private String nameOfExercise;
    private double price;

    public String getNameOfExercise() {
        return nameOfExercise;
    }

    public void setNameOfExercise(String nameOfExercise) {
        this.nameOfExercise = nameOfExercise;
    }

    public int getTypeOfExercise() {
        return typeOfExercise;
    }

    public void setTypeOfExercise(int typeOfExercise) {
        this.typeOfExercise = typeOfExercise;
    }

    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "id = " + getIdentity()
                + ", typeOfExerciseId = " + typeOfExercise
                + ", numberOfVisits = " + numberOfVisits
                + ", numberOfDays = " + numberOfDays
                + ", price=" + price;
    }
}
