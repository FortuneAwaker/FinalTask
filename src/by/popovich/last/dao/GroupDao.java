package by.popovich.last.dao;

import by.popovich.last.entity.Group;
import by.popovich.last.exception.PersistentException;

import java.util.List;

/**
 * Group DAO interface.
 */
public interface GroupDao extends DAO<Group> {
    /**
     * Updates current visitors.
     *
     * @param numberOfCurrent new number.
     * @param groupId         id of group.
     * @throws PersistentException if error in DB handling.
     */
    void updateCurrent(int numberOfCurrent,
                       int groupId) throws PersistentException;

    /**
     * Adds visitor to group.
     *
     * @param groupId id of group.
     * @throws PersistentException if error in DB handling.
     */
    void addVisitor(int groupId) throws PersistentException;

    /**
     * Removes visitor from group.
     *
     * @param groupId id of group.
     * @throws PersistentException if error in DB handling.
     */
    void removeVisitor(int groupId) throws PersistentException;

    /**
     * Reads list of groups by coach id.
     *
     * @param id coach id.
     * @return list of groups.
     * @throws PersistentException if error in DB handling.
     */
    List<Group> readByCoachId(Integer id) throws PersistentException;

    /**
     * Read list of groups by exercise id.
     *
     * @param exercisesTypeId id of exercise.
     * @return list of groups.
     * @throws PersistentException if error in DB handling.
     */
    List<Group> readGroupsByType(int exercisesTypeId)
      throws PersistentException;

    /**
     * Reads all table.
     *
     * @return list of groups.
     * @throws PersistentException if error in DB handling.
     */
    List<Group> read() throws PersistentException;
}
