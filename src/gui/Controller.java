package gui;

import jmorea.*;

import java.util.ArrayList;

public class Controller {
    private dungeonGui myGui;
    private Algorithm myAlgo;
    private static int NUM_CHAMBERS = 5;


    public Controller(dungeonGui theGui) {
        myGui = theGui;
        myAlgo = new Algorithm();
        myAlgo.performAlgorithm(NUM_CHAMBERS);

    }

    /**
     * Returns the chambers from level.
     */
    public ArrayList<Chamber> getChambersList() {
        return myAlgo.getLevel().getChambers();
    }

    /**
     * Returns the passages from level.
     */
    public ArrayList<Passage> getPassageList() {
        return myAlgo.getLevel().getPassages();
    }

    public void reactToEditButton() {
        System.out.println("Edit Functionality!");
    }

    public String getNewChamberDescription(int i) {
        return myAlgo.getLevel().getChambers().get(i).getDescription();
    }

    public String getNewPassageDescription(int i) {
        return myAlgo.getLevel().getPassages().get(i).getDescription();
    }

    public String getChamberDoorDescription(int chamberIndex, int doorIndex) {
        //System.out.println("getDoorDescription at chamberIndex " + chamberIndex + " and doorIndex " + doorIndex);
        return myAlgo.getLevel().getChambers().get(chamberIndex).makeDoorDescription(doorIndex);
    }

    public String getPassageDoorDescription(int passageIndex, int doorIndex) {
        return myAlgo.getLevel().getPassages().get(passageIndex).makeDoorDescription(doorIndex);
    }
}