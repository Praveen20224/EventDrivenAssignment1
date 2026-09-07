import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class NFA_Test {

    // -----------------------------
    // getStartStates()
    // -----------------------------

    @Test
    public void testGetStartStatesMultiple() {

        NFA nfa = new NFA(new ArrayList<>());

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, false);
        NFAState s2 = new NFAState("S2", true, false);

        nfa.states.add(s0);
        nfa.states.add(s1);
        nfa.states.add(s2);

        ArrayList<NFAState> result = nfa.getStartStates();

        assertEquals(2, result.size());
        assertTrue(result.contains(s0));
        assertTrue(result.contains(s2));
    }

    @Test
    public void testGetStartStatesNone() {

        NFA nfa = new NFA(new ArrayList<>());

        nfa.states.add(new NFAState("S0", false, false));
        nfa.states.add(new NFAState("S1", false, true));

        assertTrue(nfa.getStartStates().isEmpty());
    }

    // -----------------------------
    // getAcceptStates()
    // -----------------------------

    @Test
    public void testGetAcceptStatesMultiple() {

        NFA nfa = new NFA(new ArrayList<>());

        NFAState s0 = new NFAState("S0", false, false);
        NFAState s1 = new NFAState("S1", false, true);
        NFAState s2 = new NFAState("S2", false, true);

        nfa.states.add(s0);
        nfa.states.add(s1);
        nfa.states.add(s2);

        ArrayList<NFAState> result = nfa.getAcceptStates();

        assertEquals(2, result.size());
        assertTrue(result.contains(s1));
        assertTrue(result.contains(s2));
    }

    @Test
    public void testGetAcceptStatesNone() {

        NFA nfa = new NFA(new ArrayList<>());

        nfa.states.add(new NFAState("S0", true, false));
        nfa.states.add(new NFAState("S1", false, false));

        assertTrue(nfa.getAcceptStates().isEmpty());
    }

    // -----------------------------
    // getDestinations()
    // -----------------------------

    @Test
    public void testGetDestinationsOneDestination() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, false);

        nfa.states.add(s0);
        nfa.states.add(s1);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);

        s0.addTransition('a', dest);

        assertEquals("S1", nfa.getDestinations(s0, 'a'));
    }

    @Test
    public void testGetDestinationsMultipleDestinations() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, false);
        NFAState s2 = new NFAState("S2", false, false);

        nfa.states.add(s0);
        nfa.states.add(s1);
        nfa.states.add(s2);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);
        dest.add(s2);

        s0.addTransition('a', dest);

        assertEquals("S1,S2", nfa.getDestinations(s0, 'a'));
    }

    @Test
    public void testGetDestinationsDifferentSymbols() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');
        symbols.add('b');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, false);
        NFAState s2 = new NFAState("S2", false, false);

        nfa.states.add(s0);
        nfa.states.add(s1);
        nfa.states.add(s2);

        ArrayList<NFAState> aDest = new ArrayList<>();
        aDest.add(s1);

        ArrayList<NFAState> bDest = new ArrayList<>();
        bDest.add(s2);

        s0.addTransition('a', aDest);
        s0.addTransition('b', bDest);

        assertEquals("S1", nfa.getDestinations(s0, 'a'));
        assertEquals("S2", nfa.getDestinations(s0, 'b'));
    }

    @Test
    public void testGetDestinationsNoTransition() {

        NFA nfa = new NFA(new ArrayList<>());

        NFAState s0 = new NFAState("S0", true, false);

        assertEquals("", nfa.getDestinations(s0, 'a'));
    }

    // -----------------------------
    // removeUnreachableStates()
    // -----------------------------

    @Test
    public void testRemoveOneUnreachableState() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, true);
        NFAState s2 = new NFAState("S2", false, false);

        nfa.states.add(s0);
        nfa.states.add(s1);
        nfa.states.add(s2);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);

        s0.addTransition('a', dest);

        nfa.removeUnreachableStates();

        assertEquals(2, nfa.states.size());
        assertFalse(nfa.states.contains(s2));
    }

    @Test
    public void testRemoveMultipleUnreachableStates() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, true);
        NFAState s2 = new NFAState("S2", false, false);
        NFAState s3 = new NFAState("S3", false, false);
        NFAState s4 = new NFAState("S4", false, false);

        nfa.states.add(s0);
        nfa.states.add(s1);
        nfa.states.add(s2);
        nfa.states.add(s3);
        nfa.states.add(s4);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);

        s0.addTransition('a', dest);

        nfa.removeUnreachableStates();

        assertEquals(2, nfa.states.size());
        assertFalse(nfa.states.contains(s2));
        assertFalse(nfa.states.contains(s3));
        assertFalse(nfa.states.contains(s4));
    }

    @Test
    public void testRemoveUnreachableStatesAllReachable() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, false);
        NFAState s2 = new NFAState("S2", false, true);

        nfa.states.add(s0);
        nfa.states.add(s1);
        nfa.states.add(s2);

        ArrayList<NFAState> d1 = new ArrayList<>();
        d1.add(s1);

        ArrayList<NFAState> d2 = new ArrayList<>();
        d2.add(s2);

        s0.addTransition('a', d1);
        s1.addTransition('a', d2);

        nfa.removeUnreachableStates();

        assertEquals(3, nfa.states.size());
    }

    @Test
    public void testRemoveUnreachableStatesEmptyNFA() {

        NFA nfa = new NFA(new ArrayList<>());

        nfa.removeUnreachableStates();

        assertEquals(0, nfa.states.size());
    }

    @Test
     public void testConstructorCopiesAlphabet() {

          ArrayList<Character> symbols = new ArrayList<>();
          symbols.add('a');

          NFA nfa = new NFA(symbols);

          symbols.add('b');

          assertEquals(1, nfa.symbols.size());
          assertFalse(nfa.symbols.contains('b'));
     }

     @Test
     public void testConstructorInitializesStateList() {

          NFA nfa = new NFA(new ArrayList<>());

          assertNotNull(nfa.states);
          assertTrue(nfa.states.isEmpty());
     }

     @Test
     public void testGetDestinationsWrongSymbol() {

          ArrayList<Character> symbols = new ArrayList<>();
          symbols.add('a');

          NFA nfa = new NFA(symbols);

          NFAState s0 = new NFAState("S0", true, false);
          NFAState s1 = new NFAState("S1", false, false);

          ArrayList<NFAState> dest = new ArrayList<>();
          dest.add(s1);

          s0.addTransition('a', dest);

          assertEquals("", nfa.getDestinations(s0, 'b'));
     }

          @Test
     public void testRemoveUnreachableStatesNoStartStates() {

          NFA nfa = new NFA(new ArrayList<>());

          nfa.states.add(new NFAState("S0", false, false));
          nfa.states.add(new NFAState("S1", false, true));

          nfa.removeUnreachableStates();

          assertTrue(nfa.states.isEmpty());
     }

     @Test
     public void testRemoveUnreachableStatesMultipleStartStates() {

          ArrayList<Character> symbols = new ArrayList<>();
          symbols.add('a');

          NFA nfa = new NFA(symbols);

          NFAState s0 = new NFAState("S0", true, false);
          NFAState s1 = new NFAState("S1", true, false);
          NFAState s2 = new NFAState("S2", false, true);

          ArrayList<NFAState> dest = new ArrayList<>();
          dest.add(s2);

          s1.addTransition('a', dest);

          nfa.states.add(s0);
          nfa.states.add(s1);
          nfa.states.add(s2);

          nfa.removeUnreachableStates();

          assertEquals(3, nfa.states.size());
     }
}