import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class BuildENFA_Test {

     //-----------------------------
     // Constructor
     //-----------------------------

     @Test
     public void testConstructorInitializesRegex() {

          BuildENFA builder = new BuildENFA("a");

          assertEquals("a", builder.regex);
          assertEquals(0, builder.stateCounter);
     }

     @Test
     public void testConstructorInitializesSymbolList() {

          BuildENFA builder = new BuildENFA("a");

          assertEquals(1, builder.allSymbols.size());
          assertTrue(builder.allSymbols.contains(BuildENFA.ep));
     }

     @Test
     public void testConstructorInitializesStateList() {

          BuildENFA builder = new BuildENFA("a");

          assertTrue(builder.allStates.isEmpty());
     }

     //-----------------------------
     // Single Symbol
     //-----------------------------

     @Test
     public void testBuildSingleCharacter() {

          BuildENFA builder = new BuildENFA("a");

          Fragment fragment = builder.buildEnfa();

          assertNotNull(fragment);
          assertEquals(2, builder.allStates.size());
     }

     @Test
     public void testSingleCharacterStartEndStates() {

          BuildENFA builder = new BuildENFA("a");

          Fragment fragment = builder.buildEnfa();

          assertFalse(fragment.start.accept);
          assertTrue(fragment.end.accept);
     }

     @Test
     public void testSingleCharacterTransition() {

          BuildENFA builder = new BuildENFA("a");

          Fragment fragment = builder.buildEnfa();

          assertEquals(1, fragment.start.transitions.size());

          Transition t = fragment.start.transitions.get(0);

          assertEquals('a', t.symbol);
          assertSame(fragment.end, t.to);
     }

     //-----------------------------
     // Getters
     //-----------------------------

     @Test
     public void testGetAllStates() {

          BuildENFA builder = new BuildENFA("a");

          builder.buildEnfa();

          ArrayList<State> states = builder.getAllStates();

          assertEquals(2, states.size());
     }

     @Test
     public void testGetAllSymbols() {

          BuildENFA builder = new BuildENFA("ab.");

          builder.buildEnfa();

          ArrayList<Character> symbols = builder.getallSymbols();

          assertTrue(symbols.contains(BuildENFA.ep));
          assertTrue(symbols.contains('a'));
          assertTrue(symbols.contains('b'));
     }

     @Test
     public void testStateCounterIncrement() {

          BuildENFA builder = new BuildENFA("a");

          builder.buildEnfa();

          assertEquals(2, builder.stateCounter);
     }

     @Test
     public void testSingleCharacterAddsSymbolOnlyOnce() {

          BuildENFA builder = new BuildENFA("aaa..");

          builder.buildEnfa();

          int count = 0;

          for (char c : builder.allSymbols) {
               if (c == 'a')
                    count++;
          }

          assertEquals(1, count);
     }
     
    //-----------------------------
    // Concatenation (.)
    //-----------------------------

    @Test
    public void testConcatenationCreatesFourStates() {

        BuildENFA builder = new BuildENFA("ab.");

        Fragment fragment = builder.buildEnfa();

        assertEquals(4, builder.allStates.size());
        assertNotNull(fragment.start);
        assertNotNull(fragment.end);
    }

    @Test
    public void testConcatenationAddsEpsilonTransition() {

        BuildENFA builder = new BuildENFA("ab.");

        builder.buildEnfa();

        boolean found = false;

        for (State s : builder.allStates) {
            for (Transition t : s.transitions) {
                if (t.symbol == BuildENFA.ep) {
                    found = true;
                }
            }
        }

        assertTrue(found);
    }

    @Test
    public void testConcatenationOnlyOneAcceptState() {

        BuildENFA builder = new BuildENFA("ab.");

        builder.buildEnfa();

        int accepts = 0;

        for (State s : builder.allStates) {
            if (s.accept)
                accepts++;
        }

        assertEquals(1, accepts);
    }

    //-----------------------------
    // Union (|)
    //-----------------------------

    @Test
    public void testUnionCreatesSixStates() {

        BuildENFA builder = new BuildENFA("ab|");

        Fragment fragment = builder.buildEnfa();

        assertEquals(6, builder.allStates.size());
        assertNotNull(fragment.start);
        assertNotNull(fragment.end);
    }

    @Test
    public void testUnionNewStartHasTwoEpsilonTransitions() {

        BuildENFA builder = new BuildENFA("ab|");

        Fragment fragment = builder.buildEnfa();

        int eps = 0;

        for (Transition t : fragment.start.transitions) {
            if (t.symbol == BuildENFA.ep)
                eps++;
        }

        assertEquals(2, eps);
    }

    @Test
    public void testUnionHasSingleAcceptState() {

        BuildENFA builder = new BuildENFA("ab|");

        builder.buildEnfa();

        int accepts = 0;

        for (State s : builder.allStates) {
            if (s.accept)
                accepts++;
        }

        assertEquals(1, accepts);
    }

    @Test
    public void testUnionAddsSymbolOnce() {

        BuildENFA builder = new BuildENFA("ab|");

        builder.buildEnfa();

        assertTrue(builder.allSymbols.contains('a'));
        assertTrue(builder.allSymbols.contains('b'));
        assertEquals(3, builder.allSymbols.size()); // ε,a,b
    }
        //-----------------------------
    // Kleene Star (*)
    //-----------------------------

    @Test
    public void testStarCreatesFourStates() {

        BuildENFA builder = new BuildENFA("a*");

        Fragment fragment = builder.buildEnfa();

        assertEquals(4, builder.allStates.size());
        assertNotNull(fragment.start);
        assertNotNull(fragment.end);
    }

    @Test
    public void testStarStartHasTwoEpsilonTransitions() {

        BuildENFA builder = new BuildENFA("a*");

        Fragment fragment = builder.buildEnfa();

        int epsilonCount = 0;

        for (Transition t : fragment.start.transitions) {
            if (t.symbol == BuildENFA.ep) {
                epsilonCount++;
            }
        }

        assertEquals(2, epsilonCount);
    }

    @Test
    public void testStarHasLoopTransition() {

        BuildENFA builder = new BuildENFA("a*");

        builder.buildEnfa();

        boolean foundLoop = false;

        for (State s : builder.allStates) {
            for (Transition t : s.transitions) {
                if (t.symbol == BuildENFA.ep && t.to != null) {
                    foundLoop = true;
                }
            }
        }

        assertTrue(foundLoop);
    }

    @Test
    public void testStarSingleAcceptState() {

        BuildENFA builder = new BuildENFA("a*");

        builder.buildEnfa();

        int accepts = 0;

        for (State s : builder.allStates) {
            if (s.accept)
                accepts++;
        }

        assertEquals(1, accepts);
    }

    //-----------------------------
    // Plus (+)
    //-----------------------------

    @Test
    public void testPlusCreatesFourStates() {

        BuildENFA builder = new BuildENFA("a+");

        Fragment fragment = builder.buildEnfa();

        assertEquals(4, builder.allStates.size());
        assertNotNull(fragment.start);
        assertNotNull(fragment.end);
    }

    @Test
    public void testPlusStartHasOneEpsilonTransition() {

        BuildENFA builder = new BuildENFA("a+");

        Fragment fragment = builder.buildEnfa();

        int epsilonCount = 0;

        for (Transition t : fragment.start.transitions) {
            if (t.symbol == BuildENFA.ep) {
                epsilonCount++;
            }
        }

        assertEquals(1, epsilonCount);
    }

    @Test
    public void testPlusSingleAcceptState() {

        BuildENFA builder = new BuildENFA("a+");

        builder.buildEnfa();

        int accepts = 0;

        for (State s : builder.allStates) {
            if (s.accept)
                accepts++;
        }

        assertEquals(1, accepts);
    }

    @Test
    public void testPlusContainsLoopTransition() {

        BuildENFA builder = new BuildENFA("a+");

        builder.buildEnfa();

        boolean foundLoop = false;

        for (State s : builder.allStates) {
            for (Transition t : s.transitions) {
                if (t.symbol == BuildENFA.ep) {
                    foundLoop = true;
                }
            }
        }

        assertTrue(foundLoop);
    }
        //-----------------------------
    // getDestinations()
    //-----------------------------

    @Test
    public void testGetDestinationsSingleDestination() {

        BuildENFA builder = new BuildENFA("a");

        Fragment fragment = builder.buildEnfa();

        String result = builder.getDestinations(fragment.start, 'a');

        assertEquals(fragment.end.id, result);
    }

    @Test
    public void testGetDestinationsNoTransition() {

        BuildENFA builder = new BuildENFA("a");

        Fragment fragment = builder.buildEnfa();

        assertEquals("", builder.getDestinations(fragment.start, 'b'));
    }

    //-----------------------------
    // Symbols
    //-----------------------------

    @Test
    public void testSpaceIsValidSymbol() {

        BuildENFA builder = new BuildENFA(" ");

        builder.buildEnfa();

        assertTrue(builder.allSymbols.contains(' '));
    }

    @Test
    public void testDuplicateSymbolsStoredOnce() {

        BuildENFA builder = new BuildENFA("aaa..");

        builder.buildEnfa();

        int count = 0;

        for (char c : builder.allSymbols) {
            if (c == 'a')
                count++;
        }

        assertEquals(1, count);
    }



    @Test
    public void testPrintENFATableDoesNotThrowException() {

        BuildENFA builder = new BuildENFA("ab.");

        Fragment fragment = builder.buildEnfa();

        builder.printENFATable(fragment);
    }


    @Test
    public void testComplexExpressionBuildsSuccessfully() {

        BuildENFA builder = new BuildENFA("ab|c.");

        Fragment fragment = builder.buildEnfa();

        assertNotNull(fragment);
        assertNotNull(fragment.start);
        assertNotNull(fragment.end);
    }

    @Test
    public void testReturnedFragmentMatchesStateList() {

        BuildENFA builder = new BuildENFA("a*");

        Fragment fragment = builder.buildEnfa();

        assertTrue(builder.allStates.contains(fragment.start));
        assertTrue(builder.allStates.contains(fragment.end));
    }
}