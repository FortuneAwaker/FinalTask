package by.popovich.last.entity;

/**
 * Group.
 */
public class Group extends Entity {
    /**
     * Coach id.
     */
    private Integer coachID;
    /**
     * Type of exercises id.
     */
    private Integer typeOfExercisesId;
    /**
     * Max clients.
     */
    private int maxClients;
    /**
     * Current clients.
     */
    private int currentClients;
    /**
     * Name of exercises.
     */
    private String typeOfExercises;

    /**
     * Getter.
     * @return name of exercises.
     */
    public String getTypeOfExercises() {
        return typeOfExercises;
    }

    /**
     * Setter.
     * @param type name.
     */
    public void setTypeOfExercises(final String type) {
        this.typeOfExercises = type;
    }

    /**
     * Getter.
     * @return coach id.
     */
    public Integer getCoachID() {
        return coachID;
    }

    /**
     * Setter.
     * @param coachId coach id.
     */
    public void setCoachID(final Integer coachId) {
        this.coachID = coachId;
    }

    /**
     * Getter.
     * @return id of exercise.
     */
    public Integer getTypeOfExercisesId() {
        return typeOfExercisesId;
    }

    /**
     * Setter.
     * @param exercisesId exercise id.
     */
    public void setTypeOfExercisesId(final Integer exercisesId) {
        this.typeOfExercisesId = exercisesId;
    }

    /**
     * Getter.
     * @return max clients.
     */
    public int getMaxClients() {
        return maxClients;
    }

    /**
     * Setter.
     * @param max max clients.
     */
    public void setMaxClients(final int max) {
        this.maxClients = max;
    }

    /**
     * Getter.
     * @return current clients.
     */
    public int getCurrentClients() {
        return currentClients;
    }

    /**
     * Setter.
     * @param current current clients.
     */
    public void setCurrentClients(final int current) {
        this.currentClients = current;
    }

    /**
     * To string.
     * @return string.
     */
    @Override
    public String toString() {
        return  "groupID = " + getIdentity()
                + ", coachID = " + coachID
                + ", typeOfExercises = " + typeOfExercisesId
                + ", current visitors = " + currentClients
                + ", maximum visitors = " + maxClients;
    }
}
