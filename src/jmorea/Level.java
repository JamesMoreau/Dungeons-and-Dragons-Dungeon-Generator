package jmorea;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Level class manages the passages and chambers for this dungeon.
 */
public class Level {

    /**
     * Arraylist of passages and chambers in this dungeon.
     */
    private ArrayList<Space> mySpaces;

    /**
     * Count of chambers in dungeon.
     */
    private int chamberCount;

    /**
     * Count of passages in dungeon.
     */
    private int passageCount;

    /**
     * Flag describing end status of dungeon.
     */
    private boolean deadEndFlag;

    /**
     * Door map used for a4 generation.
     */
    private HashMap<Door, ArrayList<Chamber>> myMap;

    /**
     * Door counts for each Chamber.
     */
    private ArrayList<Integer> spaceAvailableDoorCounts;


    /**
     * Creates a blank level.
     */
    public Level() {
        this.mySpaces = new ArrayList<>();
        this.myMap = new HashMap<>();
        this.spaceAvailableDoorCounts = new ArrayList<>();
        setChamberCount(0);
        setPassageCount(0);
    }

    /**
     * Adds a chamber to level.
     * @param lastSpace the last space created
     * @param d Door chosen to go through
     */
    @Deprecated
    public void addChamber(Space lastSpace, Door d) {
        Chamber newChamber = new Chamber();
        d.setTwoSpace(newChamber);
        this.mySpaces.add(newChamber);
        this.chamberCount++;
    }

    /**
     * Adds a passage to level.
     * @param lastSpace the last space created
     * @param d Door chosen to go through
     */
    @Deprecated
    public void addPassage(Space lastSpace, Door d) {
        Passage newPassage = new Passage();
        d.setSpaces(lastSpace, newPassage);
        this.mySpaces.add(newPassage);
        this.passageCount++;
    }

    /**
     * Returns a door from a space that can be used to continue generation.
     * @param s space to be searched for a door.
     * @return the found door
     */
    Door findGoodDoor(Space s) {
        Chamber c;
        Passage p;

        if (s.getClass() == Chamber.class) { // Space is a Chamber
            c = (Chamber) s;
            for (int i = c.getDoors().size() - 2; i >= 0; i--) {
                if (c.getDoors().get(i) != null) {
                    return c.getDoors().get(i);
                }
            }
        } else {                            // Space is a Passage
            p = (Passage) s;
            for (int i = p.getDoors().size() - 2; i >= 0; i--) {
                if (p.getDoor(i) != null) {
                    return p.getDoor(i);
                }
            }
        }
        return null; // if no door is found, returns null
    }

    /**
     * Returns boolean state of level dead end state.
     * @return deadEndFlag
     */
    boolean isDeadEnd() {
        return this.deadEndFlag;
    }

    /**
     * Sets level's dead end state.
     * @param x boolean used to set level's dead end state
     */
    void setDeadEnd(Boolean x) {
        this.deadEndFlag = x;
    }

    /**
     * Returns boolean of level's done generation state.
     * @return whether the dungeon is done
     */
    boolean isDone() {
        return (this.chamberCount > 5);
    }

    /**
     * Sets chamber count in level.
     * @param x boolean used to set level's chamber count
     */
    void setChamberCount(int x) {
        this.chamberCount = x;
    }

    /**
     * returns chamber count in level.
     * @return chamberCount
     */
    int getChamberCount() {
        return this.chamberCount;
    }

    /**
     * Sets passage count in level.
     * @param x boolean used to set level's passage count.
     */
    void setPassageCount(int x) {
        this.passageCount = x;
    }

    /**
     * Returns passage count in level.
     * @return passageCount
     */
    int getPassageCount() {
        return this.passageCount;
    }

    /**
     * Returns array list of spaces in level.
     * @return mySpaces
     */
    ArrayList<Space> getSpaces() {
        return this.mySpaces;
    }

    /**
     * Adds a space to the space list.
     * @param theSpace space to be added
     */
    void addSpace(Space theSpace) {
        this.mySpaces.add(theSpace);
    }

    /**
     * Returns the map.
     * @return the hash map of doors to array list of chambers
     * TODO write test + doc
     */
    public HashMap<Door, ArrayList<Chamber>> getMap() {
        return this.myMap;
    }

    /**
     * Clears the level.
     */
    void clearLevel() {
        this.mySpaces.clear();
        setPassageCount(0);
        setChamberCount(0);
    }

    /**
     * Adds a chamber to the arralist of spaces and records its door count.
     * @param toAdd the Chamber to be added to the spacelist
     */
    void addChamber(Chamber toAdd) {
        mySpaces.add(toAdd);
        this.spaceAvailableDoorCounts.add(toAdd.getDoors().size());
        this.chamberCount++;
    }

    /**
     * Adds a passage to the arraylist of spaces andd records its door count.
     * @param toAdd the Passage to be added to the spacelist
     */
    // TODO write test for this. add in report.
    void addPassage(Passage toAdd) {
        mySpaces.add(toAdd);
        this.spaceAvailableDoorCounts.add(toAdd.getDoors().size());
        this.passageCount++;
    }

    /**
     * Returns the number of available doors in a given chamber.
     * @param i the index of the space.
     * @return the available door count for that chamber
     */
    // TODO write test for this. add in report.
    int getAvailableDoorCount(int i) {
        if (i >= this.spaceAvailableDoorCounts.size()) {
            System.out.println("Desired Chamber index does not exist");
            return -1;
        }

        return (this.spaceAvailableDoorCounts.get(i));
    }


    /**
     * Reduces the number of available doors in a given chamber.
     * @param i the index of the space.
     */
    void reduceAvailableDoorCount(int i) {
        if (i >= this.spaceAvailableDoorCounts.size()) {
            System.out.println("Desired Chamber index does not exist");
        } else {
            this.spaceAvailableDoorCounts.set(i, (this.spaceAvailableDoorCounts.get(i) - 1));
        }
    }


    /**
     * Selects a target for a given.
     * @param d the door that selects a target.
     * @param c the doors target chamber
     */
    void selectChamberTarget(Door d, Chamber c) {

        ArrayList<Chamber> cList = this.myMap.get(d);

        //If list array list does not exist, create it
        if (cList == null) {
            cList = new ArrayList<>();
            cList.add(c);
            this.myMap.put(d, cList);
        } else { //array list already exists
            cList.add(c);
        }
    }

    /**
     * Prints the current level.
     * TODO write test and add to report
     */
    void printLevel() {
        System.out.println("\nmyLevel door map:" + this.myMap.toString());
    }

    /**
     * Connects chambers using the hash map
     * TODO does not work: array lists of chambers are hard to work with. :(
     * TODO for each door, check which chamber it targets. For each door in the target chamber, check which chambers those doors target. If a target door targets an origin door's chamber, link them.
     */
    public void connectChambers() {
        Chamber c;
        ArrayList<Chamber> temp;

        for (int i = 0; i < 5; i++) { // looping over every chamber
            c = (Chamber) this.getSpaces().get(i);

            for (int j = 0; j < this.mySpaces.get(i).getDoors().size(); j++) { //looping over every door in that chamber

                temp = myMap.get(this.mySpaces.get(i).getDoors().get(j)); //temp is the chamber the door is targeting

                for (int k = 0; k < temp.get(0).getDoors().size(); k++) {
                    if ((myMap.get(temp.get(0).getDoors().get(k))).get(0) == c) { //doors are pointing at each other's chamber's UwU
                        System.out.println("hi");
                    }
                }
            }
        }
    }

}
