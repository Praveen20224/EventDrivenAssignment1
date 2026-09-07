import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class NFATransition_Test {

    //-----------------------------
    // Constructor
    //-----------------------------

    @Test
    public void testConstructorStoresSymbol() {

        ArrayList<NFAState> dest = new ArrayList<>();

        NFATransition t = new NFATransition('a', dest);

        assertEquals('a', t.symbol);
    }

    @Test
    public void testConstructorStoresDestinationList() {

        ArrayList<NFAState> dest = new ArrayList<>();

        NFATransition t = new NFATransition('a', dest);

        assertSame(dest, t.to);
    }

    @Test
    public void testSingleDestination() {

        NFAState s1 = new NFAState("S1", false, true);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);

        NFATransition t = new NFATransition('a', dest);

        assertEquals(1, t.to.size());
        assertTrue(t.to.contains(s1));
    }

    @Test
    public void testMultipleDestinations() {

        NFAState s1 = new NFAState("S1", false, false);
        NFAState s2 = new NFAState("S2", false, true);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);
        dest.add(s2);

        NFATransition t = new NFATransition('b', dest);

        assertEquals(2, t.to.size());
        assertTrue(t.to.contains(s1));
        assertTrue(t.to.contains(s2));
    }

    @Test
    public void testEmptyDestinationList() {

        ArrayList<NFAState> dest = new ArrayList<>();

        NFATransition t = new NFATransition('c', dest);

        assertTrue(t.to.isEmpty());
    }

    @Test
    public void testDifferentSymbols() {

        ArrayList<NFAState> dest = new ArrayList<>();

        NFATransition t1 = new NFATransition('a', dest);
        NFATransition t2 = new NFATransition('z', dest);

        assertEquals('a', t1.symbol);
        assertEquals('z', t2.symbol);
    }
}