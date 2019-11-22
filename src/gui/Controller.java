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
}