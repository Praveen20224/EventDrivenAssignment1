import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class ConvertToDFA_Test {

    //-------------------------------
    // move()
    //-------------------------------

    @Test
    public void testMoveSingleTransition() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, true);

        nfa.states.add(s0);
        nfa.states.add(s1);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);

        s0.addTransition('a', dest);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        ArrayList<NFAState> input = new ArrayList<>();
        input.add(s0);

        ArrayList<NFAState> result = converter.move(input, 'a');

        assertEquals(1, result.size());
        assertTrue(result.contains(s1));
    }

    @Test
    public void testMoveNoTransition() {

        NFA nfa = new NFA(new ArrayList<>());

        NFAState s0 = new NFAState("S0", true, false);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        ArrayList<NFAState> input = new ArrayList<>();
        input.add(s0);

        ArrayList<NFAState> result = converter.move(input, 'a');

        assertTrue(result.isEmpty());
    }

    @Test
    public void testMoveMultipleStates() {

        NFA nfa = new NFA(new ArrayList<>());

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, false);
        NFAState s2 = new NFAState("S2", false, true);

        ArrayList<NFAState> d1 = new ArrayList<>();
        d1.add(s2);

        ArrayList<NFAState> d2 = new ArrayList<>();
        d2.add(s2);

        s0.addTransition('a', d1);
        s1.addTransition('a', d2);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        ArrayList<NFAState> input = new ArrayList<>();
        input.add(s0);
        input.add(s1);

        ArrayList<NFAState> result = converter.move(input, 'a');

        assertEquals(1, result.size());
        assertTrue(result.contains(s2));
    }

    @Test
    public void testMoveEmptyInput() {

        ConvertToDFA converter =
                new ConvertToDFA(new NFA(new ArrayList<>()));

        ArrayList<NFAState> result =
                converter.move(new ArrayList<>(), 'a');

        assertTrue(result.isEmpty());
    }

    @Test
    public void testMoveRemovesDuplicates() {

        ConvertToDFA converter =
                new ConvertToDFA(new NFA(new ArrayList<>()));

        NFAState s0 = new NFAState("S0", false, false);
        NFAState s1 = new NFAState("S1", false, false);

        ArrayList<NFAState> d1 = new ArrayList<>();
        d1.add(s1);

        ArrayList<NFAState> d2 = new ArrayList<>();
        d2.add(s1);

        s0.addTransition('a', d1);
        s0.addTransition('a', d2);

        ArrayList<NFAState> input = new ArrayList<>();
        input.add(s0);

        ArrayList<NFAState> result = converter.move(input, 'a');

        assertEquals(1, result.size());
        assertTrue(result.contains(s1));
    }

    //-------------------------------
    // checkFinalState()
    //-------------------------------

    @Test
    public void testCheckFinalStateTrue() {

        NFA nfa = new NFA(new ArrayList<>());

        NFAState s0 = new NFAState("S0", false, true);

        nfa.states.add(s0);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        ArrayList<NFAState> subset = new ArrayList<>();
        subset.add(s0);

        assertTrue(converter.checkFinalState(subset));
    }

    @Test
    public void testCheckFinalStateFalse() {

        NFA nfa = new NFA(new ArrayList<>());

        NFAState s0 = new NFAState("S0", false, false);

        nfa.states.add(s0);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        ArrayList<NFAState> subset = new ArrayList<>();
        subset.add(s0);

        assertFalse(converter.checkFinalState(subset));
    }

    @Test
    public void testCheckFinalStateEmptySubset() {

        NFA nfa = new NFA(new ArrayList<>());

        ConvertToDFA converter = new ConvertToDFA(nfa);

        assertFalse(converter.checkFinalState(new ArrayList<>()));
    }

    //-------------------------------
    // buildDFA()
    //-------------------------------

    @Test
    public void testBuildDFACreatesStartAndDeadState() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);

        nfa.states.add(s0);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        DFA dfa = converter.buildDFA();

        assertEquals(2, dfa.states.size());

        assertTrue(dfa.states.get(0).start);

        DFAState dead = dfa.states.get(1);

        assertFalse(dead.start);
        assertFalse(dead.accept);
        assertTrue(dead.subset.isEmpty());
    }
        @Test
    public void testBuildDFAAcceptingStartState() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, true);

        nfa.states.add(s0);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        DFA dfa = converter.buildDFA();

        assertTrue(dfa.states.get(0).accept);
    }

    @Test
    public void testBuildDFANonAcceptingStartState() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);

        nfa.states.add(s0);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        DFA dfa = converter.buildDFA();

        assertFalse(dfa.states.get(0).accept);
    }

    @Test
    public void testBuildDFAOneTransition() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, true);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);

        s0.addTransition('a', dest);

        nfa.states.add(s0);
        nfa.states.add(s1);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        DFA dfa = converter.buildDFA();

        // {S0}, {S1}, {}
        assertEquals(3, dfa.states.size());

        boolean foundAccept = false;

        for (DFAState s : dfa.states) {
            if (s.accept)
                foundAccept = true;
        }

        assertTrue(foundAccept);
    }

    @Test
    public void testBuildDFADeadStateCreated() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);

        nfa.states.add(s0);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        DFA dfa = converter.buildDFA();

        assertEquals(2, dfa.states.size());

        DFAState dead = dfa.states.get(1);

        assertFalse(dead.start);
        assertFalse(dead.accept);
        assertTrue(dead.subset.isEmpty());
    }

    @Test
    public void testBuildDFASymbolsCopied() {

        ArrayList<Character> symbols = new ArrayList<>();

        symbols.add('a');
        symbols.add('b');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);

        nfa.states.add(s0);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        DFA dfa = converter.buildDFA();

        assertEquals(2, dfa.symbols.size());

        assertTrue(dfa.symbols.contains('a'));
        assertTrue(dfa.symbols.contains('b'));

        assertNotSame(symbols, dfa.symbols);
        assertEquals(symbols, dfa.symbols);
    }

    @Test
    public void testBuildDFATransitionsCreated() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, true);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);

        s0.addTransition('a', dest);

        nfa.states.add(s0);
        nfa.states.add(s1);

        DFA dfa = new ConvertToDFA(nfa).buildDFA();

        DFAState start = dfa.states.get(0);

        DFAState next = dfa.transitionFuntion(start, 'a');

        assertNotNull(next);
        assertTrue(next.accept);
    }
     
    @Test
     public void testBuildDFACycle() {

          ArrayList<Character> symbols = new ArrayList<>();
          symbols.add('a');

          NFA nfa = new NFA(symbols);

          NFAState s0 = new NFAState("S0", true, false);

          ArrayList<NFAState> loop = new ArrayList<>();
          loop.add(s0);

          s0.addTransition('a', loop);

          nfa.states.add(s0);

          ConvertToDFA converter = new ConvertToDFA(nfa);

          DFA dfa = converter.buildDFA();

          assertEquals(1, dfa.states.size());

          DFAState start = dfa.states.get(0);

          assertEquals(start, dfa.transitionFuntion(start, 'a'));
     }

    @Test
    public void testBuildDFAMultipleAcceptStates() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, true);
        NFAState s2 = new NFAState("S2", false, true);

        ArrayList<NFAState> dest = new ArrayList<>();
        dest.add(s1);
        dest.add(s2);

        s0.addTransition('a', dest);

        nfa.states.add(s0);
        nfa.states.add(s1);
        nfa.states.add(s2);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        DFA dfa = converter.buildDFA();

        assertEquals(3, dfa.states.size());

        int accepts = 0;

        for (DFAState s : dfa.states) {
            if (s.accept)
                accepts++;
        }

        assertEquals(1, accepts);
    }

    @Test
    public void testBuildDFAEmptyAlphabet() {

        NFA nfa = new NFA(new ArrayList<>());

        NFAState s0 = new NFAState("S0", true, false);

        nfa.states.add(s0);

        ConvertToDFA converter = new ConvertToDFA(nfa);

        DFA dfa = converter.buildDFA();

        assertEquals(1, dfa.states.size());
        assertEquals(0, dfa.symbols.size());
    }

    @Test
    public void testBuildDFAMultipleSymbols() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');
        symbols.add('b');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);
        NFAState s1 = new NFAState("S1", false, true);

        ArrayList<NFAState> aDest = new ArrayList<>();
        aDest.add(s1);

        s0.addTransition('a', aDest);

        nfa.states.add(s0);
        nfa.states.add(s1);

        DFA dfa = new ConvertToDFA(nfa).buildDFA();

        assertEquals(2, dfa.symbols.size());

        DFAState start = dfa.states.get(0);

        assertNotNull(dfa.transitionFuntion(start, 'a'));
        assertNotNull(dfa.transitionFuntion(start, 'b'));
    }

    @Test
    public void testBuildDFAStartStateSubset() {

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);

        nfa.states.add(s0);

        DFA dfa = new ConvertToDFA(nfa).buildDFA();

        assertEquals(1, dfa.states.get(0).subset.size());
        assertTrue(dfa.states.get(0).subset.contains(s0));
    }

    @Test
    public void testBuildDFAPreservesAlphabetOrder() {

        ArrayList<Character> symbols = new ArrayList<>();

        symbols.add('x');
        symbols.add('y');
        symbols.add('z');

        NFA nfa = new NFA(symbols);

        NFAState s0 = new NFAState("S0", true, false);

        nfa.states.add(s0);

        DFA dfa = new ConvertToDFA(nfa).buildDFA();

        assertEquals(symbols, dfa.symbols);
    }
}