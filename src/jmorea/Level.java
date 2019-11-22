package jmorea;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Level class manages the passages and chambers for this dungeon.
 */
public class Level {

    /**
     * Arraylist of chambers in this dungeon.
     */
    private ArrayList<Chamber> myChambers;

    /**
     * Array list of passages in this dungeon.
     */
    private ArrayList<Passage> myPassages;

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
    private ArrayList<Integer> chamberAvailableDoorCounts;


    /**
     * Creates a blank level.
     */
    public Level() {
        this.myChambers = new ArrayList<>();
        this.myPassages = new ArrayList<>();
        this.myMap = new HashMap<>();
        this.chamberAvailableDoorCounts = new ArrayList<>();
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
        return (this.myChambers.size() > 5);
    }

    /**
     * returns chamber count in level.
     * @return chamberCount
     */
    int getChamberCount() {
        return this.myChambers.size();
    }

    /**
     * Returns passage count in level.
     * @return passageCount
     */
    int getPassageCount() {
        return this.myPassages.size();
    }

    /**
     * Returns array list of chambers in level.
     * @return myChambers
     */
    public ArrayList<Chamber> getChambers() {
        return this.myChambers;
    }

    /**
     * Returns array list of passages in level.
     * @return myPassages
     */
    public ArrayList<Passage> getPassages() {
        return this.myPassages;
    }

    /**
     * Adds a chamber to the chamber list.
     * @param theChamber chamber to be added
     */
    void addChamber(Chamber theChamber) {
        this.myChambers.add(theChamber);
        this.chamberAvailableDoorCounts.add(theChamber.getDoors().size());
    }

    /**
     * Adds a passage to the passage list.
     * @param thePassage chamber to be added
     */
    void addPassage(Passage thePassage) {
        this.myPassages.add(thePassage);
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
        this.myChambers.clear();
        this.myPassages.clear();
    }

    /**
     * Returns the number of available doors in a given chamber.
     * @param i the index of the space.
     * @return the available door count for that chamber
     */
    // TODO write test for this. add in report.
    int getChamberAvailableDoorCount(int i) {
        if (i >= this.chamberAvailableDoorCounts.size()) {
            System.out.println("Desired Chamber index does not exist");
            return -1;
        }

        return (this.chamberAvailableDoorCounts.get(i));
    }


    /**
     * Reduces the number of available doors in a given chamber.
     * @param i the index of the space.
     */
    void reduceChamberAvailableDoorCount(int i) {
        if (i >= this.chamberAvailableDoorCounts.size()) {
            System.out.println("Desired Chamber index does not exist");
        } else {
            this.chamberAvailableDoorCounts.set(i, (this.chamberAvailableDoorCounts.get(i) - 1));
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
            c = this.getChambers().get(i);

            for (int j = 0; j < this.myChambers.get(i).getDoors().size(); j++) { //looping over every door in that chamber

                temp = myMap.get(this.myChambers.get(i).getDoors().get(j)); //temp is the chamber the door is targeting

                for (int k = 0; k < temp.get(0).getDoors().size(); k++) {
                    if ((myMap.get(temp.get(0).getDoors().get(k))).get(0) == c) { //doors are pointing at each other's chamber's UwU
                        System.out.println("hi");
                    }
                }
            }
        }
    }

}
