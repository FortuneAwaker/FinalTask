package by.popovich.last.entity;

import java.util.Objects;

public class Group extends Entity {
    private Integer coachID;
    private String typeOfExercises;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Group group = (Group) o;
        return Objects.equals(coachID, group.coachID) &&
                Objects.equals(typeOfExercises, group.typeOfExercises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), coachID, typeOfExercises);
    }

    public Integer getCoachID() {
        return coachID;
    }

    public void setCoachID(Integer coachID) {
        this.coachID = coachID;
    }

    public String getTypeOfExercises() {
        return typeOfExercises;
    }

    public void setTypeOfExercises(String typeOfExercises) {
        this.typeOfExercises = typeOfExercises;
    }

    @Override
    public String toString() {
        return "Group{" +
                "coachID=" + coachID +
                ", typeOfExercises='" + typeOfExercises + '\'' +
                '}';
    }
}
