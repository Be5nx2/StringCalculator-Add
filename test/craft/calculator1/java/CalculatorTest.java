package craft.calculator1.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    // Step 1
    @Test
    public void should_return0_when_givenEmptyStringAsInput() {
        final Calculator calculator = new Calculator();

        final int result = calculator.add("");
        assertEquals(0, result);
    }

    @Test
    public void should_return1_when_given1numberAsInput() {
        final Calculator calculator = new Calculator();
        final String s = "1";

        final int result = calculator.add(s);

        assertEquals(1, result);
    }

    @Test
    public void should_returnSumOfInput_when_givenTwoNumbersAsInputs() {
        final Calculator calculator = new Calculator();

        final int result = calculator.add("1,2");

        assertEquals(3, result);
    }

    @Test
    public void should_throwException_when_givenStringFinishPerComa() {
        final Calculator calculator = new Calculator();

        assertThrows(Exception.class, () -> calculator.add("1,"));
    }


    // Step 2 : nothing to do, already done
    /*
    @Test
    public void should_returnSumOfInputs_when_givenFiveNumbersAsInputs(){
        final Calculator calculator = new Calculator();

        final int result = calculator.add("1,2,3,4,5");

        assertEquals(15, result);
    }
    */

    // Step 3
    @Test
    public void should_handleNewLineCharacter_when_givenInputsOnManyLine() {
        final String s = "1\n2,3";
        final Calculator calculator = new Calculator();

        final int result = calculator.add(s);
        assertEquals(6, result);
    }

    // Bonus
    @Test
    public void should_throwException_when_givenInputsWithCommaWithoutNumberAfter() {
        final String s = "1,\n";
        final Calculator calculator = new Calculator();

        assertThrows(Exception.class, () -> {
            calculator.add(s);
        });

    }

    // Step 4
    @Test
    public void should_sumOfAmount_when_useTheSpecificBeginningToConfigureDelimiter() {
        // specific format : //[delimiter]\n[numbers…]
        final String input = "//;\n1;2";
        final Calculator calculator = new Calculator();

        assertEquals(3, calculator.add(input));
    }

    // Step 5
    @Test
    public void should_throwExceptionWithTheNegative_when_givenInputWithNegative() {
        final String input = "-1";
        final Calculator calculator = new Calculator();

        assertThrowsExactly(IllegalArgumentException.class,
                () -> calculator.add(input),
                "negatives not allowed : -1");
    }

    // Bonus check
    @Test
    public void should_throwExceptionWithTheAllNegative_when_givenInputWithManyNegative() {
        final String input = "-1,2,-5,-8,9";
        final Calculator calculator = new Calculator();

        assertThrowsExactly(IllegalArgumentException.class,
                () -> calculator.add(input),
                "negatives not allowed : -1, -5, -8");
    }

}