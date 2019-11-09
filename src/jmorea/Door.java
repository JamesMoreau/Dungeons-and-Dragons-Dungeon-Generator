package jmorea;

import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;
import dnd.die.D20;
import dnd.die.D10;
import dnd.die.D6;

/**
 *
 * Door class represent an individual door that attaches two spaces.
 */
public class Door {

    /**
     * Trap state.
     */
    private boolean trapFlag;

    /**
     * Door's trap.
     */
    private Trap doorTrap;

    /**
     * Open state.
     */
    private boolean openFlag;

    /**
     * ARchway state.
     */
    private boolean archwayFlag;

    /**
     * Lock state.
     */
    private boolean lockFlag;

    /**
     * Door's exit.
     */
    private Exit myExit;

    /**
     * Door's first and second spaces.
     */
    private ArrayList<Space> spaceList;

    /**
     * String description of door.
     */
    private StringBuilder myDescription;

    /**
     * Creates a door initialized to defaults.
     */
    public Door() {
        init();
    }

    /**
     * Creates a door using a specified exit.
     * @param theExit the exit to be used
     */
    public Door(Exit theExit) {
        //sets up the door based on the Exit from the tables
        init();
        this.myExit = theExit;
    }

    /**
     * Sets the door to default values.
     */
    private void init() {
        initVars();

        //Die
        D20 myD20 = new D20();
        D10 myD10 = new D10();
        D6 myD6 = new D6();

        // 1/20 chance it will be a trapped door
        if (myD20.roll() == myD20.roll()) {
            this.setTrapped(true);
            this.doorTrap.chooseTrap(new D20().roll());
        }
        // 1/6 chance it will be locked
        if (myD6.roll() == myD6.roll()) {
            this.setLock(true);
            this.setOpen(false);
        }
        // 1/10 chance that the door is an archway (in which case it is not trapped and not locked but is open)
        if (myD10.roll() == myD10.roll()) {
            this.setArchway(true);
        }
        // 1/2 chance that the door is closed
        if ((myD10.roll() % 2) == (myD10.roll() % 2)) {
            this.setOpen(false);
        }
    }

    /**
     * Sets the door as a trapped/untrapped based on flag.
     *@param flag boolean to set door trap state
     *@param roll roll used to make trap
     */
    public void setTrapped(boolean flag, int... roll) {
        // true == trapped.  Trap must be rolled if no integer is given
        D20 myD20 = new D20();
        if (roll.length == 0) {
            this.doorTrap.chooseTrap(new D20().roll());
        } else {
            this.doorTrap.chooseTrap(roll[0]);
        }
        this.trapFlag = flag;
    }

    /**
     * Returns the traps description.
     * @return doorTrap description
     */
    public String getTrapDescription() {
        return doorTrap.getDescription();
    }

    /**
     * Returns door's trap state.
     *@return the trapFLag
     */
    public boolean isTrapped() {
        return this.trapFlag;
    }

    /**
     * Sets door's open state.
     *@param flag boolean used to set door's open state
     */
    public void setOpen(boolean flag) {
        //true == open
        if (!this.isArchway()) { //Archway constraint

            this.openFlag = flag;
        } else {
            this.openFlag = true;
        }

        if (this.isOpen()) {
            this.setLock(false);
        }
    }

    /**
     * Returns door's open state.
     * @return openFlag
     */
    public boolean isOpen() {
        return this.openFlag;
    }

    /**
     * Sets the door's archway state.
     *@param flag boolean to set door archway state
     */
    public void setArchway(boolean flag) {
        //true == is archway
        this.archwayFlag = flag;

        if (this.isArchway()) {
            this.setTrapped(false);
            this.setLock(false);
            this.setOpen(true);
        }
    }

    /**
     * Returns the door's archway state.
     *@return the archwayFlag
     */
    public boolean isArchway() {
        return this.archwayFlag;
    }

    /**
     * Sets the door's locked state.
     *@param flag boolean to set door's lock state
     */
    public void setLock(boolean flag) {
        this.lockFlag = flag;
    }

    /**
     * Return's door's lock state.
     *@return lockFlag
     */
    public boolean isLock() {
        return this.lockFlag;
    }

    /**
     * Returns door's space arraylist.
     *@return the space arraylist
     */
    public ArrayList<Space> getSpaces() {
        //returns the two spaces that are connected by the door
        return this.spaceList;
    }

    /**
     * Sets the door's first space.
     *@param s space to be connected.
     */
    public void setOneSpace(Space s) {
        this.spaceList.add(s);
        s.setDoor(this);
    }

    /**
     * Sets the door's second space.
     *@param s space to be connected.
     */
    public void setTwoSpace(Space s) {
        if (this.spaceList.size() == 0) {
            System.out.println("Could not add second space to door since first space DNE");
        } else {
            this.spaceList.add(s);
            s.setDoor(this);
        }
    }

    /**
     * Adds a space to this door.
     * @param s the space to be added to the arraylist of spaces
     */
    public void addSpace(Space s) {
            this.spaceList.add(s);
            s.setDoor(this); //Maybe?
    }

    /**
     * Sets both of door's spaces.
     *@param spaceOne space to be connected.
     *@param spaceTwo space to be connected.
     */
    public void setSpaces(Space spaceOne, Space spaceTwo) {
        //identifies the two spaces with the door
        //this method should also call the addDoor method from Space
        //Should set second space to null if it not known
        this.spaceList.add(0, spaceOne);
        this.spaceList.add(1, spaceTwo);
        if (spaceOne != null) {
            spaceOne.setDoor(this);
        }
        if (spaceTwo != null) {
            spaceTwo.setDoor(this);
        }
    }

    /**
     * Returns a string describing the door.
     * @return the string description
     */
    public String getDescription() {
        //remember to test for trap using its flag
        makeDescription();
        return this.myDescription.toString();
    }

    public Exit getExit() {
        return this.myExit;
    }

    private void initVars() {
        this.trapFlag = false;
        this.doorTrap = new Trap();
        this.openFlag = true;
        this.archwayFlag = false;
        this.lockFlag = false;
        this.myExit = new Exit();
        this.spaceList = new ArrayList<Space>(); // index 0 will be previous space, 1 will be next space
    }

    private void makeDescription() {
        this.myDescription = new StringBuilder();

        if (this.isArchway()) {
            this.myDescription.append("an archway,");
        } else {
            if (this.isOpen()) {
                this.myDescription.append("open,");
            } else {
                this.myDescription.append("closed,");

                if (this.isLock()) {
                    this.myDescription.append("locked,");
                } else {
                    this.myDescription.append("unlocked,");
                }
            }

            if (this.isTrapped()) {
                this.myDescription.append("trapped,");
            }
        }

        this.myDescription.deleteCharAt(this.myDescription.length() - 1);
    }
}
