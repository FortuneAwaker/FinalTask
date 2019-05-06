package by.popovich.last.entity;

public class Price extends Entity {
    private int typeOfExercise;
    private int numberOfVisits;
    private int numberOfDays;
    private double price;

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
}
