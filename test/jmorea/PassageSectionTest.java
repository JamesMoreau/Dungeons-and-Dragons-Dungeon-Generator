package jmorea;

import dnd.models.Exit;
import dnd.models.Monster;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James
 */
public class PassageSectionTest {

    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        PassageSection testPassageSection = new PassageSection();
        boolean result;

        if ( testPassageSection.getDescription() != null) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void testGetDoor() {
        System.out.println("getDoor");
        PassageSection tempPassageSection;
        boolean result = false;

        for (int i = 0; i<10000; i++) {
            tempPassageSection = new PassageSection();

            if(tempPassageSection.getDoor() != null){               
                result = true;
                break;
            }
        }
        assertEquals(true, result);
    }

    @Test
    public void testAddMonster_GetMonster() {
        System.out.println("addMonster\ngetMonster");
        PassageSection tempPassageSection = new PassageSection();
        Monster myMonster = new Monster();
        myMonster.setType(10);
        tempPassageSection.setMonster(myMonster);
        boolean result;

        if (tempPassageSection.getMonster() != null) {
            result = true;
        } else {
            result = false;
        }

        assertEquals(true, result);
    }
}
