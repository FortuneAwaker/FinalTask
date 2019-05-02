package by.popovich.last.entity;

import java.util.Objects;

public class Queue extends Entity {
    private String wantedType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Queue queue = (Queue) o;
        return Objects.equals(wantedType, queue.wantedType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wantedType);
    }

    public String getWantedType() {
        return wantedType;
    }

    public void setWantedType(String wantedType) {
        this.wantedType = wantedType;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "wantedType='" + wantedType + '\'' +
                '}';
    }
}
