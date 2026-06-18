package arbitraryarithmetic;

/*
 * MyInfArith - Arbitrary Precision Arithmetic Calculator
 * 
 * Performs basic arithmetic operations(+, - , *, /) on large integers and high-precision floating point numbers (30 decimal places)
 * 
 * Example:
 * java arbitraryarithmetic.MyInfArith int add 123456789 987654321
 * java arbitraryarithmetic.MyInfArith float div 22.0 7.0
*/

public class MyInfArith{
    public static void main(String[] args){
        // Argument validation
        if(args.length != 4){
            printUsage();
            return;
        }

        String type = args[0];
        String op = args[1];
        String num1 = args[2];
        String num2 = args[3];
        
        try{
            // Integer operation
            if(type.equals("int")){
                AInteger a = AInteger.parse(num1);
                AInteger b = AInteger.parse(num2);
                AInteger result;

                switch (op) {
                    case "add":
                        result = a.add(b);
                        break;

                    case "sub":
                        result = a.sub(b);
                        break;

                    case "mul":
                        result = a.mul(b);
                        break;

                    case "div":
                        result = a.div(b);
                        break;

                    default:
                        System.out.println("Invalid operation: " + op);
                        printUsage();
                        return;
                }

                System.out.println(result);
            }
            // float operation
            else if(type.equals("float")){
                AFloat a = AFloat.parse(num1);
                AFloat b = AFloat.parse(num2);
                AFloat result;

                switch(op){
                    case "add":
                        result = a.add(b);
                        break;

                    case "sub":
                        result = a.sub(b);
                        break;

                    case "mul":
                        result = a.mul(b);
                        break;

                    case "div":
                        result = a.div(b);
                        break;

                    default:
                        System.out.println("Invalid operation: " + op);
                        printUsage();
                        return;
                }

                System.out.println(result);
            }
            else{
                System.out.println("Invalid type. Use 'int' or 'float'");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Invalid input: " + e.getMessage());
        }
        catch(ArithmeticException e){
            System.out.println(e.getMessage());
        }
    }

    private static void printUsage(){
        System.out.println("Usage:");
        System.out.println("  java arbitraryarithmetic.MyInfArith <int|float> <add|sub|mul|div> <num1> <num2>");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java arbitraryarithmetic.MyInfArith int add 123456789 987654321");
        System.out.println("  java arbitraryarithmetic.MyInfArith float div 22.0 7.0");
    }
}