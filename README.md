# Arbitrary Precision Arithmetic Library

## Features
- Arbitrary-size integer arithmetic
- High-precision floating-point arithmetic
- Addition
- Subtraction
- Multiplication
- Division
- Input normalization
- JUnit test suite

## Build

mvn clean test

## Run

java -cp target/classes arbitraryarithmetic.MyInfArith int add 123 456

Output:
579

## Project Structure

AInteger.java
AFloat.java
MyInfArith.java
AIntegerTest.java
AFloatTest.java

## Testing

68 unit tests passing.