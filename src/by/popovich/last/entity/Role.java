package by.popovich.last.entity;

public enum Role {
    ADMINISTRATOR("администратор"),
    COACH("тренер"),
    CLIENT("клиент");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getIdentity() {
        return ordinal();
    }

    public static Role getByIdentity(Integer identity) {
        return Role.values()[identity];
    }
}
