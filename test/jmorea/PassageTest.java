package jmorea;

import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Stairs;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author James
 */
public class PassageTest {
    
    public PassageTest() {
    }   
   
    @Test
    public void testGetSectionCount() {
        System.out.println("getSectionCount");
        Passage tempPassage = new Passage();
        boolean result;

        if (tempPassage.getSectionCount() > 0 ) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void testGetDoors() {
        System.out.println("getDoors");
        Passage tempPassage = new Passage();
        boolean result;

        if(tempPassage.getDoors() != null) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test 
    public void testGetDoor() {
        System.out.println("getDoor");
        Passage tempPassage = new Passage();
        Door tempDoor = new Door();
        boolean result;

        tempPassage.setDoor(tempDoor);

        assertEquals(tempDoor, tempPassage.getDoor(tempPassage.getDoors().size()-1));
    }


    @Test
    public void testAddMonster_GetMonster() {
        System.out.println("addMonster\ngetMonster");
        Passage tempPassage = new Passage();
        Monster myMonster = new Monster();
        boolean result;

        tempPassage.addMonster(myMonster, 0);

        assertEquals(myMonster, tempPassage.getMonster(0));
    }

    @Test
    public void testAddPassageSection() {
        System.out.println("addPassageSection");
        Passage tempPassage = new Passage();
        int length;
        boolean result;

        length = tempPassage.getSectionCount();

        PassageSection testPS = new PassageSection();
        tempPassage.addPassageSection(testPS);

        if( length == tempPassage.getSectionCount()-1) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void testSetDone_IsDone() {
        System.out.println("setDone\nisDone");
        Passage tempPassage = new Passage();

        tempPassage.setDone(false);
        tempPassage.setDone(true);

        assertEquals(true, tempPassage.isDone());
    }

    @Test
    public void testSetDoor() {
        System.out.println("setDoor");
        Passage tempPassage = new Passage();
        Door tempDoor = new Door();
        boolean result;

        tempPassage.setDoor(tempDoor);

        assertEquals(tempDoor, tempPassage.getDoor(tempPassage.getDoors().size() - 1));
    }

    @Test
    public void testGetDescription() {
        System.out.println("getDescription");        
        Passage myPassage = new Passage();
        boolean result;

        if (myPassage.getDescription() != null) {
            result = true;
        } else {
            result = false;
        }

    }

    @Test
    public void testMakeBoring() {
        System.out.println("makeBoring");
        Passage tempPassage = new Passage();
        tempPassage.makeBoring();
        boolean result;

        if((tempPassage.getDoors().size() == 2) && (tempPassage.getSectionCount() == 2)) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

  
}
