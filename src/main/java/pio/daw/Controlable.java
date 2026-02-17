package pio.daw;

import java.util.List;

public interface Controlable {


    /**
     * Register a entry/exit change of a user.
     * @param id ID of the user to update.
     * @param s event type detected.
     */
    public void registerChange(String id, EventType e);

    /**
     * Return the list of all of the current Users
     * @return current users.
     */
    public List<User> getCurrentInside();

    /**
     * Get the user with the biggest amount of entries.
     * @return user that enters more tiemes.
     */
    public  List<User> getMaxEntryUsers();


    /**
     * Get the list with all the users that has enter the place ordered by User ID.
     * @return
     */
    public List<User> getUserList();

    /**
     * Print a resume of the current status:
     * 1. Current users
     * 2. Entries per user
     * 3. User with more entries
     */
    public void printResume();
;}