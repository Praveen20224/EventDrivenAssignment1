import static org.junit.Assert.*;
import org.junit.Test;

public class RegexParser_Test {

    //-----------------------------
    // Constructor
    //-----------------------------

    @Test
    public void testConstructorInitializesFields() {

        RegexParser parser = new RegexParser("ab");

        assertEquals("ab", parser.input_regex);
        assertEquals("", parser.processed_regex);
        assertEquals("", parser.final_regex);
        assertEquals(2, parser.length);
    }

    //-----------------------------
    // isValid()
    //-----------------------------

    @Test
    public void testEmptyRegexInvalid() {

        RegexParser parser = new RegexParser("");

        assertFalse(parser.isValid());
    }

    @Test
    public void testSingleCharacterValid() {

        RegexParser parser = new RegexParser("a");

        assertTrue(parser.isValid());
    }

    @Test
    public void testUnionValid() {

        RegexParser parser = new RegexParser("a|b");

        assertTrue(parser.isValid());
    }

    @Test
    public void testConcatenationValid() {

        RegexParser parser = new RegexParser("ab");

        assertTrue(parser.isValid());
    }

    @Test
    public void testStarValid() {

        RegexParser parser = new RegexParser("a*");

        assertTrue(parser.isValid());
    }

    @Test
    public void testPlusValid() {

        RegexParser parser = new RegexParser("a+");

        assertTrue(parser.isValid());
    }

    @Test
    public void testParenthesesValid() {

        RegexParser parser = new RegexParser("(a|b)");

        assertTrue(parser.isValid());
    }

    @Test
    public void testStartsWithStarInvalid() {

        RegexParser parser = new RegexParser("*a");

        assertFalse(parser.isValid());
    }

    @Test
    public void testStartsWithPlusInvalid() {

        RegexParser parser = new RegexParser("+a");

        assertFalse(parser.isValid());
    }
        @Test
    public void testStartsWithClosingParenthesisInvalid() {

        RegexParser parser = new RegexParser(")ab");

        assertFalse(parser.isValid());
    }

    @Test
    public void testStartsWithUnionInvalid() {

        RegexParser parser = new RegexParser("|ab");

        assertFalse(parser.isValid());
    }

    @Test
    public void testEndsWithOpeningParenthesisInvalid() {

        RegexParser parser = new RegexParser("ab(");

        assertFalse(parser.isValid());
    }

    @Test
    public void testEndsWithUnionInvalid() {

        RegexParser parser = new RegexParser("ab|");

        assertFalse(parser.isValid());
    }

    @Test
    public void testEmptyParenthesesInvalid() {

        RegexParser parser = new RegexParser("()");

        assertFalse(parser.isValid());
    }

    @Test
    public void testOpenParenthesisFollowedByUnionInvalid() {

        RegexParser parser = new RegexParser("(|a)");

        assertFalse(parser.isValid());
    }

    @Test
    public void testOpenParenthesisFollowedByStarInvalid() {

        RegexParser parser = new RegexParser("(*a)");

        assertFalse(parser.isValid());
    }

    @Test
    public void testOpenParenthesisFollowedByPlusInvalid() {

        RegexParser parser = new RegexParser("(+a)");

        assertFalse(parser.isValid());
    }

    @Test
    public void testDoubleUnionInvalid() {

        RegexParser parser = new RegexParser("a||b");

        assertFalse(parser.isValid());
    }

    @Test
    public void testDoubleStarInvalid() {

        RegexParser parser = new RegexParser("a**");

        assertFalse(parser.isValid());
    }

    @Test
    public void testDoublePlusInvalid() {

        RegexParser parser = new RegexParser("a++");

        assertFalse(parser.isValid());
    }

    @Test
    public void testUnbalancedOpeningParenthesisInvalid() {

        RegexParser parser = new RegexParser("(ab");

        assertFalse(parser.isValid());
    }

    @Test
    public void testUnbalancedClosingParenthesisInvalid() {

        RegexParser parser = new RegexParser("ab)");

        assertFalse(parser.isValid());
    }

    @Test
    public void testNestedParenthesesValid() {

        RegexParser parser = new RegexParser("((a|b)c)");

        assertTrue(parser.isValid());
    }

