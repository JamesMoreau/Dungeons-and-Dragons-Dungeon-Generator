package jmorea;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.DnDElement;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James
 */
public class ChamberTest {

    public ChamberTest() {
    }

    /**
     * Test of getTreasureList method, of class Chamber.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        boolean result;
        boolean expResult = true;
        Chamber myChamber = new Chamber();
        if(myChamber.getDescription() != null) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(expResult, result);
    }



    @Test
    public void testGetDoors() {
        System.out.println("getDoors");
        int maxTestCount = 100;
        Chamber myTestChamber = new Chamber();;
        boolean result;

        if (myTestChamber.getDoors() != null) {
            result = true;
        } else {
            result = false;
        }
        /* System.out.println(myTestChamber.getDoors().toString()); */

        assertEquals(true, result);
    }


    @Test
    public void testAddMonster_GetMonster1() {
        System.out.println("addMonster1\nGetMonster1");
        Chamber myTestChamber = new Chamber();
        boolean result;

        myTestChamber.addMonster(new Monster());
        myTestChamber.addMonster(new Monster());

        if(myTestChamber.getMonsters().size() >= 2) {
            result = true;
        } else {
            result = false;
        }
        assertEquals(true, result);
    }

    @Test
    public void testAddMonster_GetMonster2() {
        System.out.println("addMonster2\nGetMonster2");

        Chamber myTestChamber = new Chamber();
        Monster myTempMonster1 = new Monster();
        Monster myTempMonster2 = new Monster();

        myTestChamber.addMonster(myTempMonster1);
        myTestChamber.addMonster(myTempMonster2);

        assertEquals(myTempMonster1, myTestChamber.getMonsters().get( myTestChamber.getMonsters().size() - 2 ));
        assertEquals(myTempMonster2, myTestChamber.getMonsters().get( myTestChamber.getMonsters().size() - 1 ));
    }

    @Test
    public void testAddTreasure_GetTreasure() {
        System.out.println("addTreasure\naddTreasure");

        Chamber myTestChamber = new Chamber();
        Treasure myTestTreasure1 = new Treasure();
        Treasure myTestTreasure2 = new Treasure();

        myTestChamber.addTreasure(myTestTreasure1);
        myTestChamber.addTreasure(myTestTreasure2);

        assertEquals(myTestTreasure1, myTestChamber.getTreasureList().get(myTestChamber.getTreasureList().size() - 2));
        assertEquals(myTestTreasure2, myTestChamber.getTreasureList().get(myTestChamber.getTreasureList().size() - 1));
    }

    @Test
    public void testSetDoor() {
        System.out.println("setDoor");
        Chamber myTestChamber = new Chamber();
        Door myTestDoor = new Door();
        boolean result = false;

        myTestChamber.setDoor(myTestDoor);

        for(Door d: myTestChamber.getDoors()) {
            if (d.equals(myTestDoor)) {
                result = true;
            }
        }

        assertEquals(true, result);
    }


}