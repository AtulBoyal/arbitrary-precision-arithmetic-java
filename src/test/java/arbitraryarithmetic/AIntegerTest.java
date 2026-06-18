package arbitraryarithmetic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AIntegerTest {

    @Test
    public void testDefaultConstructor() {
        AInteger integer = new AInteger();
        assertEquals("0", integer.toString());
    }

    @Test
    public void testStringConstructorPositive() {
        AInteger integer = new AInteger("12345");
        assertEquals("12345", integer.toString());
    }

    @Test
    public void testStringConstructorNegative() {
        AInteger integer = new AInteger("-9876");
        assertEquals("-9876", integer.toString());
    }

    @Test
    public void testCopyConstructor() {
        AInteger original = new AInteger("555");
        AInteger copy = new AInteger(original);
        assertEquals("555", copy.toString());
    }

    @Test
    public void testParsePositive() {
        AInteger integer = AInteger.parse("100");
        assertEquals("100", integer.toString());
    }

    @Test
    public void testParseNegative() {
        AInteger integer = AInteger.parse("-200");
        assertEquals("-200", integer.toString());
    }

    @Test
    public void testAddPositiveNumbers() {
        AInteger a = new AInteger("23650078224912949497310933240250");
        AInteger b = new AInteger("42939783262467113798386384401498");
        AInteger sum = a.add(b);
        assertEquals("66589861487380063295697317641748", sum.toString());
    }

    @Test
    public void testAddPositiveAndNegative() {
        AInteger a = new AInteger("100");
        AInteger b = new AInteger("-50");
        AInteger sum = a.add(b);
        assertEquals("50", sum.toString());
    }

    @Test
    public void testAddNegativeNumbers() {
        AInteger a = new AInteger("-75");
        AInteger b = new AInteger("-25");
        AInteger sum = a.add(b);
        assertEquals("-100", sum.toString());
    }

    @Test
    public void testSubtractPositiveNumbers() {
        AInteger a = new AInteger("3116511674006599806495512758577");
        AInteger b = new AInteger("57745242300346381144446453884008");
        AInteger diff = a.sub(b);
        assertEquals("-54628730626339781337950941125431", diff.toString());
    }

    @Test
    public void testSubtractPositiveFromNegative() {
        AInteger a = new AInteger("-10");
        AInteger b = new AInteger("5");
        AInteger diff = a.sub(b);
        assertEquals("-15", diff.toString());
    }

    @Test
    public void testSubtractNegativeFromPositive() {
        AInteger a = new AInteger("10");
        AInteger b = new AInteger("-5");
        AInteger diff = a.sub(b);
        assertEquals("15", diff.toString());
    }

    @Test
    public void testMultiplyPositiveNumbers() {
        AInteger a = new AInteger("14344163160445929942680697312322");
        AInteger b = new AInteger("23017167694823904478474013730519");
        AInteger product = a.mul(b);
        assertEquals("330162008905899217578310782382075660760972861550182008086155118", product.toString());
    }

    @Test
    public void testMultiplyByZero() {
        AInteger a = new AInteger("123");
        AInteger b = new AInteger("0");
        AInteger product = a.mul(b);
        assertEquals("0", product.toString());
    }

    @Test
    public void testDividePositiveNumbers() {
        AInteger a = new AInteger("8792726365283060579833950521677211");
        AInteger b = new AInteger("493835253617089647454998358");
        AInteger quotient = a.div(b);
        assertEquals("17804979", quotient.toString());
    }

    @Test
    public void testDivideByZero() {
        assertThrows(
            ArithmeticException.class,
            () -> new AInteger("100")
                    .div(new AInteger("0"))
        );
    }

    @Test
    public void testDivideSmallerByLarger() {
        AInteger a = new AInteger("25");
        AInteger b = new AInteger("123");
        AInteger quotient = a.div(b);
        assertEquals("0", quotient.toString());
    }

    @Test
    public void testNormalizationLeadingZeros() {
        AInteger integer = new AInteger("0000123");
        assertEquals("123", integer.toString());
    }

    @Test
    public void testNormalizationNegativeLeadingZeros() {
        AInteger integer = new AInteger("-0000123");
        assertEquals("-123", integer.toString());
    }

    @Test
    public void testNormalizationZero() {
        AInteger integer = new AInteger("-0000");
        assertEquals("0", integer.toString());
    }

    @Test
    public void testInvalidAlphabeticInput() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new AInteger("abc")
        );
    }

    @Test
    public void testInvalidDoubleMinusInput() {
        assertThrows(IllegalArgumentException.class, () -> new AInteger("--123"));
    }

    @Test
    public void testInvalidDecimalInput() {
        assertThrows(IllegalArgumentException.class, () -> new AInteger("12.34"));
    }

    @Test
    public void testComparePositiveNumbers() {
        assertTrue(
            new AInteger("100")
                .compareTo(new AInteger("50")) > 0
        );
    }

    @Test
    public void testCompareNegativeNumbers() {
        assertTrue(
            new AInteger("-100")
                .compareTo(new AInteger("-50")) < 0
        );
    }

    @Test
    public void testComparePositiveAndNegative() {
        assertTrue(
            new AInteger("50")
                .compareTo(new AInteger("-50")) > 0
        );
    }

    @Test
    public void testAddOppositeNumbers() {
        AInteger result =
            new AInteger("123")
                .add(new AInteger("-123"));

        assertEquals("0", result.toString());
    }

    @Test
    public void testAddWithZero() {
        AInteger result =
            new AInteger("123")
                .add(new AInteger("0"));

        assertEquals("123", result.toString());
    }

    @Test
    public void testMultiplyNegativeAndPositive() {
        AInteger result =
            new AInteger("-12")
                .mul(new AInteger("5"));

        assertEquals("-60", result.toString());
    }

    @Test
    public void testMultiplyNegativeAndNegative() {
        AInteger result =
            new AInteger("-12")
                .mul(new AInteger("-5"));

        assertEquals("60", result.toString());
    }

    @Test
    public void testDivideNegativeAndPositive() {
        AInteger result =
            new AInteger("-25")
                .div(new AInteger("5"));

        assertEquals("-5", result.toString());
    }

    @Test
    public void testDivideNegativeAndNegative() {
        AInteger result =
            new AInteger("-25")
                .div(new AInteger("-5"));

        assertEquals("5", result.toString());
    }

}