package by.popovich.last.entity;

import java.io.Serializable;
import java.util.Objects;

abstract public class Entity implements Serializable {
    private Integer identity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(identity, entity.identity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity);
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "identity=" + identity +
                '}';
    }
}
