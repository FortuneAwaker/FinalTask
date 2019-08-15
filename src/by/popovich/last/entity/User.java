package by.popovich.last.entity;

public class User extends Entity {
    private String login;
    private String password;
    private Role role;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Getter.
     * @return role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Setter
     * @param r role.
     */
    public void setRole(final Role r) {
        this.role = r;
    }

    /**
     * Getter.
     * @return login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter.
     * @param l login.
     */
    public void setLogin(final String l) {
        this.login = l;
    }

    @Override
    public String toString() {
        return "id = " + getIdentity()
                + ", login = " + login
                + ", password = " + password
                + ", role = " + role + '.';
    }
}