    @Test
    public void testComplexExpressionValid() {

        RegexParser parser = new RegexParser("(a|b)*c+");

        assertTrue(parser.isValid());
    }
        //-----------------------------
    // addConcatenationOperator()
    //-----------------------------

    @Test
    public void testAddConcatenationBetweenLetters() {

        RegexParser parser = new RegexParser("ab");

        assertEquals("a.b", parser.addConcatenationOperator());
    }

    @Test
    public void testAddConcatenationLetterAndParenthesis() {

        RegexParser parser = new RegexParser("a(b)");

        assertEquals("a.(b)", parser.addConcatenationOperator());
    }

    @Test
    public void testAddConcatenationAfterClosingParenthesis() {

        RegexParser parser = new RegexParser("(a)b");

        assertEquals("(a).b", parser.addConcatenationOperator());
    }

    @Test
    public void testAddConcatenationAfterStar() {

        RegexParser parser = new RegexParser("a*b");

        assertEquals("a*.b", parser.addConcatenationOperator());
    }

    @Test
    public void testAddConcatenationAfterPlus() {

        RegexParser parser = new RegexParser("a+b");

        assertEquals("a+.b", parser.addConcatenationOperator());
    }

    @Test
    public void testAddConcatenationWithNestedParentheses() {

        RegexParser parser = new RegexParser("(a)(b)");

        assertEquals("(a).(b)", parser.addConcatenationOperator());
    }

    @Test
    public void testAddConcatenationWithSpaces() {

        RegexParser parser = new RegexParser("a b");

        assertEquals("a. .b", parser.addConcatenationOperator());
    }

    //-----------------------------
    // checkPrecedence()
    //-----------------------------

    @Test
    public void testStarPrecedence() {

        RegexParser parser = new RegexParser("a");

        assertEquals(3, parser.checkPrecedence('*'));
    }

    @Test
    public void testPlusPrecedence() {

        RegexParser parser = new RegexParser("a");

        assertEquals(3, parser.checkPrecedence('+'));
    }

    @Test
    public void testConcatenationPrecedence() {

        RegexParser parser = new RegexParser("a");

        assertEquals(2, parser.checkPrecedence('.'));
    }

    @Test
    public void testUnionPrecedence() {

        RegexParser parser = new RegexParser("a");

        assertEquals(1, parser.checkPrecedence('|'));
    }

    @Test
    public void testUnknownOperatorPrecedence() {

        RegexParser parser = new RegexParser("a");

        assertEquals(0, parser.checkPrecedence('x'));
    }
    @Test
     public void testLetterFollowedBySpace() {
     RegexParser parser = new RegexParser("a ");
     assertEquals("a. ", parser.addConcatenationOperator());
     }

     @Test
     public void testSpaceFollowedByLetter() {
     RegexParser parser = new RegexParser(" a");
     assertEquals(" .a", parser.addConcatenationOperator());
     }

     @Test
     public void testStarFollowedByParenthesis() {
     RegexParser parser = new RegexParser("a*(b)");
     assertEquals("a*.(b)", parser.addConcatenationOperator());
     }

     @Test
     public void testPlusFollowedByParenthesis() {
     RegexParser parser = new RegexParser("a+(b)");
     assertEquals("a+.(b)", parser.addConcatenationOperator());
     }

     @Test
     public void testPostfixOnlyConcatenation() {
     RegexParser parser = new RegexParser("");
     assertEquals("ab.", parser.infixToPostfix("a.b"));
     }

     @Test
     public void testPostfixUnionWithConcat() {
     RegexParser parser = new RegexParser("");
     assertEquals("abc.|", parser.infixToPostfix("a|b.c"));
     }

     @Test
     public void testPostfixNestedParentheses() {
     RegexParser parser = new RegexParser("");
     assertEquals("ab|cd|.", parser.infixToPostfix("(a|b).(c|d)"));
     }
     @Test
     public void testSingleOpenParenthesisInvalid() {
     RegexParser parser = new RegexParser("(");
     assertFalse(parser.isValid());
     }

     @Test
     public void testSingleCloseParenthesisInvalid() {
     RegexParser parser = new RegexParser(")");
     assertFalse(parser.isValid());
     }

     @Test
     public void testOnlyUnionInvalid() {
     RegexParser parser = new RegexParser("|");
     assertFalse(parser.isValid());
     }
}