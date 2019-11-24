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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Chamber class represents an individual chamber for the dungeon.
 */
public class Chamber extends Space implements java.io.Serializable {
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
     *
     * @return the arraylist of doors
     */
    @Override
    public ArrayList<Door> getDoors() {
        return this.myDoors;
    }

    /**
     * Adds a monster to the arraylist of monster.
     *
     * @param theMonster the monster to be added
     */
    public void addMonster(Monster theMonster) {
        theMonster.setType(new Percentile().roll());
        this.myMonsters.add(theMonster);
    }

    /**
     * returns the arraylist of monsters associated with the chamber.
     *
     * @return the arraylist of monsters
     */
    public ArrayList<Monster> getMonsters() {
        return this.myMonsters;
    }


    /**
     * Adds a treasure to the arraylist of treasure.
     *
     * @param theTreasure the treasure to be added
     */
    public void addTreasure(Treasure theTreasure) {
        theTreasure.setContainer(new D20().roll());
        theTreasure.chooseTreasure(new Percentile().roll());
        this.myTreasure.add(theTreasure);
    }

    /**
     * returns the arraylist of treasure associated with the chamber.
     *
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
        this.makeChamberDescription();
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
     * Creates a description for the entire chamber.
     */
    private void makeChamberDescription() {
        this.myDescription = new StringBuilder();
        this.myDescription.append("Chamber Description\n").append("==========================\n");

        this.myDescription.append(makeChamberShapeDescription());
        this.myDescription.append(makeChamberMonstersDescription());
        this.myDescription.append(makeChamberTreasureDescription());
        //this.myDescription.append(makeChamberExitsAndDoorsDescription());
    }

    /**
     * Creates a description for the shape.
     * @return the string description
     */
    private String makeChamberShapeDescription() {
        StringBuilder s = new StringBuilder();

        int area;
        int length;
        int width;

        s.append("Shape:\n");
        s.append("\tThe chamber is ").append(this.myShape.getShape()).append(".\n");
        try {
            length = myShape.getLength();
            width = myShape.getWidth();
            s.append("\tThe chamber's length is ").append(length)
                    .append(" and its width is ").append(width).append(".\n");
        } catch (UnusualShapeException e) {
            area = myShape.getArea();
            s.append("\tThe chamber's area is ").append(area).append(".\n");
        }

        return s.toString();
    }

    /**
     * Creates a description for the monsters.
     * @return the string description
     */
    private String makeChamberMonstersDescription() {
        StringBuilder s = new StringBuilder();

        if (myMonsters.size() > 0) {
            s.append("\nMonsters:\n");
            for (Monster myMonster : myMonsters) {
                s.append("\t").append(myMonster.getDescription());
            }
            s.append(".\n");
        } else {
            s.append("\nNo monsters in this chamber.\n");
        }

        return s.toString();
    }

    /**
     * Creates a description for the Treasure.
     * @return the string description
     */
    private String makeChamberTreasureDescription() {
        StringBuilder s = new StringBuilder();

        if (myTreasure.size() > 0) {
            s.append("\nTreasure:\n");
            for (Treasure treasure : myTreasure) {
                s.append("\t").append(treasure.getDescription());
                try {
                    s.append(" is protected by ").append(treasure.getProtection());
                } catch (NotProtectedException e) {
                    s.append(" is left unprotected");
                }
            }
            s.append(".\n");
        } else {
            s.append("\nNo treasure in this chamber.\n");
        }

        return s.toString();
    }

    /**
     * Creates a description for the exits/doors.
     * @return the string description
     */
    private String makeChamberExitsAndDoorsDescription() {
        StringBuilder s = new StringBuilder();

        if (myShape.getNumExits() > 0) {
            s.append("\nExits:\n");
            for (int k = 0; k < this.myDoors.size(); k++) {
                s.append("Door #").append(k).append(":\n");
                s.append(makeDoorDescription(k));
            }
        } else {
            s.append("\nNo doors in this chamber. This is a dead end.\n");
        }

        return s.toString();
    }

    /**
     * Creates a description for an exits/door.
     * @param i the index of the door in door list.
     * @return the string door description.
     */
    public String makeDoorDescription(int i) {
        StringBuilder s = new StringBuilder();

        s.append("\tDoor is ").append(myDoors.get(i).getDescription()).append(".\n");
        s.append("\tDoor is on ").append(myDoors.get(i).getExit().getLocation()).append(" ").append(myDoors.get(i).getExit().getDirection());

        if (myDoors.get(i).isTrapped()) {
            s.append("\tDoor Trap:").append(this.myDoors.get(i).getTrapDescription());
        }

        return s.toString();
    }


    /**
     * Adds a door to the arraylist of doors.
     *
     * @param newDoor the door to be added
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to this room
        this.myDoors.add(newDoor);
    }
}
