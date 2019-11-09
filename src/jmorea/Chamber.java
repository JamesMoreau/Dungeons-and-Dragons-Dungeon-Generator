/**
 * My Dungeon generation package
 */
package jmorea;

import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.die.D20;
import dnd.die.Percentile;
import java.util.ArrayList;

/**
 * Chamber class represents an individual chamber for the dungeon.
 */
public class Chamber extends Space {
    /**
     * Chamber contents.
     */
    private ChamberContents myContents;

    /**
     * Chamber shape.
     */
    private ChamberShape myShape;

    /**
     * Chamber Description builder.
     */
    private StringBuilder myDescription;

    /**
     * Chamber door array.
     */
    private ArrayList<Door> myDoors;

    /**
     * Chamber monsters array.
     */
    private ArrayList<Monster> myMonsters;

    /**
     * Chamber treasure array.
     */
    private ArrayList<Treasure> myTreasure;

    /**
     * Creates a chamber with basic qualities.
     */
    public Chamber() {
        init();
    }

    /**
     * Initializes the chamber qualities.
     */
    private void init() {
        this.initializeVars();
        //Generation which applies to all chambers
        this.myShape.setNumExits();
        this.myContents.chooseContents(new D20().roll());

        for (int i = 0; i < myShape.getNumExits(); i++) { // Exits and doors map 1 to 1 in ArrayLists
            Door tempDoor = new Door(new Exit()); //initializes door to default
            tempDoor.setOneSpace(this);
        }
        // Specific generation
        if (this.myContents.getDescription().contains("monster")) {
            Monster newMonster = new Monster();
            this.addMonster(newMonster);
        }
        if (this.myContents.getDescription().contains("treasure")) {
            Treasure newTreasure = new Treasure();
            this.addTreasure(newTreasure);
        }
    }

    /**
     * returns the arraylist of doors associated with the chamber.
     * @return the arraylist of doors
     */
    @Override
    public ArrayList<Door> getDoors() {
        return this.myDoors;
    }

    /**
     * Adds a monster to the arraylist of monster.
     * @param theMonster the monster to be added
     */
    public void addMonster(Monster theMonster) {
        theMonster.setType(new Percentile().roll());
        this.myMonsters.add(theMonster);
    }

    /**
     * returns the arraylist of monsters associated with the chamber.
     * @return the arraylist of monsters
     */
    public ArrayList<Monster> getMonsters() {
        return this.myMonsters;
    }


    /**
     * Adds a treasure to the arraylist of treasure.
     * @param theTreasure the treasure to be added
     */
    public void addTreasure(Treasure theTreasure) {
        theTreasure.setContainer(new D20().roll());
        theTreasure.chooseTreasure(new Percentile().roll());
        this.myTreasure.add(theTreasure);
    }

    /**
     * returns the arraylist of treasure associated with the chamber.
     * @return the arraylistof treasure
     */
    public ArrayList<Treasure> getTreasureList() {
        return this.myTreasure;
    }

    /**
     * Returns a string describing the chamber.
     */
    @Override
    public String getDescription() {
        this.makeChamberDecription();
        return myDescription.toString();
    }

    /**
     * Initializes standard variables of a chamber.
     */
    private void initializeVars() {
        this.myContents = new ChamberContents();
        this.myShape = ChamberShape.selectChamberShape(new D20().roll());
        this.myDoors = new ArrayList<>();
        this.myMonsters = new ArrayList<>();
        this.myTreasure = new ArrayList<>();
    }

    /**
     * Creates a description for the entire chameber.
     */
    private void makeChamberDecription() {
        this.myDescription = new StringBuilder();

        this.myDescription.append("\n\n____________Chamber____________\n\n");

        this.makeChamberShapeDescription();
        this.makeChamberMonstersDescription();
        this.makeChamberTreasureDescription();
        this.makeChamberExitsAndDoorsDescription();
    }

    /**
     *  Creates a description for the shape.
     */
    private void makeChamberShapeDescription() {
        int area = 0;
        int length = 0;
        int width = 0;
        this.myDescription.append("\nShape:\n");
        this.myDescription.append("\tThe chamber is ").append(this.myShape.getShape()).append(".\n");
        try {
            length = myShape.getLength();
            width = myShape.getWidth();
            this.myDescription.append("\tThe chamber's length is ").append(length)
                    .append(" and its width is ").append(width).append(".\n");
        } catch (UnusualShapeException e) {
            area = myShape.getArea();
            this.myDescription.append("\tThe chamber's area is ").append(area).append(".\n");
        }
    }

    /**
     * Creates a description for the monsters.
     */
    private void makeChamberMonstersDescription() {
        if (myMonsters.size() > 0) {
            this.myDescription.append("\nMonsters:\n");
            for (int i = 0; i < myMonsters.size(); i++) {
                this.myDescription.append("\t" + myMonsters.get(i).getDescription());
            }
            this.myDescription.append(".\n");
        } else {
            this.myDescription.append("\nNo monsters in this chamber.\n");
        }
    }

    /**
     * Creates a description for the Treasure.
     */
    private void makeChamberTreasureDescription() {
        if (myTreasure.size() > 0) {
            this.myDescription.append("\nTreasure:\n");
            for (int j = 0; j < myTreasure.size(); j++) {
                this.myDescription.append("\t" + this.myTreasure.get(j).getDescription());
                try {
                    this.myDescription.append(" is protected by " + this.myTreasure.get(j).getProtection());
                } catch (NotProtectedException e) {
                    this.myDescription.append(" is left unprotected");
                }
            }
            this.myDescription.append(".\n");
        } else {
            this.myDescription.append("\nNo treasure in this chamber.\n");
        }
    }

    /**
     * Creates a description for the exits/doors.
     */
    private void makeChamberExitsAndDoorsDescription() {
        if (myShape.getNumExits() > 0) {
            this.myDescription.append("\nExits:\n");
            for (int k = 0; k < this.myDoors.size(); k++) {
                this.myDescription.append("Door #" + k + ":\n");
                this.myDescription.append("\tDoor is " + myDoors.get(k).getDescription() + ".\n");
                this.myDescription.append("\tDoor is on " + myDoors.get(k).getExit().getLocation() + " " + myDoors.get(k).getExit().getDirection() + ".\n"); //Be careful since this executes outside the door class

                if (myDoors.get(k).isTrapped()) {
                    this.myDescription.append("\tDoor Trap:" + this.myDoors.get(k).getTrapDescription() + ".\n");
                }
            }
        } else {
            this.myDescription.append("\nNo doors in this chamber. This is a dead end.\n");
        }
    }


    /**
     * Adds a door to the arraylist of doors.
     * @param newDoor the door to be added
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to this room
        this.myDoors.add(newDoor);
    }
}
