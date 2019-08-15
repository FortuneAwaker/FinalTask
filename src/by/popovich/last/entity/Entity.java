package by.popovich.last.entity;

import java.io.Serializable;

/**
 * Abstract entity.
 */
abstract public class Entity implements Serializable {
    /**
     * Id.
     */
    private Integer identity;

    /**
     * Getter.
     *
     * @return id.
     */
    public Integer getIdentity() {
        return identity;
    }

    /**
     * Setter.
     *
     * @param id id.
     */
    public void setIdentity(final Integer id) {
        this.identity = id;
    }

    /**
     * To string.
     * @return string.
     */
    @Override
    public String toString() {
        return "Entity{"
          + "identity=" + identity
          + '}';
    }
}
