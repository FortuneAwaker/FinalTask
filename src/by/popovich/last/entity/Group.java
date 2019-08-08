package by.popovich.last.entity;

import java.util.Objects;

public class Group extends Entity {
    private Integer coachID;
    private Integer typeOfExercisesId;
    private int maxClients;
    private int currentClients;
    private String typeOfExercises;

    public String getTypeOfExercises() {
        return typeOfExercises;
    }

    public void setTypeOfExercises(String typeOfExercises) {
        this.typeOfExercises = typeOfExercises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Group group = (Group) o;
        return Objects.equals(coachID, group.coachID) &&
                Objects.equals(typeOfExercisesId, group.typeOfExercisesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), coachID, typeOfExercisesId);
    }

    public Integer getCoachID() {
        return coachID;
    }

    public void setCoachID(Integer coachID) {
        this.coachID = coachID;
    }

    public Integer getTypeOfExercisesId() {
        return typeOfExercisesId;
    }

    public void setTypeOfExercisesId(Integer typeOfExercisesId) {
        this.typeOfExercisesId = typeOfExercisesId;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public int getCurrentClients() {
        return currentClients;
    }

    public void setCurrentClients(int currentClients) {
        this.currentClients = currentClients;
    }

    @Override
    public String toString() {
        return  "groupID = " + getIdentity() +
                ", coachID = " + coachID +
                ", typeOfExercises = " + typeOfExercisesId +
                ", current visitors = " + currentClients +
                ", maximum visitors = " + maxClients;
    }
}
