package arbitraryarithmetic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AFloatTest {

    @Test
    public void testDefaultConstructor() {
        AFloat floatNum = new AFloat();
        assertEquals("0.0", floatNum.toString());
    }

    @Test
    public void testStringConstructorPositive() {
        AFloat floatNum = new AFloat("123.45");
        assertEquals("123.45", floatNum.toString());
    }

    @Test
    public void testStringConstructorNegative() {
        AFloat floatNum = new AFloat("-98.76");
        assertEquals("-98.76", floatNum.toString());
    }

    @Test
    public void testCopyConstructor() {
        AFloat original = new AFloat("5.55");
        AFloat copy = new AFloat(original);
        assertEquals("5.55", copy.toString());
    }

    @Test
    public void testAddPositiveNumbers() {
        AFloat a = new AFloat("84486723.420039");
        AFloat b = new AFloat("70974199.843732");
        AFloat sum = a.add(b);
        assertEquals("155460923.263771", sum.toString());
    }

    @Test
    public void testAddPositiveAndNegative() {
        AFloat a = new AFloat("10.0");
        AFloat b = new AFloat("-5.5");
        AFloat sum = a.add(b);
        assertEquals("4.5", sum.toString());
    }

    @Test
    public void testAddNegativeNumbers() {
        AFloat a = new AFloat("-7.5");
        AFloat b = new AFloat("-2.5");
        AFloat sum = a.add(b);
        assertEquals("-10.0", sum.toString());
    }

    @Test
    public void testSubtractPositiveNumbers() {
        AFloat a = new AFloat("840196454.51725");
        AFloat b = new AFloat("712586963.70283");
        AFloat diff = a.sub(b);
        assertEquals("127609490.81442", diff.toString());
    }

    @Test
    public void testSubtractPositiveFromNegative() {
        AFloat a = new AFloat("-1.0");
        AFloat b = new AFloat("0.5");
        AFloat diff = a.sub(b);
        assertEquals("-1.5", diff.toString());
    }

    @Test
    public void testSubtractNegativeFromPositive() {
        AFloat a = new AFloat("1.0");
        AFloat b = new AFloat("-0.5");
        AFloat diff = a.sub(b);
        assertEquals("1.5", diff.toString());
    }

    @Test
    public void testMultiplyPositiveNumbers() {
        AFloat a = new AFloat("6400251.9377695");
        AFloat b = new AFloat("2326541.6827934");
        AFloat product = a.mul(b);
        assertEquals("14890452913599.9717457253213", product.toString());
    }

    @Test
    public void testMultiplyByZero() {
        AFloat a = new AFloat("12.3");
        AFloat b = new AFloat("0.0");
        AFloat product = a.mul(b);
        assertEquals("0.0", product.toString());
    }

    @Test
    public void testDividePositiveNumbers() {
        AFloat a = new AFloat("8792726365283060579833950521677211.0");
        AFloat b = new AFloat("493835253617089647454998358");
        AFloat quotient = a.div(b);
        assertEquals("17804979.091469989302961159520087878533", quotient.toString());
    }

    @Test
    public void testDivideByZero() {
        assertThrows(
            ArithmeticException.class,
            () -> new AFloat("10.0")
                    .div(new AFloat("0.0"))
        );
    }

    @Test
    public void testDivideSmallerByLarger() {
        AFloat a = new AFloat("5.5");
        AFloat b = new AFloat("2");
        AFloat quotient = a.div(b);
        assertEquals("2.75", quotient.toString());
    }

    @Test
    public void testDivideFloatByInteger() {
        AFloat a = new AFloat("3227");
        AFloat b = new AFloat("555");
        String expected = "5.814414414414414414414414414414"; // Repeating to 30 digits
        assertEquals(expected, a.div(b).toString());
    }

    @Test
    public void testSubtractSmallNegativeDecimal() {
        AFloat a = new AFloat("0.05");
        AFloat b = new AFloat("0.10");

        assertEquals("-0.05", a.sub(b).toString());
    }

    @Test
    public void testMultiplySmallNegativeDecimal() {
        AFloat a = new AFloat("0.05");
        AFloat b = new AFloat("-0.1");

        assertEquals("-0.005", a.mul(b).toString());
    }

    @Test
    public void testNormalizeLeadingZeros() {
        assertEquals("123.45", new AFloat("000123.45000").toString());
    }

    @Test
    public void testNormalizeNegativeZero() {
        assertEquals("0.0", new AFloat("-0.000").toString());
    }

    @Test
    public void testNormalizeIntegerInput() {
        assertEquals("123.0", new AFloat("123").toString());
    }

    @Test
    public void testInvalidAlphabeticInput() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new AFloat("abc")
        );
    }

    @Test
    public void testInvalidMultipleDots() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new AFloat("1.2.3")
        );
    }

    @Test
    public void testInvalidDoubleMinus() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new AFloat("--12.5")
        );
    }

    @Test
    public void testAddWithZero() {
        assertEquals(
            "123.45",
            new AFloat("123.45")
                .add(new AFloat("0.0"))
                .toString()
        );
    }

    @Test
    public void testAddOppositeNumbers() {
        assertEquals(
            "0.0",
            new AFloat("5.25")
                .add(new AFloat("-5.25"))
                .toString()
        );
    }

    @Test
    public void testAddCarryAcrossDecimal() {
        assertEquals(
            "10.0",
            new AFloat("9.99")
                .add(new AFloat("0.01"))
                .toString()
        );
    }

    @Test
    public void testSubtractSameNumber() {
        assertEquals(
            "0.0",
            new AFloat("123.456")
                .sub(new AFloat("123.456"))
                .toString()
        );
    }

    @Test
    public void testBorrowAcrossDecimal() {
        assertEquals(
            "9.99",
            new AFloat("10.00")
                .sub(new AFloat("0.01"))
                .toString()
        );
    }

    @Test
    public void testMultiplyNegativePositive() {
        assertEquals(
            "-12.5",
            new AFloat("-2.5")
                .mul(new AFloat("5"))
                .toString()
        );
    }

    @Test
    public void testMultiplyDecimalShift() {
        assertEquals(
            "0.01",
            new AFloat("0.1")
                .mul(new AFloat("0.1"))
                .toString()
        );
    }

    @Test
    public void testExactDivision() {
        assertEquals(
            "5.0",
            new AFloat("10")
                .div(new AFloat("2"))
                .toString()
        );
    }

    @Test
    public void testTerminatingDivision() {
        assertEquals(
            "0.5",
            new AFloat("1")
                .div(new AFloat("2"))
                .toString()
        );
    }

    @Test
    public void testNegativeDivision() {
        assertEquals(
            "-2.5",
            new AFloat("5")
                .div(new AFloat("-2"))
                .toString()
        );
    }

    @Test
    public void testDoubleNegativeDivision() {
        assertEquals(
            "2.5",
            new AFloat("-5")
                .div(new AFloat("-2"))
                .toString()
        );
    }

    @Test
    public void testZeroNumeratorDivision() {
        assertEquals(
            "0.0",
            new AFloat("0")
                .div(new AFloat("123"))
                .toString()
        );
    }
}