package jmorea;

import dnd.models.Trap;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author James Moreau
 */
public class LevelTest {

    public LevelTest() {    
    }

    @Test
    public void testFindGoodDoor1() {
        System.out.println("findGoodDoor using Chamber");
        Chamber myTestChamber = new Chamber();
        Level myTestLevel = new Level();
        boolean result;

        /* System.out.println(myTestChamber.getDoors().toString()); */

        if(myTestChamber.getDoors().size() > 1) {
             if (myTestLevel.findGoodDoor(myTestChamber) != null) {
                result = true;
             } else {
                result = false;
             }
        } else { //is 1 or 0
            if (myTestLevel.findGoodDoor(myTestChamber) == null) {
                result = true;
            } else {
                result = false;
            }
        }

        assertEquals(true, result);
    }

    @Test
    public void testFindGoodDoor2() {
        System.out.println("findGoodDoor using Passage");
        Level myTestLevel = new Level();
        boolean result = false;

        for (int i = 0; i < 50; i++) {
            Passage myTestPassage = new Passage();
            /* System.out.println(myTestPassage.getDoors().toString()); */

            if (myTestPassage.getDoors().size() > 1) {
                if (myTestLevel.findGoodDoor(myTestPassage) != null) {
                    result = true;
                    break;
                }
            } else { // is 1 or 0
                if (myTestLevel.findGoodDoor(myTestPassage) == null) {
                    result = true;
                }
            }
        }

        assertEquals(true, result);
    }

    @Test
    public void testisDeadEnd_SetDeadEnd() {
        System.out.println("isDeadEnd\nsetDeadEnd");
        Level myTestLevel = new Level();
        boolean result;

        myTestLevel.setDeadEnd(false);
        myTestLevel.setDeadEnd(true);

        result = myTestLevel.isDeadEnd();

        assertEquals(true, result);
    }


    @Test
    public void testIsDone() {
        System.out.println("isDone");
        boolean result;
        Level myTestLevel = new Level();

        for(int i = 0; i <10 ; i++) {
            myTestLevel.addChamber(new Chamber());
        }

        if(myTestLevel.isDone()) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void testAddChamber_GetChamberCount() {
        System.out.println("getChamberCount");
        boolean result;
        Level myTestLevel = new Level();

        myTestLevel.addChamber(new Chamber());

        if (myTestLevel.getChamberCount() == 1) {
            result = true;
        } else {
            result = false;
        }
        
        assertEquals(true, result);
    }

    @Test
    public void testAddPassage_getPassageCount() {
        System.out.println("getPassageCount");

        boolean result;
        Level myTestLevel = new Level();
        myTestLevel.addPassage(new Passage());

        if(myTestLevel.getPassageCount() == 1) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void testClearLevel() {
        System.out.println("clearLevel");
        Level myLevel;

        myLevel = new Level();
        boolean result;

        for (int i = 0; i < 7; i++) {
            myLevel.addChamber(new Chamber());
            myLevel.addPassage(new Passage());
        }

        myLevel.clearLevel();

        if ((myLevel.getChambers().size() == 0) && (myLevel.getPassages().size() == 0)) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void testAddChamber() {
        System.out.println("addChamber");
        Level testLevel = new Level();

        Chamber testChamber = new Chamber();
        testLevel.addChamber(testChamber);

        assertEquals(testChamber, testLevel.getChambers().get(0));
    }

    @Test
    public void testGetAvailableDoorCount_ReduceAvailableDoorCount() {
        System.out.println("getAvailableDoorCount\nreduceAvailableDoorCount");
        Chamber tempChamber;
        Level myLevel = new Level();

        /* Making 5 Chambers */
        for (int i = 0; i < 5; i++) {
            tempChamber = new Chamber();
            myLevel.addChamber(tempChamber);
        }

        for (int i = 0; i < 5; i++) {
            myLevel.reduceChamberAvailableDoorCount(i);
        }

        for(int i = 0; i < 5; i++) {
            assertEquals(myLevel.getChambers().get(i).getDoors().size(), (myLevel.getChamberAvailableDoorCount(i) + 1));
        }

    }
}