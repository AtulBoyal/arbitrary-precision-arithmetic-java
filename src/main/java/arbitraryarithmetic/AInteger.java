package arbitraryarithmetic;

import java.util.concurrent.atomic.AtomicInteger;

public class AInteger {
    private String value;

    // Default constructor (Initialize value to "0")
    public AInteger(){
        this.value = "0";
    }

    // String Constructor 
    public AInteger(String s){
        if(!isValidInteger(s)){
            throw new IllegalArgumentException("Invalid integer: " + s);
        }

        this.value = normalize(s);
    }

    /*
     Validates whether a string represents a signed decimal integer.
     Accepted: 123, -456, 0
     Rejected: 12.4, abc, --45
    */
    private static boolean isValidInteger(String s){
        return s != null && s.matches("-?[0-9]+");
    }

    /*
     * Converts an integer string into canonical form
     * 
     * Example:
     * "000123" -> "123"
     * "-0045"  -> "-45"
     * "0000"   -> "0"
     * "-000"   -> "0"
     * 
    */
    private static String normalize(String s){
        boolean negative = false;

        if(s.startsWith("-")){
            negative = true;
            s = s.substring(1);
        }

        int i = 0;

        while(i<s.length()-1 && s.charAt(i) == '0'){
            i++;
        }

        s = s.substring(i);

        if(s.equals("0")){
            return "0";
        }

        return negative? "-" + s: s;

    }

    // Returns true if the stored value is negative.
    private boolean isNegative(){
        return value.startsWith("-");
    }

    // Returns teh absolute value as a string.
    private String absValue(){
        if(isNegative()) return value.substring(1);
        return value;
    }

    // Copy Constructor
    public AInteger(AInteger other){ 
        this.value = other.value;
    }

    // Create a new AInteger instance with input string and return the instance created
    public static AInteger parse(String s){
        return new AInteger(s);
    }

    public int compare(String a, String b){
        /* Compares two non-negative integer strings.
         *
         * Returns:
         *   1 if a > b
         *   0 if a == b
         *  -1 if a < b
         * 
         * Leading zeroes are ignored before comparison.
         */

        StringBuilder x = new StringBuilder(a);
        StringBuilder y = new StringBuilder(b);
        
        // removing leading zeroes from both numbers
        while(x.length()>1 && x.charAt(0)=='0'){
            x.deleteCharAt(0);
        }
        while(y.length()>1 && y.charAt(0)=='0'){
            y.deleteCharAt(0);
        }

        int length1 = x.length();
        int length2 = y.length();
        
        // Compare lengths
        if(length1>length2) return 1;
        if(length1<length2) return -1;

        // If equal length compare digit by digit from left
        for(int i=0;i<length1; i++){
            int aDigit = (int)(x.charAt(i)-'0');
            int bDigit = (int)(y.charAt(i)-'0');

            if(aDigit > bDigit) return 1;
            if(aDigit < bDigit) return -1;
        }
        return 0;
    }

    public int compareTo(AInteger other){
        boolean thisNegative = this.isNegative();
        boolean otherNegative = other.isNegative();

        // Negative is always smaller than positive
        if(thisNegative && !otherNegative){
            return -1;
        }

        // Positive is always greater than negative
        if(!thisNegative && otherNegative){
            return 1;
        }

        // Both Positive
        if(!thisNegative && !otherNegative){
            return compare(this.value, other.value);
        }

        // Both Negative
        return -compare(this.absValue(), other.absValue());
    }


    // Returns the string represenation of the AInteger object
    @Override
    public String toString(){
        return this.value;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(!(obj instanceof AInteger)){
            return false;
        }

        AInteger other = (AInteger)obj;

        return this.value.equals(other.value);
    }

    // Hash code based on normalized string representation
    @Override
    public int hashCode(){
        return value.hashCode();
    }

    private AInteger abs(){
        return new AInteger(this.absValue());
    }

    private AInteger negate(){
        if(this.value.equals("0")){
            return new AInteger("0");
        }
        
        if(this.isNegative()){
            return new AInteger(this.absValue());
        }

        return new AInteger("-" + this.value);
    }
    


    // Adds two arbitrary precision integers
    public AInteger add(AInteger other){
        boolean thisNegative = this.isNegative();
        boolean otherNegative = other.isNegative();

        // +a + +b
        if(!thisNegative && !otherNegative){
            return this.addPositive(other);
        }

        // -a + -b
        if(thisNegative && otherNegative){
            return this.abs().addPositive(other.abs()).negate();
        }

        // +a + -b
        if(!thisNegative){
            return this.subtractPositive(other.abs());
        }

        // -a + +b
        return other.subtractPositive(this.abs());
    }

    // Subtracts two arbitrary precision integers.
    public AInteger sub(AInteger other){
        return this.add(other.negate());
    }

    // Multiplies two arbitrary precision integers.
    public AInteger mul(AInteger other){
        boolean negativeResult = this.isNegative() ^ other.isNegative();

        AInteger result = this.abs().multiplyPositive(other.abs());

        if(result.toString().equals("0")){
            return result;
        }

        if(negativeResult){
            return result.negate();
        }

        return result;
    }

    // Divides two arbitrary precision integers.
    public AInteger div(AInteger other){
        if(other.toString().equals("0")){
            throw new ArithmeticException("Division by zero");
        }

        boolean negativeResult = this.isNegative() ^ other.isNegative();

        AInteger result = this.abs().dividePositive(other.abs());

        if(result.toString().equals("0")){
            return result;
        }

        if(negativeResult){
            return result.negate();
        }

        return result;
    }


