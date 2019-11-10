package jmorea;

import java.util.ArrayList;

/**
 * Space class represents either a Chamber or a Passage.
 */
public abstract class Space {

    /**
     * Returns a string describing the space.
     *@return the string description of that space
     */
    public abstract String getDescription();

    /**
     * Sets the door for a space.
     * @param theDoor the door to be added
     */
    public abstract void setDoor(Door theDoor);

    /**
     * returns the doors from a given space.
     * @return the doors
     */
    public abstract ArrayList<Door> getDoors();
}
