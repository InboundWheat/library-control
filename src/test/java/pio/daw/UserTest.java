package pio.daw;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UserTest {
    
    @Test
    public void isInsideTest(){
        User u = new User("U001");
        assertFalse(u.isInside());
        u.registerNewEvent(EventType.ENTRY);
        assertTrue(u.isInside());
        u.registerNewEvent(EventType.ENTRY);
        assertTrue(u.isInside());
        u.registerNewEvent(EventType.EXIT);
        assertFalse(u.isInside());
        u.registerNewEvent(EventType.EXIT);
        assertFalse(u.isInside());
    }
}
