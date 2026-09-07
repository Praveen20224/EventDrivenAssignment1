import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class ConvertToNFA_Test {

    //-----------------------------------
    // epsilonClosure()
    //-----------------------------------

    @Test
    public void testEpsilonClosureSingleState() {

        State s0 = new State("S0", false);

        ArrayList<State> closure = ConvertToNFA.epsilonClosure(s0);

        assertEquals(1, closure.size());
        assertTrue(closure.contains(s0));
    }

    @Test
    public void testEpsilonClosureOneEpsilonTransition() {

        State s0 = new State("S0", false);
        State s1 = new State("S1", false);

        s0.addTransition(s1, ConvertToNFA.ep);

        ArrayList<State> closure = ConvertToNFA.epsilonClosure(s0);

        assertEquals(2, closure.size());
        assertTrue(closure.contains(s0));
        assertTrue(closure.contains(s1));
    }

    @Test
    public void testEpsilonClosureChain() {

        State s0 = new State("S0", false);
        State s1 = new State("S1", false);
        State s2 = new State("S2", false);

        s0.addTransition(s1, ConvertToNFA.ep);
        s1.addTransition(s2, ConvertToNFA.ep);

        ArrayList<State> closure = ConvertToNFA.epsilonClosure(s0);

        assertEquals(3, closure.size());
        assertTrue(closure.contains(s0));
        assertTrue(closure.contains(s1));
        assertTrue(closure.contains(s2));
    }

    @Test
    public void testEpsilonClosureIgnoresNormalTransitions() {

        State s0 = new State("S0", false);
        State s1 = new State("S1", false);

        s0.addTransition(s1, 'a');

        ArrayList<State> closure = ConvertToNFA.epsilonClosure(s0);

        assertEquals(1, closure.size());
        assertTrue(closure.contains(s0));
        assertFalse(closure.contains(s1));
    }

    @Test
    public void testEpsilonClosureCycle() {

        State s0 = new State("S0", false);
        State s1 = new State("S1", false);

        s0.addTransition(s1, ConvertToNFA.ep);
        s1.addTransition(s0, ConvertToNFA.ep);

        ArrayList<State> closure = ConvertToNFA.epsilonClosure(s0);

        assertEquals(2, closure.size());
        assertTrue(closure.contains(s0));
        assertTrue(closure.contains(s1));
    }

    //-----------------------------------
    // move()
    //-----------------------------------

    @Test
    public void testMoveSingleTransition() {

        Fragment fragment =
                new Fragment(new State("A", false),
                             new State("B", true));

        ConvertToNFA converter = new ConvertToNFA(fragment);

        State s0 = new State("S0", false);
        State s1 = new State("S1", false);

        s0.addTransition(s1, 'a');

        ArrayList<State> input = new ArrayList<>();
        input.add(s0);

        ArrayList<State> result = converter.move(input, 'a');

        assertEquals(1, result.size());
        assertTrue(result.contains(s1));
    }

    @Test
    public void testMoveNoTransition() {

        Fragment fragment =
                new Fragment(new State("A", false),
                             new State("B", true));

        ConvertToNFA converter = new ConvertToNFA(fragment);

        State s0 = new State("S0", false);

        ArrayList<State> input = new ArrayList<>();
        input.add(s0);

        ArrayList<State> result = converter.move(input, 'a');

        assertTrue(result.isEmpty());
    }

    @Test
    public void testMoveMultipleStates() {

        Fragment fragment =
                new Fragment(new State("A", false),
                             new State("B", true));

        ConvertToNFA converter = new ConvertToNFA(fragment);

        State s0 = new State("S0", false);
        State s1 = new State("S1", false);
        State s2 = new State("S2", false);

        s0.addTransition(s2, 'a');
        s1.addTransition(s2, 'a');

        ArrayList<State> input = new ArrayList<>();
        input.add(s0);
        input.add(s1);

        ArrayList<State> result = converter.move(input, 'a');

        assertEquals(1, result.size());
        assertTrue(result.contains(s2));
    }

    @Test
    public void testMoveEmptyInput() {

        Fragment fragment =
                new Fragment(new State("A", false),
                             new State("B", true));

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> result =
                converter.move(new ArrayList<>(), 'a');

        assertTrue(result.isEmpty());
    }

    @Test
    public void testMoveRemovesDuplicates() {

        Fragment fragment =
                new Fragment(new State("A", false),
                             new State("B", true));

        ConvertToNFA converter = new ConvertToNFA(fragment);

        State s0 = new State("S0", false);
        State s1 = new State("S1", false);

        s0.addTransition(s1, 'a');
        s0.addTransition(s1, 'a');

        ArrayList<State> input = new ArrayList<>();
        input.add(s0);

        ArrayList<State> result = converter.move(input, 'a');

        assertEquals(1, result.size());
        assertTrue(result.contains(s1));
    }

    //-----------------------------------
    // buildNFA()
    //-----------------------------------
        @Test
    public void testBuildNFACreatesCorrectNumberOfStates() {

        State s0 = new State("A", false);
        State s1 = new State("B", true);

        Fragment fragment = new Fragment(s0, s1);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> allStates = new ArrayList<>();
        allStates.add(s0);
        allStates.add(s1);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(allStates, symbols);

        assertEquals(2, nfa.states.size());
    }

    @Test
    public void testBuildNFARemovesEpsilonFromAlphabet() {

        State s0 = new State("A", false);

        Fragment fragment = new Fragment(s0, s0);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');
        symbols.add(ConvertToNFA.ep);

        NFA nfa = converter.buildNFA(states, symbols);

        assertEquals(1, nfa.symbols.size());
        assertTrue(nfa.symbols.contains('a'));
        assertFalse(nfa.symbols.contains(ConvertToNFA.ep));
    }

    @Test
    public void testBuildNFAStartStateMarked() {

        State s0 = new State("A", false);

        Fragment fragment = new Fragment(s0, s0);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(states, symbols);

        assertTrue(nfa.states.get(0).start);
    }

    @Test
    public void testBuildNFAAcceptStateMarked() {

        State s0 = new State("A", true);

        Fragment fragment = new Fragment(s0, s0);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(states, symbols);

        assertTrue(nfa.states.get(0).accept);
    }

    @Test
    public void testBuildNFANonAcceptState() {

        State s0 = new State("A", false);
        State s1 = new State("B", true);

        Fragment fragment = new Fragment(s0, s1);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);
        states.add(s1);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(states, symbols);

        assertFalse(nfa.states.get(0).accept);
        assertTrue(nfa.states.get(1).accept);
    }

    @Test
    public void testBuildNFATransitionCreated() {

        State s0 = new State("A", false);
        State s1 = new State("B", true);

        s0.addTransition(s1, 'a');

        Fragment fragment = new Fragment(s0, s1);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);
        states.add(s1);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(states, symbols);

        assertEquals(2, nfa.states.size());

        assertEquals(1, nfa.states.get(0).transitions.size());

        NFATransition t = nfa.states.get(0).transitions.get(0);

        assertEquals('a', t.symbol);

        assertEquals(1, t.to.size());

        assertEquals("S1", t.to.get(0).id);
    }
        @Test
    public void testBuildNFAStartStateThroughEpsilonClosure() {

        State s0 = new State("A", false);
        State s1 = new State("B", true);

        s0.addTransition(s1, ConvertToNFA.ep);

        Fragment fragment = new Fragment(s0, s1);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);
        states.add(s1);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add(ConvertToNFA.ep);

        NFA nfa = converter.buildNFA(states, symbols);

        assertTrue(nfa.states.get(0).start);
        assertTrue(nfa.states.get(1).start);
    }

    @Test
    public void testBuildNFAAcceptStateThroughEpsilonClosure() {

        State s0 = new State("A", false);
        State s1 = new State("B", true);

        s0.addTransition(s1, ConvertToNFA.ep);

        Fragment fragment = new Fragment(s0, s1);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);
        states.add(s1);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(states, symbols);

        assertTrue(nfa.states.get(0).accept);
        assertTrue(nfa.states.get(1).accept);
    }

    @Test
    public void testBuildNFAMultipleSymbols() {

        State s0 = new State("A", false);

        Fragment fragment = new Fragment(s0, s0);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');
        symbols.add('b');
        symbols.add('c');

        NFA nfa = converter.buildNFA(states, symbols);

        assertEquals(3, nfa.symbols.size());

        assertTrue(nfa.symbols.contains('a'));
        assertTrue(nfa.symbols.contains('b'));
        assertTrue(nfa.symbols.contains('c'));
    }

    @Test
    public void testBuildNFACreatesTransitionForEachSymbol() {

        State s0 = new State("A", false);

        Fragment fragment = new Fragment(s0, s0);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');
        symbols.add('b');

        NFA nfa = converter.buildNFA(states, symbols);

        assertEquals(2, nfa.states.get(0).transitions.size());
    }

    @Test
    public void testBuildNFAEmptyAlphabet() {

        State s0 = new State("A", false);

        Fragment fragment = new Fragment(s0, s0);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);

        ArrayList<Character> symbols = new ArrayList<>();

        NFA nfa = converter.buildNFA(states, symbols);

        assertEquals(0, nfa.symbols.size());
    }

    @Test
    public void testBuildNFAEmptyStateList() {

        State s0 = new State("A", false);

        Fragment fragment = new Fragment(s0, s0);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(states, symbols);

        assertEquals(0, nfa.states.size());
    }

    @Test
    public void testBuildNFAStateIdsGenerated() {

        State s0 = new State("A", false);
        State s1 = new State("B", true);

        Fragment fragment = new Fragment(s0, s1);

        ConvertToNFA converter = new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);
        states.add(s1);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(states, symbols);

        assertEquals("S0", nfa.states.get(0).id);
        assertEquals("S1", nfa.states.get(1).id);
    }
        @Test
    public void testMoveHandlesEpsilonSymbol() {

        Fragment fragment =
                new Fragment(new State("A", false),
                             new State("B", true));

        ConvertToNFA converter =
                new ConvertToNFA(fragment);

        State s0 = new State("S0", false);
        State s1 = new State("S1", false);

        s0.addTransition(s1, ConvertToNFA.ep);

        ArrayList<State> input = new ArrayList<>();
        input.add(s0);

        ArrayList<State> result =
                converter.move(input, ConvertToNFA.ep);

        assertEquals(1, result.size());
        assertTrue(result.contains(s1));

        result = converter.move(input, 'a');

        assertTrue(result.isEmpty());
    }

    @Test
    public void testBuildNFATransitionDestinationCorrect() {

        State s0 = new State("A", false);
        State s1 = new State("B", true);

        s0.addTransition(s1, 'a');

        Fragment fragment = new Fragment(s0, s1);

        ConvertToNFA converter =
                new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);
        states.add(s1);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(states, symbols);

        NFATransition t = nfa.states.get(0).transitions.get(0);

        assertEquals(1, t.to.size());
        assertEquals("S1", t.to.get(0).id);
    }

    @Test
    public void testBuildNFAWithUnreachableState() {

        State s0 = new State("A", false);
        State s1 = new State("B", true);
        State s2 = new State("C", false);

        s0.addTransition(s1, 'a');

        Fragment fragment = new Fragment(s0, s1);

        ConvertToNFA converter =
                new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);
        states.add(s1);
        states.add(s2);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');

        NFA nfa = converter.buildNFA(states, symbols);

        assertEquals(3, nfa.states.size());
    }

    @Test
    public void testBuildNFAAlphabetCopied() {

        State s0 = new State("A", false);

        Fragment fragment = new Fragment(s0, s0);

        ConvertToNFA converter =
                new ConvertToNFA(fragment);

        ArrayList<State> states = new ArrayList<>();
        states.add(s0);

        ArrayList<Character> symbols = new ArrayList<>();
        symbols.add('a');
        symbols.add('b');

        NFA nfa = converter.buildNFA(states, symbols);

        assertNotSame(symbols, nfa.symbols);
        assertEquals(symbols, nfa.symbols);
    }

    @Test
    public void testConstructorStoresFragment() {

        State start = new State("Start", false);
        State end = new State("End", true);

        Fragment fragment = new Fragment(start, end);

        ConvertToNFA converter =
                new ConvertToNFA(fragment);

        assertSame(fragment, converter.enfa);
        assertSame(start, converter.enfa.start);
        assertSame(end, converter.enfa.end);
    }

}