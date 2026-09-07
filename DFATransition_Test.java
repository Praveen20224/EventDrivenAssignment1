import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class DFATransition_Test {

    //-----------------------------
    // Constructor
    //-----------------------------

    @Test
    public void testConstructorStoresSymbol() {

        DFAState destination = new DFAState(
                "D1",
                new ArrayList<NFAState>(),
                false,
                true);

        DFATransition t = new DFATransition('a', destination);

        assertEquals('a', t.symbol);
    }

    @Test
    public void testConstructorStoresDestination() {

        DFAState destination = new DFAState(
                "D1",
                new ArrayList<NFAState>(),
                false,
                true);

        DFATransition t = new DFATransition('a', destination);

        assertSame(destination, t.to);
    }

    @Test
    public void testDifferentSymbols() {

        DFAState destination = new DFAState(
                "D1",
                new ArrayList<NFAState>(),
                false,
                false);

        DFATransition t1 = new DFATransition('a', destination);
        DFATransition t2 = new DFATransition('b', destination);

        assertEquals('a', t1.symbol);
        assertEquals('b', t2.symbol);
    }

    @Test
    public void testDestinationIsAcceptState() {

        DFAState destination = new DFAState(
                "Accept",
                new ArrayList<NFAState>(),
                false,
                true);

        DFATransition t = new DFATransition('x', destination);

        assertTrue(t.to.accept);
    }

    @Test
    public void testDestinationIsStartState() {

        DFAState destination = new DFAState(
                "Start",
                new ArrayList<NFAState>(),
                true,
                false);

        DFATransition t = new DFATransition('y', destination);

        assertTrue(t.to.start);
    }

    @Test
    public void testStoresExactReference() {

        DFAState destination = new DFAState(
                "D1",
                new ArrayList<NFAState>(),
                false,
                false);

        DFATransition t = new DFATransition('z', destination);

        assertSame(destination, t.to);
    }
}