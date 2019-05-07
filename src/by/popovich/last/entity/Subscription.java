package by.popovich.last.entity;

import java.sql.Date;
import java.util.Objects;

public class Subscription extends Entity {
    private static int count = 0;

    private Integer idOfGroup;
    private int leftVisits;
    private Date lastDay;

    public Subscription() {
        this.setIdentity(count++);
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
        return "Subscription{" +
                "idOfGroup=" + idOfGroup +
                ", leftVisits=" + leftVisits +
                ", lastDay=" + lastDay +
                '}';
    }
}
