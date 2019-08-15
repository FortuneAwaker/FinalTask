package by.popovich.last.entity;

import java.sql.Date;

public class Subscription extends Entity {

    private int clientId;
    private Integer idOfGroup;
    private int leftVisits;
    private Date lastDay;
    private double payment;
    private String typeOfExercise;
    private int coachId;
    private Person userInfo;

    public Person getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Person userInfo) {
        this.userInfo = userInfo;
    }

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
