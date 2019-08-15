package by.popovich.last.entity;

/**
 * Roles.
 */
public enum Role {
    /**
     * Admin.
     */
    ADMINISTRATOR("администратор"),
    /**
     * Coach.
     */
    COACH("тренер"),
    /**
     * Client.
     */
    CLIENT("клиент");
    /**
     * Name.
     */
    private String roleName;

    /**
     * Constructor.
     * @param name name
     */
    Role(final String name) {
        this.roleName = name;
    }

    /**
     * Getter.
     * @return name.
     */
    public String getName() {
        return roleName;
    }

    /**
     * Getter.
     * @return id.
     */
    public Integer getIdentity() {
        return ordinal();
    }

    /**
     * Getter.
     * @param identity id.
     * @return Role.
     */
    public static Role getByIdentity(final Integer identity) {
        return Role.values()[identity];
    }
}
