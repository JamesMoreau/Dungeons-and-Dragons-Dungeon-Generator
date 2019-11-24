package gui;

import dnd.models.Monster;
import dnd.models.Treasure;
import jmorea.Passage;
import jmorea.Algorithm;
import jmorea.Chamber;

import java.util.ArrayList;

public class Controller {

    /**
     * the dungeon gui pointer.
     */
    private DungeonGui myGui;

    /**
     * the algorithm.
     */
    private Algorithm myAlgo;

    /**
     * the number of chambers to be generated in this application.
     */
    private static int numChambers = 5;

    /**
     * the controller constructor.
     * @param theGui the pointer to the application's gui
     */
    public Controller(DungeonGui theGui) {
        this.myGui = theGui;
        myAlgo = new Algorithm();
        myAlgo.performAlgorithm(numChambers);

    }

    /**
     * Returns the chambers from level.
     *
     * @return the arra list of chambers
     */
    public ArrayList<Chamber> getChambersList() {
        return myAlgo.getLevel().getChambers();
    }

    /**
     * Returns the passages from level.
     *
     * @return the array list of passages
     */
    public ArrayList<Passage> getPassageList() {
        return myAlgo.getLevel().getPassages();
    }

    /**
     * reacts to edit button being pressed.
     */
    public void reactToEditButton() {
        System.out.println("Edit Functionality!");
    }

    /**
     * returns the chamber description after it has been changed.
     *
     * @param i the index of the chamber
     * @return the string description
     */
    public String getNewChamberDescription(int i) {
        return myAlgo.getLevel().getChambers().get(i).getDescription();
    }

    /**
     * returns the chamber description after it has been changed.
     *
     * @param i the index of the chamber
     * @return the string description
     */
    public String getNewPassageDescription(int i) {
        return myAlgo.getLevel().getPassages().get(i).getDescription();
    }

    /**
     * returns a door's description.
     *
     * @param chamberIndex the index of the chamber
     * @param doorIndex    the index of the door inside the chamber
     * @return the string description
     */
    public String getChamberDoorDescription(int chamberIndex, int doorIndex) {
        //System.out.println("getDoorDescription at chamberIndex " + chamberIndex + " and doorIndex " + doorIndex);
        return myAlgo.getLevel().getChambers().get(chamberIndex).makeDoorDescription(doorIndex);
    }

    /**
     * returns a door's description.
     *
     * @param passageIndex the index of the passage
     * @param doorIndex    the index of the door inside the passage.
     * @return the string description
     */
    public String getPassageDoorDescription(int passageIndex, int doorIndex) {
        return myAlgo.getLevel().getPassages().get(passageIndex).makeDoorDescription(doorIndex);
    }

    /**
     * returns the monsters from a chamber.
     *
     * @param i the index of the chamber
     * @return the arraylist of monsters
     */
    public ArrayList<Monster> getChamberMonsters(int i) {
        return myAlgo.getLevel().getChambers().get(i).getMonsters();
    }

    /**
     * adds a monster the a chamber.
     *
     * @param i the index of the chamber.
     */
    public void addChamberMonster(int i) {
        myAlgo.getLevel().getChambers().get(i).addMonster(new Monster());
    }

    /**
     * returns the treasure list from a given chamber.
     *
     * @param i the index of the chamber
     * @return the array list of treasure
     */
    public ArrayList<Treasure> getChamberTreasureList(int i) {
        return myAlgo.getLevel().getChambers().get(i).getTreasureList();
    }

    /**
     * returns the treasure list from a given passage.
     *
     * @param i the index of the passage
     * @return the array list of treasure
     */
    public ArrayList<Treasure> getPassageTreasureList(int i) {
        return myAlgo.getLevel().getPassages().get(i).getTreasureList();
    }

    /**
     * adds a treasure to a given chamber.
     * @param i the index of the chamber
     */
    public void addChamberTreasure(int i) {
        myAlgo.getLevel().getChambers().get(i).addTreasure(new Treasure());
    }

    /**
     * adds a treasure to a given passage.
     * @param i the index of the passage
     */
    public void addPassageTreasure(int i) {
        myAlgo.getLevel().getPassages().get(i).addTreasure(new Treasure());
    }

}
