import static org.junit.Assert.*;
import org.junit.Test;

public class Fragment_Test {

    //-----------------------------
    // Constructor
    //-----------------------------

    @Test
    public void testConstructorStoresStartState() {

        State start = new State("S0", false);
        State end = new State("S1", true);

        Fragment fragment = new Fragment(start, end);

        assertSame(start, fragment.start);
    }

    @Test
    public void testConstructorStoresEndState() {

        State start = new State("S0", false);
        State end = new State("S1", true);

        Fragment fragment = new Fragment(start, end);

        assertSame(end, fragment.end);
    }

    @Test
    public void testConstructorStoresExactReferences() {

        State start = new State("Start", false);
        State end = new State("End", true);

        Fragment fragment = new Fragment(start, end);

        assertSame(start, fragment.start);
        assertSame(end, fragment.end);
    }

    @Test
    public void testStartAndEndCanBeSameState() {

        State state = new State("S0", true);

        Fragment fragment = new Fragment(state, state);

        assertSame(state, fragment.start);
        assertSame(state, fragment.end);
    }

    @Test
    public void testStatePropertiesPreserved() {

        State start = new State("A", false);
        State end = new State("B", true);

        Fragment fragment = new Fragment(start, end);

        assertEquals("A", fragment.start.id);
        assertEquals("B", fragment.end.id);

        assertFalse(fragment.start.accept);
        assertTrue(fragment.end.accept);
    }
}