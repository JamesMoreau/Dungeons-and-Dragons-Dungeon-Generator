package jmorea;

import dnd.models.Monster;

import java.util.ArrayList;

/**
 * Passage class represents an individual passage in the dungeon.
 */
public class Passage extends Space {
    //these instance variables are suggestions only
    //you can change them if you wish.

    /**
     * Represents an array of passage sections which compose the passage.
     */
    private ArrayList<PassageSection> thePassage;

    /**
     * Represents an array of doors contained within the passage.
     */
    private ArrayList<Door> myDoors;

    /**
     * Flag indicates if the passage is done generating.
     */
    private boolean doneFlag;

    /**
     * String representing the description of the passage.
     */
    private StringBuilder myDescription;

    /**
     * Creates a passage with default characteristics.
     */
    public Passage() {
        this.thePassage = new ArrayList<>();
        this.myDescription = new StringBuilder();
        this.myDoors = new ArrayList<>();
        this.doneFlag = false;
        PassageSection temp;

        while (!this.isDone()) {
            temp = new PassageSection();
            this.addPassageSection(temp);
            if (thePassage.get(this.getSectionCount() - 1).getDescription().contains("Dead End")) {
                this.setDone(true);
            }
            if (thePassage.get(this.getSectionCount() - 1).getDescription().contains("passage ends")) {
                this.setDone(true);
            }
        }
    }

    /**
     * Returns the number of passage sections in this passage.
     * @return the passage size
     */
    int getSectionCount() {
        return this.thePassage.size();
    }

    /**
     * Returns the array of doors associated with the passage.
     * @return the door arraylist
     */
    @Override
    public ArrayList<Door> getDoors() {
    //gets all of the doors in the entire passage
        return this.myDoors;
    }

    /**
     * Returns a door from a given section. If no door exits, returns null.
     * @param i the index of the passage section
     * @return the passage's door
     */
    Door getDoor(int i) {
        //returns the door in section 'i'. If there is no door, returns null
        if (i >= this.getDoors().size()) {
            System.out.println("Out of range");
            return null;
        }
        return this.getDoors().get(i);
    }

    /**
    * Adds a monster at a given passage section.
    *@param theMonster the monster to be added
    * @param i the index of the passage section
     */
    void addMonster(Monster theMonster, int i) {
        // adds a monster to section 'i' of the passage
        thePassage.get(i).setMonster(theMonster);
    }

    /**
     * Returns the monster froma given passage section. If no monster exists, returns null.
     * @param i the index of the passage section
     * @return the desired monster
     */
    Monster getMonster(int i) {
        //returns Monster door in section 'i'. If there is no Monster, returns null
        return thePassage.get(i).getMonster();
    }

    /**
     * Adds a passage section the the end of the array list of passage sections.
     * If there is a door in this section, associates it with the passage.
     * @param toAdd the passage section to be added
     */
    void addPassageSection(PassageSection toAdd) {
        //adds the passage section to the passageway
        if (toAdd.getDoor() != null) {
            toAdd.getDoor().setOneSpace(this);
        } else {
            this.myDoors.add(null);
        }
        this.thePassage.add(toAdd);
    }

    /**
     * Sets the done flag specified value (usually set to true).
     * @param flag the flag value to be set
     */
    void setDone(boolean flag) {
        this.doneFlag = flag;
    }

    /**
     * Returns whether or not the passage is done generating.
     * @return the doneFlag
     */
    boolean isDone() {
        return this.doneFlag;
    }

    /**
     * Adds door to this passage.
     * @param newDoor the door to be added
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to the current Passage Section
        this.myDoors.add(newDoor);
    }

    /**
     * Returns a string describing all characteristics of this passage.
     * @return the string description
     */
    @Override
    public String getDescription() {
        makeDescription();
        return this.myDescription.toString();
    }

    /**
     * Generates a description for this passage.
     */
    private void makeDescription() {
        this.myDescription = new StringBuilder();

        for (PassageSection passageSection : thePassage) {
            this.myDescription.append(passageSection.getDescription()).append(".\n");

            if (passageSection.getDoor() != null) {
                this.myDescription.append("Door is ").append(passageSection.getDoor().getDescription()).append(".\n");
            }

            if (passageSection.getMonster() != null) {
                this.myDescription.append(passageSection.getMonster().getDescription()).append(".\n");
            }

            this.myDescription.append("\n");
        }
    }

    public String makeDoorDescription(int i) {
        StringBuilder s = new StringBuilder();

        return s.toString();
    }

    /**
     * Makes Passage boring, i.e., 2 plain passage sections, 2 doors.
     */
    void makeBoring() {
        PassageSection temp;

        /* Clear the passage */
        this.thePassage.clear();
        this.myDoors.clear();

        for (int i = 0; i < 2; i++) {
            /* make a PS with a door */
            do {
                temp = new PassageSection();
            } while (temp.getDoor() == null);

            temp.setMonster(null); // removing its monster, if there is one.

            /* adding this boring PS the the passage */
            this.addPassageSection(temp);
        }
    }
}
