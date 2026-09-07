import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class Transition_Test {

    //-----------------------------
    // Constructor
    //-----------------------------

    @Test
    public void testConstructorStoresFromState() {

        State from = new State("A", false);
        State to = new State("B", true);

        Transition t = new Transition(from, to, 'a');

        assertEquals(from, t.from);
    }

    @Test
    public void testConstructorStoresToState() {

        State from = new State("A", false);
        State to = new State("B", true);

        Transition t = new Transition(from, to, 'a');

        assertEquals(to, t.to);
    }

    @Test
    public void testConstructorStoresSymbol() {

        State from = new State("A", false);
        State to = new State("B", true);

        Transition t = new Transition(from, to, 'x');

        assertEquals('x', t.symbol);
    }

    @Test
    public void testSelfLoopTransition() {

        State s = new State("A", false);

        Transition t = new Transition(s, s, 'a');

        assertEquals(s, t.from);
        assertEquals(s, t.to);
    }

    @Test
    public void testDifferentSymbols() {

        State s0 = new State("A", false);
        State s1 = new State("B", false);

        Transition t1 = new Transition(s0, s1, 'a');
        Transition t2 = new Transition(s0, s1, 'b');

        assertEquals('a', t1.symbol);
        assertEquals('b', t2.symbol);
    }

    @Test
    public void testAcceptStateDestination() {

        State from = new State("A", false);
        State to = new State("B", true);

        Transition t = new Transition(from, to, 'c');

        assertTrue(t.to.accept);
    }

    @Test
    public void testStartAndDestinationIds() {

        State from = new State("Start", false);
        State to = new State("End", true);

        Transition t = new Transition(from, to, 'z');

        assertEquals("Start", t.from.id);
        assertEquals("End", t.to.id);
    }

    @Test
    public void testConstructorStoresExactReferences() {

        State from = new State("S0", false);
        State to = new State("S1", false);

        Transition t = new Transition(from, to, 'a');

        assertSame(from, t.from);
        assertSame(to, t.to);
    }
    public void testPrintTransition() {

        State from = new State("S0", false);
        State to = new State("S1", true);

        Transition t = new Transition(from, to, 'a');

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;

        System.setOut(new PrintStream(out));

        t.printTransition();

        System.setOut(original);

        String expected =
                "Transition from state S0 to state S1 on symbol 'a'"
                + System.lineSeparator();

        assertEquals(expected, out.toString());
    }
}