package by.popovich.last.entity;

import java.util.Objects;

public class Exercise extends Entity {
    private String typeOfExercises;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(typeOfExercises, exercise.typeOfExercises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), typeOfExercises);
    }

    public String getTypeOfExercises() {
        return typeOfExercises;
    }

    public void setTypeOfExercises(String typeOfExercises) {
        this.typeOfExercises = typeOfExercises;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "typeOfExercises='" + typeOfExercises + '\'' +
                '}';
    }
}
