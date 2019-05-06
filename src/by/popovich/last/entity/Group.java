package by.popovich.last.entity;

import java.util.Objects;

public class Group extends Entity {
    private Integer coachID;
    private Integer typeOfExercisesId;

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

    @Override
    public String toString() {
        return "Group{" +
                "coachID=" + coachID +
                ", typeOfExercises='" + typeOfExercisesId + '\'' +
                '}';
    }
}
