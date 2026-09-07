import static org.junit.Assert.*;
import org.junit.Test;

public class State_Test {

    //-----------------------------
    // Constructor
    //-----------------------------

    @Test
    public void testConstructorStoresId() {

        State s = new State("A", false);

        assertEquals("A", s.id);
    }

    @Test
    public void testConstructorStoresAcceptTrue() {

        State s = new State("A", true);

        assertTrue(s.accept);
    }

    @Test
    public void testConstructorStoresAcceptFalse() {

        State s = new State("A", false);

        assertFalse(s.accept);
    }

    @Test
    public void testConstructorInitializesTransitionList() {

        State s = new State("A", false);

        assertNotNull(s.transitions);
        assertTrue(s.transitions.isEmpty());
    }

    //-----------------------------
    // addTransition()
    //-----------------------------

    @Test
    public void testAddSingleTransition() {

        State s0 = new State("A", false);
        State s1 = new State("B", true);

        s0.addTransition(s1, 'a');

        assertEquals(1, s0.transitions.size());

        Transition t = s0.transitions.get(0);

        assertEquals(s0, t.from);
        assertEquals(s1, t.to);
        assertEquals('a', t.symbol);
    }

    @Test
    public void testAddMultipleTransitions() {

        State s0 = new State("A", false);
        State s1 = new State("B", false);
        State s2 = new State("C", true);

        s0.addTransition(s1, 'a');
        s0.addTransition(s2, 'b');

        assertEquals(2, s0.transitions.size());

        assertEquals('a', s0.transitions.get(0).symbol);
        assertEquals('b', s0.transitions.get(1).symbol);
    }

    @Test
    public void testAddSelfLoopTransition() {

        State s = new State("A", false);

        s.addTransition(s, 'a');

        Transition t = s.transitions.get(0);

        assertEquals(s, t.from);
        assertEquals(s, t.to);
    }

    @Test
    public void testTransitionOrderPreserved() {

        State s0 = new State("A", false);
        State s1 = new State("B", false);
        State s2 = new State("C", false);

        s0.addTransition(s1, 'a');
        s0.addTransition(s2, 'b');

        assertEquals(s1, s0.transitions.get(0).to);
        assertEquals(s2, s0.transitions.get(1).to);
    }
}