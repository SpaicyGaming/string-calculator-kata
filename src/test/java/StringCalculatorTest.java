import me.michelecoco.stringcalculatorkata.StringCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link StringCalculator}
 */
public class StringCalculatorTest {

    private static StringCalculator calculator;

    @BeforeAll
    public static void setup() {
        calculator = new StringCalculator();
    }

    @Test
    public void testAddSimple() {
        assertEquals(0, calculator.add(""));
        assertEquals(1, calculator.add("1"));
        assertEquals(3, calculator.add("1,2"));
    }

    @Test
    public void testAddUnknownAmountOfNumbers() {
        assertEquals(8, calculator.add("1,2,3,1,1"));
        assertEquals(10, calculator.add("1,2,0,3,4"));
    }

    @Test
    public void testAddHandleNewLines() {
        assertEquals(6, calculator.add("1\n2,3"));
        assertEquals(7, calculator.add("1,2,3\n1"));
    }

    @Test
    public void testAddCustomDelimiter() {
        assertEquals(3, calculator.add("//;\n1;2"));
        assertEquals(3, calculator.add("//4\n142"));
        assertEquals(3, calculator.add("//*\n1*2")); // contains regex metacharacter
        assertEquals(3, calculator.add("//+\n1+2"));
    }

    @Test
    public void testAddCustomDelimiterOfAnyLength() {
        assertEquals(6, calculator.add("//[***]\n1[***]2[***]3"));
        assertEquals(6, calculator.add("//++\n1++2++3"));
        assertEquals(6, calculator.add("//ABC\n1ABC2ABC3"));
    }

    @Test
    public void testAddCustomDelimiterWithNewLine() {
        assertEquals(3, calculator.add("//;\n1\n2"));
        assertEquals(8, calculator.add("//+\n1+2\n5"));
    }

    @Test
    public void testAddNegativeNumbers() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> calculator.add("1,4,-1"), "negatives not allowed: -1");
        assertEquals("negatives not allowed: -1", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> calculator.add("1,4,-1,-2"), "negatives not allowed: -1");
        assertEquals("negatives not allowed: -1, -2", thrown.getMessage());
    }

    @Test
    public void testAddNegativeNumbersWithCustomDelimiterAndNewLine() {
        Throwable thrown = assertThrows(IllegalArgumentException.class, () -> calculator.add("//;\n-3;4;-1"), "negatives not allowed: -1");
        assertEquals("negatives not allowed: -3, -1", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> calculator.add("//;\n-1;0;-44;5\n-2"), "negatives not allowed: -1");
        assertEquals("negatives not allowed: -1, -44, -2", thrown.getMessage());
    }

    @Test
    public void testAddIgnoreBigNumbers() {
        assertEquals(2, calculator.add("1001,2"));
        assertEquals(1002, calculator.add("1000,2"));
    }

}
