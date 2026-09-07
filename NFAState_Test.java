import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class NFAState_Test {

    //-----------------------------
    // Constructor
    //-----------------------------

    @Test
    public void testConstructorStoresId() {

        NFAState state = new NFAState("S0", true, false);

        assertEquals("S0", state.id);
    }

    @Test
    public void testConstructorStoresStartFlag() {

        NFAState state = new NFAState("S0", true, false);

        assertTrue(state.start);
    }

    @Test
    public void testConstructorStoresAcceptFlag() {

        NFAState state = new NFAState("S0", false, true);

        assertTrue(state.accept);
    }

    @Test
    public void testConstructorInitializesTransitionList() {

        NFAState state = new NFAState("S0", false, false);

        assertNotNull(state.transitions);
        assertTrue(state.transitions.isEmpty());
    }

    //-----------------------------
    // addTransition()
    //-----------------------------

    @Test
    public void testAddSingleTransition() {

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, true);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);

        s0.addTransition('a', dest);

        assertEquals(1, s0.transitions.size());

        NFATransition t = s0.transitions.get(0);

        assertEquals('a', t.symbol);
        assertEquals(1, t.to.size());
        assertTrue(t.to.contains(s1));
    }

    @Test
    public void testAddMultipleTransitions() {

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, false);
        NFAState s2 = new NFAState("S2", false, true);

        ArrayList<NFAState> d1 = new ArrayList<>();
        d1.add(s1);

        ArrayList<NFAState> d2 = new ArrayList<>();
        d2.add(s2);

        s0.addTransition('a', d1);
        s0.addTransition('b', d2);

        assertEquals(2, s0.transitions.size());

        assertEquals('a', s0.transitions.get(0).symbol);
        assertEquals('b', s0.transitions.get(1).symbol);
    }

    @Test
    public void testAddTransitionMultipleDestinations() {

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, false);
        NFAState s2 = new NFAState("S2", false, true);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);
        dest.add(s2);

        s0.addTransition('a', dest);

        NFATransition t = s0.transitions.get(0);

        assertEquals(2, t.to.size());
        assertTrue(t.to.contains(s1));
        assertTrue(t.to.contains(s2));
    }

    @Test
    public void testTransitionOrderPreserved() {

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, false);
        NFAState s2 = new NFAState("S2", false, true);

        ArrayList<NFAState> d1 = new ArrayList<>();
        d1.add(s1);

        ArrayList<NFAState> d2 = new ArrayList<>();
        d2.add(s2);

        s0.addTransition('a', d1);
        s0.addTransition('b', d2);

        assertEquals('a', s0.transitions.get(0).symbol);
        assertEquals('b', s0.transitions.get(1).symbol);
    }
}