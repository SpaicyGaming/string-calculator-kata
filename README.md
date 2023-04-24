# SimpleStringCalculator

Java implementation of the [simple-string-calculator TDD KATA](https://gitlab.com/cherry-chain/kata/string-calculator-kata).

## Getting Started

### Prerequisites
- Java 17 or higher

### Building

To build the project, run the following command:

```bash
./gradlew build
```

this will compile the project and run the tests. It will also generate a JaCoCo report in `$buildDir/reports/jacoco`.

### Running

To run the project, run the following command:

```bash
java -jar build/libs/string-calculator-kata-1.0-SNAPSHOT.jar [INPUT_STRING]
```