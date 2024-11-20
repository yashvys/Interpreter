

#### Overview
`ComplexInterpreter` is a simple interpreter built in Java that can parse and evaluate mathematical expressions, supporting basic arithmetic operations such as addition, subtraction, multiplication, division, and exponentiation. It also allows the use of variables to store and retrieve values. The interpreter follows a tokenization approach where the input expression is broken into tokens (such as numbers, operators, and parentheses), and then parsed to compute the result.

#### Features:
- Supports basic arithmetic operations: `+`, `-`, `*`, `/`, and `^` (exponentiation).
- Handles parentheses to control operation precedence.
- Supports variable assignment and retrieval.
- Provides error handling for undefined variables.
- Allows complex expressions to be parsed and evaluated, such as: `x = (2 + 3) * (4 - 2) / 2 ^ 2`.

---

### Steps to Set Up and Run the Project

#### 1. Clone the Repository from GitHub
1. First, make sure you have **Git** installed on your local machine. If not, download and install it from [Git's official site](https://git-scm.com/).
   
2. Clone the repository using the following command:

    ```bash
    git clone https://github.com/your-username/complex-interpreter.git
    ```

3. Navigate to the project directory:

    ```bash
    cd complex-interpreter
    ```

#### 2. Set Up Your Java Development Environment

Make sure you have **Java** installed on your system. The project uses **Java 8 or later**. You can download and install Java from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html).

Once installed, verify Java is correctly set up by running the following command in your terminal or command prompt:

```bash
java -version
```

#### 3. Build and Run the Project

1. **Using IDE (e.g., IntelliJ IDEA or Eclipse):**

   - Open the project in your IDE.
   - Compile the project using the IDE's build option.
   - Run the `ComplexInterpreter` class (the `main` method).

2. **Using Command Line:**

   If you prefer using the command line, follow these steps:
   
   - Make sure your terminal is in the root directory of the project.
   
   - Compile the Java code:

     ```bash
     javac ComplexInterpreter.java
     ```

   - Run the compiled program:

     ```bash
     java ComplexInterpreter
     ```

#### 4. Sample Output

```bash
Result: 5
Result (y): 7
Variables: {x=5, y=7}
```
