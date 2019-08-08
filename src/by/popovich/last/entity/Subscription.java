package by.popovich.last.entity;

import java.sql.Date;
import java.util.Objects;

public class Subscription extends Entity {

    private int clientId;
    private Integer idOfGroup;
    private int leftVisits;
    private Date lastDay;
    private double payment;
    private String typeOfExercise;
    private int coachId;

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public String getTypeOfExercise() {
        return typeOfExercise;
    }

    public void setTypeOfExercise(String typeOfExercise) {
        this.typeOfExercise = typeOfExercise;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subscription that = (Subscription) o;
        return leftVisits == that.leftVisits &&
                Objects.equals(idOfGroup, that.idOfGroup) &&
                Objects.equals(lastDay, that.lastDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idOfGroup, leftVisits, lastDay);
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Integer getIdOfGroup() {
        return idOfGroup;
    }

    public void setIdOfGroup(Integer idOfGroup) {
        this.idOfGroup = idOfGroup;
    }

    public int getLeftVisits() {
        return leftVisits;
    }

    public void setLeftVisits(int leftVisits) {
        this.leftVisits = leftVisits;
    }

    public Date getLastDay() {
        return lastDay;
    }

    public void setLastDay(Date lastDay) {
        this.lastDay = lastDay;
    }

    @Override
    public String toString() {
        return "id = " + getIdentity()
                + ", clientId = " + clientId
                + ", idOfGroup = " + idOfGroup
                + ", leftVisits = " + leftVisits
                + ", lastDay = " + lastDay;
    }
}
