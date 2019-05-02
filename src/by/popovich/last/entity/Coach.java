package by.popovich.last.entity;

public class Coach extends Person {
    private int maxClients;
    private int currentClients;
    private double salary;

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(final int maxClients) {
        this.maxClients = maxClients;
    }

    public int getCurrentClients() {
        return currentClients;
    }

    public void setCurrentClients(final int currentClients) {
        this.currentClients = currentClients;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(final double salary) {
        this.salary = salary;
    }
}