    // Adds two non-negative integers
    private AInteger addPositive(AInteger other){
        if(this.isNegative() || other.isNegative()){
            throw new IllegalArgumentException(
                "addPositive only accepts non-negtiave integers"
            );
        }

        String a = this.value;
        String b = other.value;

        int length1 = a.length();
        int length2 = b.length();

        int carry = 0;

        int new_length = Math.max(length1, length2) + 1;

        StringBuilder result = new StringBuilder();

        // For each digit position sum up digits and carry, store units digit and update carry
        for(int i=1; i<=new_length; i++){
            int digit1 = (i<=length1) ? a.charAt(length1 - i) - '0' : 0;
            int digit2 = (i<=length2) ? b.charAt(length2 - i) - '0' : 0;

            int sum = digit1 + digit2 + carry;

            result.append((char)('0' + (sum%10)));

            carry = sum/10;
        }

        result.reverse();

        return parse(normalize(result.toString()));
    }

    
    private AInteger subtractPositive(AInteger other){
        if(this.isNegative() || other.isNegative()){
            throw new IllegalArgumentException(
                "subtractPositive only accepts non-negative integers"
            );
        }
        
        String num1 = this.value;
        String num2 = other.value;

        // Indicates whether the final result should be negative.
        boolean negative = false;
        int cmp = compare(num1, num2);

        // If numbers are equal, return 0
        if(cmp == 0){
            return parse("0");
        }

        /*
         * If num1 < num2:
         * 1. Swap operands.
         * 2. Compute |num1 - num2|.
         * 3. Attach '-' sign at the end.
        */
        if(cmp < 0){
            String temp = num1;
            num1 = num2;
            num2 = temp;
            negative = true;
        }

        String a = num1;
        StringBuilder b = new StringBuilder(num2);

        StringBuilder result = new StringBuilder();

        // Pad smaller number with leading zeroes for subtraction digit by digit
        while(b.length() < a.length()){
            b.insert(0, '0');
        }

        int borrow = 0;
        for(int i=(a.length()-1); i>=0; i--){
            int digit1 = a.charAt(i) - '0' - borrow;
            int digit2 = b.charAt(i) - '0';

            // Borrow from the next higher digit when current digit is insufficient.
            if(digit1 < digit2){
                digit1 += 10;
                borrow = 1;
            }
            else{
                borrow = 0;
            }

            result.append((char)(digit1 - digit2 + '0'));
        }

        result.reverse();

        String answer = normalize(result.toString());

        if(negative){
            answer = "-" + answer;
        }

        return parse(answer);
    }
    

    private AInteger multiplyPositive(AInteger other){
        if(this.value.equals("0") || other.value.equals("0")){
            return parse("0");
        }

        if(this.isNegative() || other.isNegative()){
            throw new IllegalArgumentException(
                "multiplyPositive only accepts non-negative integers"
            );
        }

        String num1 = this.value;
        String num2 = other.value;

        int length1 = num1.length();
        int length2 = num2.length();

        // Use the shorter number as the outer loop to reduce iterations.
        String smallerNum = "";
        String biggerNum = "";
        if(length1<length2){
            smallerNum = num1;
            biggerNum = num2;
        }
        else{
            smallerNum = num2;
            biggerNum = num1;
        }

        int smaller = smallerNum.length();
        int bigger = biggerNum.length();

        StringBuilder result = new StringBuilder("0");

        /*
         * For each digit of the smaller number:
         * 1. Multiply it with the entire larger number.
         * 2. Append appropriate trailing zeroes.
         * 3. Add the partial product to the running result.
        */
        for(int i=0; i<smaller; i++){
            int currentDigit = (int)(smallerNum.charAt(smaller-1-i)-'0');
            StringBuilder temp = new StringBuilder();

            int carry = 0;

            for(int j=0;j<bigger; j++){
                int product = currentDigit * (int)(biggerNum.charAt(bigger-1-j)-'0') + carry;

                temp.append((char)(product%10 + '0'));
                carry = product/10; 
            }

            if(carry!=0){
                temp.append((char)(carry + '0'));
            }

            temp.reverse();

            for(int k=0;k<i; k++){
                temp.append('0');
            }

            StringBuilder result_new = new StringBuilder((parse(result.toString()).addPositive(parse(temp.toString()))).toString());
            result = result_new;
        }

        String answer = result.toString();
        return parse(answer);
    }
    
    /*
     * Implements long division.
     *
     * At each step:
     * 1. Bring down the next digit.
     * 2. Determine the largest quotient digit (0-9).
     * 3. Subtract divisor x quotient digit.
     * 4. Continue with the remainder.
    */
    private AInteger dividePositive(AInteger other){
        if(this.isNegative() || other.isNegative()){
            throw new IllegalArgumentException(
                "dividePositive only accepts non-negative integers"
            );
        }
        

        String a = this.value;
        String b = other.value;
        
        if (b.equals("0")){
            throw new ArithmeticException("Division by zero");
        }

        if(compare(a,b) < 0){
            return parse("0");
        }

        StringBuilder result = new StringBuilder();
        StringBuilder current = new StringBuilder();

        AInteger divisor = parse(b);

        for(int i=0; i<a.length(); i++){
            current.append(a.charAt(i));

            current = new StringBuilder(normalize(current.toString()));

            int digit=0;

            // Determine the largest quotient digit such that:
            // divisor × digit <= current remainder.
            for(int k=9;k>=0;k--){
                AInteger product = divisor.multiplyPositive(parse(String.valueOf(k)));
                if (compare(product.toString(), current.toString()) <= 0) { // product <= current
                    digit = k;
                    break;
                }
            }

            result.append(digit);

            AInteger product = divisor.multiplyPositive(parse(String.valueOf(digit)));
            AInteger diff = parse(current.toString()).subtractPositive(product);
            current = new StringBuilder(diff.toString());
        }

        return parse(normalize(result.toString()));
    }
}
