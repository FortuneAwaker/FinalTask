package by.popovich.last.entity;

/**
 * User info.
 */
public class Person extends Entity {
    /**
     * Surname.
     */
    private String surname;
    /**
     * Name.
     */
    private String name;
    /**
     * Patro.
     */
    private String patronymic;
    /**
     * Phone.
     */
    private long phone;
    /**
     * Avatar.
     */
    private String avatar;
    /**
     * Login.
     */
    private String login;
    /**
     * Role.
     */
    private Role role;

    /**
     * Getter.
     *
     * @return role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Setter.
     *
     * @param r role.
     */
    public void setRole(final Role r) {
        this.role = r;
    }

    /**
     * Getter.
     *
     * @return login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter.
     *
     * @param l login.
     */
    public void setLogin(final String l) {
        this.login = l;
    }

    /**
     * Getter.
     *
     * @return phone.
     */
    public long getPhone() {
        return phone;
    }

    /**
     * Setter.
     *
     * @param p phone.
     */
    public void setPhone(final long p) {
        this.phone = p;
    }

    /**
     * Getter.
     *
     * @return avatar.
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Setter.
     *
     * @param ava avatar.
     */
    public void setAvatar(final String ava) {
        this.avatar = ava;
    }

    /**
     * Getter.
     *
     * @return surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Setter.
     *
     * @param sur surname.
     */
    public void setSurname(final String sur) {
        this.surname = sur;
    }

    /**
     * Getter.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     *
     * @param n name.
     */
    public void setName(final String n) {
        this.name = n;
    }

    /**
     * Getter.
     *
     * @return patronymic.
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Setter.
     *
     * @param patro patronymic.
     */
    public void setPatronymic(final String patro) {
        this.patronymic = patro;
    }

    /**
     * To string.
     *
     * @return string.
     */
    @Override
    public String toString() {
        return "Person{"
          + "surname='" + surname + '\''
          + ", name='" + name + '\''
          + ", patronymic='" + patronymic + '\''
          + ", phone=" + phone
          + ", avatar=" + avatar
          + '}';
    }
}
