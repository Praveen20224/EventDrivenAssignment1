import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class DFAState_Test {

    //-----------------------------
    // Constructor
    //-----------------------------

    @Test
    public void testConstructorStoresId() {

        DFAState state = new DFAState(
                "D0",
                new ArrayList<NFAState>(),
                true,
                false);

        assertEquals("D0", state.id);
    }

    @Test
    public void testConstructorStoresStartFlag() {

        DFAState state = new DFAState(
                "D0",
                new ArrayList<NFAState>(),
                true,
                false);

        assertTrue(state.start);
    }

    @Test
    public void testConstructorStoresAcceptFlag() {

        DFAState state = new DFAState(
                "D0",
                new ArrayList<NFAState>(),
                false,
                true);

        assertTrue(state.accept);
    }

    @Test
    public void testConstructorInitializesTransitionList() {

        DFAState state = new DFAState(
                "D0",
                new ArrayList<NFAState>(),
                false,
                false);

        assertNotNull(state.transitions);
        assertTrue(state.transitions.isEmpty());
    }

    @Test
    public void testConstructorCopiesSubset() {

        ArrayList<NFAState> subset = new ArrayList<>();
        subset.add(new NFAState("S0", true, false));

        DFAState state = new DFAState(
                "D0",
                subset,
                true,
                false);

        subset.add(new NFAState("S1", false, true));

        assertEquals(1, state.subset.size());
    }

    @Test
    public void testConstructorStoresSubsetContents() {

        ArrayList<NFAState> subset = new ArrayList<>();

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, true);

        subset.add(s0);
        subset.add(s1);

        DFAState state = new DFAState(
                "D0",
                subset,
                true,
                false);

        assertEquals(2, state.subset.size());
        assertTrue(state.subset.contains(s0));
        assertTrue(state.subset.contains(s1));
    }

    //-----------------------------
    // addTransition()
    //-----------------------------

    @Test
    public void testAddSingleTransition() {

        DFAState from = new DFAState(
                "D0",
                new ArrayList<NFAState>(),
                true,
                false);

        DFAState to = new DFAState(
                "D1",
                new ArrayList<NFAState>(),
                false,
                true);

        from.addTransition('a', to);

        assertEquals(1, from.transitions.size());

        DFATransition t = from.transitions.get(0);

        assertEquals('a', t.symbol);
        assertSame(to, t.to);
    }

    @Test
    public void testAddMultipleTransitions() {

        DFAState from = new DFAState(
                "D0",
                new ArrayList<NFAState>(),
                true,
                false);

        DFAState d1 = new DFAState(
                "D1",
                new ArrayList<NFAState>(),
                false,
                false);

        DFAState d2 = new DFAState(
                "D2",
                new ArrayList<NFAState>(),
                false,
                true);

        from.addTransition('a', d1);
        from.addTransition('b', d2);

        assertEquals(2, from.transitions.size());

        assertEquals('a', from.transitions.get(0).symbol);
        assertEquals('b', from.transitions.get(1).symbol);
    }

    @Test
    public void testTransitionOrderPreserved() {

        DFAState from = new DFAState(
                "D0",
                new ArrayList<NFAState>(),
                true,
                false);

        DFAState d1 = new DFAState(
                "D1",
                new ArrayList<NFAState>(),
                false,
                false);

        DFAState d2 = new DFAState(
                "D2",
                new ArrayList<NFAState>(),
                false,
                true);

        from.addTransition('a', d1);
        from.addTransition('b', d2);

        assertSame(d1, from.transitions.get(0).to);
        assertSame(d2, from.transitions.get(1).to);
    }
}