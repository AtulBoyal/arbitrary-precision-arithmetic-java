package arbitraryarithmetic;

public class AFloat {
    private String value;

    // Default constructor (Initialize value to "0.0")
    public AFloat(){
        this.value = "0.0";
    }

    // String constructor
    public AFloat(String s){
        if(!isValidFloat(s)){
            throw new IllegalArgumentException(
                "Invalid floating point number: "+ s
            );
        }

        this.value = normalizeFloat(s);
    }

    // Copy Constructor
    public AFloat(AFloat other){
        this.value = other.value;
    }

    // Create a new AFloat instance with input string and return the instance created
    public static AFloat parse(String s){
        return new AFloat(s);
    }

    // Validates whether a string represents a signed decimal number.
    private static boolean isValidFloat(String s){
        return s!=null && s.matches("-?[0-9]+(\\.[0-9]+)?");
    }

    // Converts a decimal string into canonical form.
    private static String normalizeFloat(String s){
        boolean negative = false;

        if(s.startsWith("-")){
            negative = true;
            s = s.substring(1);
        }

        String integerPart;
        String fractionalPart;

        if(s.contains(".")){
            String[] parts = s.split("\\.");
            integerPart = parts[0];
            fractionalPart = parts[1];
        }
        else{
            integerPart = s;
            fractionalPart = "";
        }

        // Remove leading zeroes from integer part
        int i=0;
        while(i < integerPart.length() - 1 && integerPart.charAt(i) == '0'){
            i++;
        }

        integerPart = integerPart.substring(i);

        // Remove trailing zeroes fmor fractional part
        int j = fractionalPart.length();
        while(j>0 && fractionalPart.charAt(j - 1) == '0'){
            j--;
        }

        fractionalPart = fractionalPart.substring(0, j);

        // Pure Zero
        if(integerPart.equals("0") && fractionalPart.isEmpty()){
            return "0.0";
        }

        String result;

        if(fractionalPart.isEmpty()){
            result = integerPart + ".0";
        }
        else{
            result = integerPart + "." + fractionalPart;
        }

        if(negative && !result.equals("0.0")){
            result = "-" + result;
        }

        return result;
    }
    
    
    // Internla helper used by division
    // Compares two non-negative integer strings after decimal points have been removed.
    public int compare(String a, String b){
        /* Returns:
        *   1 for a > b
         *   0 for a==b
         *  -1 for a < b
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
        if(x.toString().equals(y.toString())) return 0;
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

    // Returns the string represenation of the AFloat object
    @Override
    public String toString() {
        return this.value;
    }


    public AFloat add(AFloat other){
        String x = this.value;
        String y = other.value;

        int index1 = ((x).indexOf('.') == -1) ? x.length() : x.indexOf('.');   // index of '.' in string x
        int index2 = ((y).indexOf('.') == -1) ? y.length() : y.indexOf('.');   // index of '.' in string y
        
        int decimalDigits1 = (x.length() - index1 - 1);   // no. of digits after the '.' in string x
        int decimalDigits2 = (y.length() - index2 - 1);   // no. of digits after the '.' in string y
        int maxDecimal = Math.max(decimalDigits1, decimalDigits2);   // no. of digits after decimal in result

        StringBuilder a = new StringBuilder(x.replace(".",""));
        StringBuilder b = new StringBuilder(y.replace(".",""));

        for (int i = decimalDigits1; i < maxDecimal; i++) a.append('0');
        for (int i = decimalDigits2; i < maxDecimal; i++) b.append('0');

        AInteger intA = new AInteger(a.toString());
        AInteger intB = new AInteger(b.toString());

        AInteger result = intA.add(intB);
        StringBuilder answer = new StringBuilder(result.toString());

        // Pad with leading zeroes if result is too short to place decimal
        while (answer.length() <= maxDecimal) {
            answer.insert(0, '0');
        }

        // Insert decimal point
        answer.insert(answer.length() - maxDecimal, '.');

        return new AFloat(answer.toString());
    }


    public AFloat sub(AFloat other){
        String x = this.value.contains(".") ? this.value : this.value + ".0";
        String y = other.value.contains(".") ? other.value : other.value + ".0";
        
        int index1 = ((x).indexOf('.') == -1) ? x.length() : x.indexOf('.');   // index of '.' in string x
        int index2 = ((y).indexOf('.') == -1) ? y.length() : y.indexOf('.');   // index of '.' in string y
        
        int decimalDigits1 = (x.length() - (index1+1));    // no. of digits after the '.' in string x
        int decimalDigits2 = (y.length() - (index2+1));    // no. of digits after the '.' in string y
        int maxDecimal = Math.max(decimalDigits1, decimalDigits2);   // no. of digits after decimal in result
        
        StringBuilder a = new StringBuilder(x.replace(".",""));
        StringBuilder b = new StringBuilder(y.replace(".",""));

        for (int i = decimalDigits1; i < maxDecimal; i++) a.append('0');
        for (int i = decimalDigits2; i < maxDecimal; i++) b.append('0');

        AInteger intA = new AInteger(a.toString());
        AInteger intB = new AInteger(b.toString());

        AInteger result = intA.sub(intB);
        
        StringBuilder answer = new StringBuilder(result.toString());

        boolean negative = false;

        if(answer.charAt(0) == '-'){
            negative = true;
            answer.deleteCharAt(0);
        }

        // Padding with leading zeros if result is too short to place decimal
        while (answer.length() <= maxDecimal) {
            answer.insert(0, '0');
        }

        // Insert decimal point
        answer.insert(answer.length() - maxDecimal, '.');

        if(negative){
            answer.insert(0, '-');
        }

        // Remove trailing zeros and unnecessary dot
        while (answer.length() > 1 && answer.charAt(answer.length() - 1) == '0') {
            answer.deleteCharAt(answer.length() - 1);
        }
        if (answer.charAt(answer.length() - 1) == '.') {
            answer.deleteCharAt(answer.length() - 1);
        }

        return new AFloat(answer.toString());
    }


    public AFloat mul(AFloat other){
        String x = this.value;
        String y = other.value;

        int index1 = x.indexOf('.') == -1 ? 0 : x.length() - 1 - x.indexOf('.');   // index of '.' in string x
        int index2 = y.indexOf('.') == -1 ? 0 : y.length() - 1 - y.indexOf('.');   // index of '.' in string y


        int decimal_index = index1 + index2;

        String a = x.replace(".", "");
        String b = y.replace(".", "");

        AInteger intA = new AInteger(a);
        AInteger intB = new AInteger(b);
        AInteger result = intA.mul(intB);
        
        StringBuilder answer = new StringBuilder(result.toString());

        boolean negative = false;

        if(answer.charAt(0) == '-'){
            negative = true;
            answer.deleteCharAt(0);
        }
        
        // Padding with leading zeros if result is too short to place decimal
        while (answer.length() <= decimal_index) {
            answer.insert(0, '0');
        }

        answer.insert(answer.length() - decimal_index, '.');
        
        // Remove trailing zeros and unnecessary dot
        while (answer.length() > 1 && answer.charAt(answer.length() - 1) == '0') {
            answer.deleteCharAt(answer.length() - 1);
        }
        if (answer.charAt(answer.length() - 1) == '.') {
            answer.deleteCharAt(answer.length() - 1);
        }

        if(negative){
            answer.insert(0, '-');
        }

        return new AFloat(answer.toString());
    }


    public AFloat div(AFloat other){
        String x = this.value;
        String y = other.value;

        boolean negativeResult = (x.startsWith("-")) ^ (y.startsWith("-"));

        if(x.startsWith("-")){
            x = x.substring(1);
        }

        if(y.startsWith("-")){
            y = y.substring(1);
        }

        StringBuilder a = new StringBuilder(x);
        StringBuilder b = new StringBuilder(y);
        
        if (y.equals("0.0") || y.equals("0")){
            throw new ArithmeticException("Division by zero");
        }

        if (x.equals("0.0") || x.equals("0")) {
            return parse("0.0");
        }
        
        // index of decimal from backwards
        int decimalDigitsA = x.contains(".") ? x.length() - 1 - x.indexOf('.') : 0;
        int decimalDigitsB = y.contains(".") ? y.length() - 1 - y.indexOf('.') : 0;
        int totalDecimalShift = decimalDigitsA - decimalDigitsB;    // no. of digits after decimal in result


        if (x.contains(".")) {
            a.deleteCharAt(x.indexOf('.'));
        }
        if (y.contains(".")) {
            b.deleteCharAt(y.indexOf('.'));
        }

        StringBuilder result = new StringBuilder();
        StringBuilder current = new StringBuilder();

        AInteger divisor = new AInteger(b.toString());

        for(int i=0; i<a.length(); i++){
            current.append(a.charAt(i));
            
            // Remove leading zeros in "current"
            while (current.length() > 1 && current.charAt(0) == '0') {
                current.deleteCharAt(0);
            }

            AInteger currentInt = new AInteger(current.toString());

            int digit=0;
            AInteger chosenProduct = new AInteger("0");

            for (int k = 9; k >= 0; k--) {
                AInteger product = divisor.mul(new AInteger(String.valueOf(k)));

                if(product.compareTo(currentInt) <= 0){
                    digit = k;
                    chosenProduct = product;
                    break;
                }
            }
            
            result.append(digit);


            currentInt = currentInt.sub(chosenProduct);
            current = new StringBuilder(currentInt.toString());
        }

        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }

    
        // Calculating the digits after decimal (fractional part)

        boolean hasFraction = !current.toString().equals("0");
        if(hasFraction) result.append('.');

        if(hasFraction){
            for(int i=0; i<30; i++){
                if (current.toString().equals("0")) break;

                current.append('0');

                AInteger currentInt = new AInteger(current.toString());

                int digit=0;
                AInteger chosenProduct = new AInteger("0");
    
                for (int k = 9; k >= 0; k--) {
                    AInteger product = divisor.mul(new AInteger(String.valueOf(k)));
                    
                    if(product.compareTo(currentInt) <= 0){
                        digit = k;
                        chosenProduct = product;
                        break;
                    }
                    
                }
                
                result.append(digit);

                currentInt = currentInt.sub(chosenProduct);
                current = new StringBuilder(currentInt.toString());
            }
        }
        else{
            if(totalDecimalShift==0){
                return parse(result.toString());
            }  
        }
        

        String resultstr = result.toString();   // resultstr contains the division of a and b without decimal points
        int index_decimal = resultstr.indexOf('.');  // storing the index of '.' in resultstr
        if(index_decimal == -1){    // if no deciaml in resulrstr then initialize index as length of resultstr
            index_decimal = resultstr.length();
        }
        else{
            result.deleteCharAt(index_decimal);
        }

        int newDecimalshift = index_decimal - totalDecimalShift;   // decimal position in the final result

        while(newDecimalshift > result.length()){  // Appending zeroes to result if result is too short to place decimal
            result.append('0');
        }

        while (newDecimalshift < 0) {   // Padding with leading zeros if result is too short to place decimal
            result.insert(0, '0');
            newDecimalshift++;
        }

        // Placing decimal in the final result
        if(newDecimalshift < result.length()){
            result.insert(newDecimalshift, '.');
        }

        // Removing the unnecessary leading zeroes
        while(result.length()>1 && result.charAt(0)=='0' && result.charAt(1)!='.'){
            result.deleteCharAt(0);
        }

        // Remove decimal point if it's the last character
        if (result.charAt(result.length() - 1) == '.') {
            result.deleteCharAt(result.length() - 1);
        }

        // Keep no. of digits after decimal to 30
        if(result.indexOf(".") != -1){
            while((result.length()-result.indexOf(".") -1) > 30){
                result.deleteCharAt(result.length()-1);
            }
        }

        String finalAnswer = result.toString();

        if(negativeResult && !finalAnswer.equals("0") && !finalAnswer.equals("0.0")){
            finalAnswer = "-" + finalAnswer;
        }

        return parse(finalAnswer);
    }
}