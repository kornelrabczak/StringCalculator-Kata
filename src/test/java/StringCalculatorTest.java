import com.google.common.collect.Lists;
import com.googlecode.catchexception.CatchException;
import hackathon.StringCalculator;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.googlecode.catchexception.CatchException.*;
import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {

    private StringCalculator sut;

    @Before
    public void setUp() {
        sut = new StringCalculator(Lists.newArrayList(",", "\n"));
    }

    @Test
    public void emptyStringShouldBeZero() {
        assertSum("", 0);
    }

    private void assertSum(String numbers, int expectedSum) {
        int actualSum = sut.add(numbers);

        assertEquals(expectedSum, actualSum);
    }

    @Test
    public void shouldSumOneNumber() {
        assertSum("1", 1);
    }

    @Test
    public void shouldSumTwoNumbers() {
        assertSum("1,2", 3);
    }

    @Test
    public void shouldSumManyNumbers() {
        assertSum("10,5,9,20", 44);
    }

    @Test
    public void shouldHandleNewLineSeparator() {
        assertSum("6\n10,4", 20);
    }

    @Test
    public void shouldHandleCustomSeparator() {
        assertSum("//[;]\n1;2", 3);
    }

    @Test
    public void shouldHandleCustomSeparatorAndNoNumbers() {
        assertSum("//[;]\n", 0);
    }

    @Test
    public void shouldIgnoreNumbersBiggerThen1000() {
        assertSum("//[;]\n1,12333;34534534,5", 6);
    }

    @Test
    public void shouldFailToSumNegatives() {
        catchException(sut).add("-6,4,-15");

        String actualMessage = caughtException().getMessage();
        assertEquals("Negative numbers are not supported. Negatives=[-6, -15]", actualMessage);
    }
}
