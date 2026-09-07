import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class DFA_Test {

    @Test
    public void testTransitionFunctionReturnsDestination() {

        DFA dfa = new DFA();

        DFAState s0 = new DFAState(
                "S0",
                new ArrayList<NFAState>(),
                true,
                false);

        DFAState s1 = new DFAState(
                "S1",
                new ArrayList<NFAState>(),
                false,
                true);

        dfa.states.add(s0);
        dfa.states.add(s1);

        s0.addTransition('a', s1);

        DFAState result = dfa.transitionFuntion(s0, 'a');

        assertEquals(s1, result);
    }

    @Test
    public void testTransitionFunctionReturnsNull() {

        DFA dfa = new DFA();

        DFAState s0 = new DFAState(
                "S0",
                new ArrayList<NFAState>(),
                true,
                false);

        dfa.states.add(s0);

        DFAState result = dfa.transitionFuntion(s0, 'b');

        assertNull(result);
    }

    @Test
    public void testCheckDFAStatePresentFound() {

        DFA dfa = new DFA();

        NFAState n0 = new NFAState("S0", true, false);
        NFAState n1 = new NFAState("S1", false, true);

        ArrayList<NFAState> subset = new ArrayList<>();
        subset.add(n0);
        subset.add(n1);

        DFAState d0 = new DFAState("D0", subset, true, false);
        dfa.states.add(d0);

        ArrayList<NFAState> search = new ArrayList<>();
        search.add(n0);
        search.add(n1);

        DFAState result = dfa.checkDFAStatePresent(search);

        assertEquals(d0, result);
    }

    @Test
    public void testCheckDFAStatePresentNotFound() {

        DFA dfa = new DFA();

        NFAState n0 = new NFAState("S0", true, false);
        NFAState n1 = new NFAState("S1", false, true);
        NFAState n2 = new NFAState("S2", false, false);

        ArrayList<NFAState> subset = new ArrayList<>();
        subset.add(n0);
        subset.add(n1);

        DFAState d0 = new DFAState("D0", subset, true, false);
        dfa.states.add(d0);

        ArrayList<NFAState> search = new ArrayList<>();
        search.add(n0);
        search.add(n2);

        DFAState result = dfa.checkDFAStatePresent(search);

        assertNull(result);
    }

    @Test
    public void testCheckDFAStatePresentDifferentOrder() {

        DFA dfa = new DFA();

        NFAState n0 = new NFAState("S0", true, false);
        NFAState n1 = new NFAState("S1", false, true);

        ArrayList<NFAState> subset = new ArrayList<>();
        subset.add(n0);
        subset.add(n1);

        DFAState d0 = new DFAState("D0", subset, true, false);
        dfa.states.add(d0);

        ArrayList<NFAState> search = new ArrayList<>();
        search.add(n1);
        search.add(n0);

        DFAState result = dfa.checkDFAStatePresent(search);

        assertEquals(d0, result);
    }
    @Test
     public void testCheckDFAStatePresentEmptyDFA() {

          DFA dfa = new DFA();

          ArrayList<NFAState> subset = new ArrayList<>();

          assertNull(dfa.checkDFAStatePresent(subset));
     }

     @Test
     public void testCheckDFAStatePresentEmptySubset() {

          DFA dfa = new DFA();

          DFAState state = new DFAState(
                    "D0",
                    new ArrayList<NFAState>(),
                    true,
                    false);

          dfa.states.add(state);

          DFAState result =
                    dfa.checkDFAStatePresent(new ArrayList<>());

          assertEquals(state, result);
     }

     @Test
     public void testCheckDFAStatePresentDifferentSubsetSize() {

          DFA dfa = new DFA();

          NFAState n0 = new NFAState("S0", true, false);
          NFAState n1 = new NFAState("S1", false, false);

          ArrayList<NFAState> subset = new ArrayList<>();
          subset.add(n0);

          dfa.states.add(new DFAState(
                    "D0",
                    subset,
                    true,
                    false));

          ArrayList<NFAState> search = new ArrayList<>();
          search.add(n0);
          search.add(n1);

          assertNull(dfa.checkDFAStatePresent(search));
     }

     @Test
     public void testTransitionFromAcceptState() {

          DFA dfa = new DFA();

          DFAState s0 = new DFAState(
                    "S0",
                    new ArrayList<NFAState>(),
                    true,
                    true);

          DFAState s1 = new DFAState(
                    "S1",
                    new ArrayList<NFAState>(),
                    false,
                    false);

          s0.addTransition('a', s1);

          assertEquals(s1,
                    dfa.transitionFuntion(s0, 'a'));
     }

     @Test
     public void testTransitionMultipleSymbols() {

          DFA dfa = new DFA();

          DFAState s0 = new DFAState(
                    "S0",
                    new ArrayList<NFAState>(),
                    true,
                    false);

          DFAState s1 = new DFAState(
                    "S1",
                    new ArrayList<NFAState>(),
                    false,
                    false);

          DFAState s2 = new DFAState(
                    "S2",
                    new ArrayList<NFAState>(),
                    false,
                    true);

          s0.addTransition('a', s1);
          s0.addTransition('b', s2);

          assertEquals(s1,
                    dfa.transitionFuntion(s0, 'a'));

          assertEquals(s2,
                    dfa.transitionFuntion(s0, 'b'));
     }

     @Test
     public void testConstructorInitializesLists() {

          DFA dfa = new DFA();

          assertNotNull(dfa.states);
          assertNotNull(dfa.symbols);
     }
}