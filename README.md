# Arbitrary Precision Arithmetic Library

A Java library for performing arbitrary-precision integer and floating-point arithmetic without relying on Java's built-in `BigInteger` or `BigDecimal` classes.

The project implements core arithmetic operations from first principles using string-based representations and classical arithmetic algorithms. It supports integers of unlimited size and floating-point numbers with up to 30 digits of precision after the decimal point.

---

## Features

### Integer Arithmetic (`AInteger`)

* Arbitrary-precision signed integers
* Addition
* Subtraction
* Multiplication
* Division
* Input validation
* Automatic normalization
* Comparison operations
* Immutable-style arithmetic operations

### Floating-Point Arithmetic (`AFloat`)

* Arbitrary-precision floating-point numbers
* Addition
* Subtraction
* Multiplication
* Division
* Support for signed operands
* Decimal alignment and normalization
* Up to 30 digits of precision after the decimal point

### Additional Features

* Command-line calculator interface
* Comprehensive JUnit 5 test suite
* Maven build system
* Docker support
* Fully object-oriented design

---

## Project Structure

```text
src/
├── main/
│   └── java/
│       └── arbitraryarithmetic/
│           ├── AInteger.java
│           ├── AFloat.java
│           └── MyInfArith.java
│
└── test/
    └── java/
        └── arbitraryarithmetic/
            ├── AIntegerTest.java
            └── AFloatTest.java
```

### Core Components

#### AInteger

Implements arbitrary-precision integer arithmetic using string-based storage.

#### AFloat

Implements arbitrary-precision floating-point arithmetic by converting decimal values into scaled integer representations and reusing the integer arithmetic engine.

#### MyInfArith

Command-line interface for performing arithmetic operations on integers and floating-point numbers.

---

## Algorithms Used

### Addition

Performs digit-by-digit addition from right to left while propagating carries.

**Time Complexity:** O(n)

### Subtraction

Performs digit-by-digit subtraction with borrow propagation.

**Time Complexity:** O(n)

### Multiplication

Implements classical long multiplication.

**Time Complexity:** O(n × m)

### Division

Implements long division and generates decimal expansions up to 30 digits after the decimal point.

**Time Complexity:** O(n × m)

---

## Input Normalization

All inputs are converted to a canonical representation before storage.

Examples:

```text
000123      -> 123
-00045      -> -45
0000        -> 0
-0000       -> 0

001.23000   -> 1.23
-000.5000   -> -0.5
```

This guarantees consistent internal storage and comparison.

---

## Build Instructions

### Prerequisites

* Java 17+
* Maven 3.8+

### Compile and Run Tests

```bash
mvn clean test
```

### Build Executable JAR

```bash
mvn clean package
```

---

## Usage

### Integer Arithmetic

Addition:

```bash
java -cp target/classes arbitraryarithmetic.MyInfArith int add 123456789 987654321
```

Output:

```text
1111111110
```

Subtraction:

```bash
java -cp target/classes arbitraryarithmetic.MyInfArith int sub 1000 250
```

Output:

```text
750
```

Multiplication:

```bash
java -cp target/classes arbitraryarithmetic.MyInfArith int mul 12345 67890
```

Output:

```text
838102050
```

Division:

```bash
java -cp target/classes arbitraryarithmetic.MyInfArith int div 1000 3
```

Output:

```text
333
```

---

### Floating-Point Arithmetic

Addition:

```bash
java -cp target/classes arbitraryarithmetic.MyInfArith float add 1.25 2.75
```

Output:

```text
4.0
```

Subtraction:

```bash
java -cp target/classes arbitraryarithmetic.MyInfArith float sub 1.25 2.75
```

Output:

```text
-1.5
```

Multiplication:

```bash
java -cp target/classes arbitraryarithmetic.MyInfArith float mul 0.05 -0.1
```

Output:

```text
-0.005
```

Division:

```bash
java -cp target/classes arbitraryarithmetic.MyInfArith float div 5.5 2
```

Output:

```text
2.75
```

---

## Testing

The project includes a comprehensive JUnit 5 test suite covering:

* Constructors
* Parsing
* Input validation
* Normalization
* Positive and negative operands
* Zero handling
* Large numbers
* Decimal arithmetic
* Division by zero
* Edge cases

Current test status:

```text
68 Tests Passed
0 Failures
0 Errors
```

Run tests using:

```bash
mvn test
```

---

## Docker Support

Build Docker image:

```bash
docker build -t arbitrary-precision-arithmetic .
```

Run container:

```bash
docker run arbitrary-precision-arithmetic int add 123 456
```

---

## Limitations

* Multiplication uses classical long multiplication rather than Karatsuba multiplication.
* Floating-point division is limited to 30 digits after the decimal point.
* Scientific notation is not currently supported.

---

## Future Improvements

* Karatsuba multiplication
* Fast exponentiation
* Modulo operation
* Scientific notation support
* Arbitrary configurable decimal precision
* Performance benchmarking suite

---

## Technologies Used

* Java 17
* Maven
* JUnit 5
* Docker

---

## Author

Atul Boyal

Indian Institute of Technology Hyderabad (IIT Hyderabad)
