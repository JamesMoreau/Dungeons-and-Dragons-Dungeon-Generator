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
public class DoorTest {
    
    public DoorTest() {
        

    }

    /* MY WORK */
    @Test
    public void testDoorCreation() {
        System.out.println("Door");
        boolean result, expResult;
        Door myTestDoor = new Door();
        if(myTestDoor != null) {
            result = true;
        } else {
            result = false;
        }
        expResult = true;
        assertEquals(true, result);
    }

    @Test
    public void testIsTrapped() {
        System.out.println("isTrapped");
        Door myTestDoor = new Door();
        myTestDoor.setArchway(false);
        myTestDoor.setTrapped(true);

        boolean result = myTestDoor.isTrapped();

        assertEquals(true, result);
    }

    @Test
    public void testGetTrapDescription() {
        System.out.println("getTrapDescription");
        Door myTestDoor = new Door();
        myTestDoor.setArchway(false);
        myTestDoor.setTrapped(true);
        boolean result;

        if (myTestDoor.getTrapDescription() != null) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void testIsOpen() {
        System.out.println("isOpen");
        Door myTestDoor = new Door();
        myTestDoor.setArchway(false);
        myTestDoor.setOpen(true);

        boolean result = myTestDoor.isOpen();
        assertEquals(true, result);
    }

    @Test
    public void testSetOpen() {
        System.out.println("setOpen");
        Door myTestDoor = new Door();
        myTestDoor.setArchway(false);
        myTestDoor.setOpen(true);

        boolean result = myTestDoor.isOpen();
        assertEquals(true, result);
    }

    @Test
    public void testSetArchway() {
        System.out.println("setArchway");
        Door myTestDoor = new Door();
        myTestDoor.setArchway(true);

        boolean result = myTestDoor.isArchway();
        assertEquals(true, result);
    }

    @Test
    public void testSetLock() {
        System.out.println("setLock");
        Door myTestDoor = new Door();
        myTestDoor.setArchway(false);
        myTestDoor.setLock(true);

        boolean result = myTestDoor.isLock();
        assertEquals(true, result);
    }

    @Test
    public void testIsLock() {
        System.out.println("isLock");
        Door myTestDoor = new Door();
        myTestDoor.setArchway(false);
        myTestDoor.setLock(true);

        boolean result = myTestDoor.isLock();
        assertEquals(true, result);
    }

    @Test
    public void testGetSpaces() {
        System.out.println("getSpaces");
        Chamber testChamber1 = new Chamber();
        Chamber testChamber2 = new Chamber();

        Door myTestDoor = new Door();
        myTestDoor.setSpaces(testChamber1, testChamber2);

        ArrayList<Space> result = myTestDoor.getSpaces();
        assertEquals(2, result.size());
    }

    @Test
    public void testSetOneSpace() {
        System.out.println("setOneSpace");
        Chamber testChamber1 = new Chamber();

        Door myTestDoor = new Door();
        myTestDoor.setOneSpace(testChamber1);
        boolean result;

        if(myTestDoor.getSpaces().get(0) != null) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void testSetTwoSpace() {
        System.out.println("setOneSpace");
        Chamber testChamber1 = new Chamber();
        Chamber testChamber2 = new Chamber();

        Door myTestDoor = new Door();
        myTestDoor.setOneSpace(testChamber1);
        myTestDoor.setTwoSpace(testChamber2);
        boolean result;

        if (myTestDoor.getSpaces().get(1) != null) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void testSetSpaces() {
        System.out.println("setSpaces");
        Chamber testChamber1 = new Chamber();
        Chamber testChamber2 = new Chamber();

        Door myTestDoor = new Door();
        myTestDoor.setSpaces(testChamber1, testChamber2);

        ArrayList<Space> result = myTestDoor.getSpaces();
        assertEquals(result.size(), 2);
    }

    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        boolean result;
        Door myTestDoor = new Door();
        if (myTestDoor.getDescription() !=null) {
            result = true;
        } else {
            result = false;
        }
        assertEquals(true, result);
    }    
}
