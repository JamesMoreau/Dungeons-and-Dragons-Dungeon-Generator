package jmorea;

import dnd.die.D20;
import dnd.die.Percentile;
import dnd.exceptions.NotProtectedException;
import dnd.models.Monster;
import dnd.models.Treasure;

import java.util.ArrayList;

/**
 * Passage class represents an individual passage in the dungeon.
 */
public class Passage extends Space implements java.io.Serializable {
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
     * Array list of treasure in passage.
     */
    private ArrayList<Treasure> myTreasureList;

    /**
     * Array list of treasure in passage.
     */
    private ArrayList<Monster> myMonsterList;

    /**
     * Creates a passage with default characteristics.
     */
    public Passage() {
        this.thePassage = new ArrayList<>();
        this.myDescription = new StringBuilder();
        this.myDoors = new ArrayList<>();
        this.myTreasureList = new ArrayList<>();
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
     * Adds a treasure to the arraylist of treasure.
     *
     * @param theTreasure the treasure to be added
     */
    public void addTreasure(Treasure theTreasure) {
        theTreasure.setContainer(new D20().roll());
        theTreasure.chooseTreasure(new Percentile().roll());
        this.myTreasureList.add(theTreasure);
    }

    /**
     * Returns the treasure for this passage.
     * @return the treasure list.
     */
    public ArrayList<Treasure> getTreasureList() {
        return this.myTreasureList;
    }

    /**
     * Creates a list of the monsters from the passage sections.
     */
    @Deprecated
    public void makeMonsterList() {
        this.myMonsterList = new ArrayList<>();
        for (PassageSection p : this.thePassage) {
            this.myMonsterList.add(p.getMonster());
        }
    }

    /**
     * Returns the number of passage sections in this passage.
     *
     * @return the passage size
     */
    int getSectionCount() {
        return this.thePassage.size();
    }

    /**
     * Returns the array of doors associated with the passage.
     *
     * @return the door arraylist
     */
    @Override
    public ArrayList<Door> getDoors() {
        //gets all of the doors in the entire passage
        return this.myDoors;
    }

    /**
     * Returns a door from a given section. If no door exits, returns null.
     *
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
     *
     * @param theMonster the monster to be added
     * @param i          the index of the passage section
     */
    void addMonster(Monster theMonster, int i) {
        // adds a monster to section 'i' of the passage
        thePassage.get(i).setMonster(theMonster);
    }

    /**
     * Returns the monster froma given passage section. If no monster exists, returns null.
     *
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
     *
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
     *
     * @param flag the flag value to be set
     */
    void setDone(boolean flag) {
        this.doneFlag = flag;
    }

    /**
     * Returns whether or not the passage is done generating.
     *
     * @return the doneFlag
     */
    boolean isDone() {
        return this.doneFlag;
    }

    /**
     * Adds door to this passage.
     *
     * @param newDoor the door to be added
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to the current Passage Section
        this.myDoors.add(newDoor);
    }

    /**
     * Returns a string describing all characteristics of this passage.
     *
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
        this.myDescription.append("Passage Description\n").append("==========================\n");

        for (int i = 0; i < this.thePassage.size(); i++) {
            this.myDescription.append(this.thePassage.get(i).getDescription()).append(".\n");

            this.myDescription.append(makeDoorDescription(i)).append("\n");

            if (this.thePassage.get(i).getMonster() != null) {
                this.myDescription.append(this.thePassage.get(i).getMonster().getDescription()).append(".\n");
            }

            this.myDescription.append("\n");
        }

        this.myDescription.append(makePassageTreasureDescription());
    }


    /**
     * Generates a treasure description of the passage.
     * @return the string description.
     */
    private String makePassageTreasureDescription() {
        StringBuilder s = new StringBuilder();

        if (myTreasureList.size() > 0) {
            s.append("Treasure:\n");
            for (Treasure treasure : myTreasureList) {
                s.append("\t").append(treasure.getDescription());
                try {
                    s.append(" is protected by ").append(treasure.getProtection());
                } catch (NotProtectedException e) {
                    s.append(" is left unprotected");
                }
            }
            s.append(".");
        } else {
            s.append("No treasure in this passage.");
        }

        return s.toString();
    }


    /**
     * Makes the door description.
     *
     * @param i index of the passage section
     * @return the description of the door
     */
    public String makeDoorDescription(int i) {
        StringBuilder s = new StringBuilder();
        if (this.thePassage.get(i).getDoor() != null) {
            s.append("Door is ").append(this.thePassage.get(i).getDoor().getDescription());
        } else {
            s.append(("No Door"));
        }
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
        this.myTreasureList.clear();

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
