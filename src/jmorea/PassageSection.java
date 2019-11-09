package jmorea;

import dnd.die.D20;
import dnd.models.Monster;
import dnd.die.Percentile;

/* Represents a 10 ft section of passageway */

/**
*  PassageSection class represents an individual unit of which the Passage is composed.
*/
public class PassageSection {

    /**
     * Represents the monster in this section, if there is one.
     */
    private Monster myMonster;

    /**
     * Represents the door in this section, if there is one.
     */
    private Door myDoor;

    /**
     * String description of this passage.
     */
    private StringBuilder myDescription;

    /**
     * Creates a passage section with default characteristics.
     */
    public PassageSection() {
        //sets up the 10 foot section with default settings
        myMonster = null;
        myDoor = null;
        chooseDescription();
    }

    /**
     * Creates a passage section with a specified description.
     * @param description the description that is set
     */
    @Deprecated
    private PassageSection(String description) { /* DO NOT USE */
        //sets up a specific passage based on the values sent in from
        //modified table 1
        if (description.contains("Monster")) {
            this.myMonster = new Monster();
        }
        if (description.contains("Door")) {
            this.myDoor = new Door();
        }
        this.myDescription = new StringBuilder();
        this.myDescription.append(description);
    }

    /**
     * Chooses a description for this section based on the roll table supplied.
     */
    private void chooseDescription() { //Put changes to Door Monster in these descriptions.
        this.myDescription = new StringBuilder();
        D20 myD20 = new D20();
        int theRoll = myD20.roll();

        if (theRoll < 3) {
            this.myDescription.append("passage goes straight for 10 ft");
        } else if (theRoll < 6) {
            this.myDescription.append("passage ends in Door to a Chamber");
            this.myDoor = new Door();
        } else if (theRoll < 8) {
            this.myDescription.append("archway (door) to right (main passage continues straight for 10 ft)");
            this.myDoor = new Door();
            this.myDoor.setArchway(true);
        } else if (theRoll < 10) {
            this.myDescription.append("archway (door) to left (main passage continues straight for 10 ft)");
            this.myDoor = new Door();
            this.myDoor.setArchway(true);
        } else if (theRoll < 12) {
            this.myDescription.append("passage turns to left and continues for 10 ft");
        } else if (theRoll < 14) {
            this.myDescription.append("passage turns to right and continues for 10 ft");
        } else if (theRoll < 17) {
            this.myDescription.append("passage ends in archway (door) to chamber");
            this.myDoor = new Door();
            this.myDoor.setArchway(true);
        } else if (theRoll < 18) {
            this.myDescription.append("Stairs, (passage continues straight for 10 ft)");
        } else if (theRoll < 20) {
            this.myDescription.append("Dead End");
        } else if (theRoll < 21) {
            this.myDescription.append("20 Wandering Monster (passage continues straight for 10 ft)");
            this.myMonster = new Monster();
            myMonster.setType(new Percentile().roll());
        }
    }

    /**
     * Returns the door that is in the passage section, if there is one.
     * @return the door of this passage section
     */
    public Door getDoor() {
        return this.myDoor;
    }

    /**
     * Returns the monster that is in the passage section, if there is one.
     * @return the monster in this passage section
     */
    public Monster getMonster() {
        return this.myMonster;
    }

    /**
     * Sets the monster in this section to the supplied monster.
     * @param theMonster the monster to be added
     */
    public void setMonster(Monster theMonster) {
        this.myMonster = theMonster;
    }

    /**
     * Returns a string describing this section.
     * @return the string description
     */
    public String getDescription() {
        return myDescription.toString();
    }
}
