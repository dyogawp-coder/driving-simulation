# Car Crash Java - Driving Simulation

## Overview

This project implements a command-line driving simulation in Java.

The application allows users to:

* Create a rectangular simulation field.
* Add one or more cars.
* Define the starting position and direction of each car.
* Provide movement commands.
* Run the simulation.
* Detect collisions between cars.
* Start a new simulation or exit the application.

## Technology

* Java 21
* Gradle 8.10
* JUnit 5

## Prerequisites

### Java

Java 21 or later is required.

Verify your Java installation:

```bash
java -version
```

The project includes the Gradle Wrapper, so **Gradle does not need to be installed separately**.

---

## Running the Application

### Windows - Command Prompt

Open Command Prompt in the project root directory and run:

```cmd
gradlew.bat run
```

### Windows - PowerShell

Open PowerShell in the project root directory and run:

```powershell
.\gradlew.bat run
```

### Git Bash / Linux / macOS

Run:

```bash
./gradlew run
```

Alternatively, on Linux/macOS or Git Bash:

```bash
./start.sh
```

---

## Running Tests

To execute the complete test suite:

### Windows

```cmd
gradlew.bat clean test
```

### PowerShell

```powershell
.\gradlew.bat clean test
```

### Linux / macOS / Git Bash

```bash
./gradlew clean test
```

The test report is generated under:

```text
build/reports/tests/test/index.html
```

---

## Building the Application

To build the project:

### Windows

```cmd
gradlew.bat build
```

### Linux / macOS / Git Bash

```bash
./gradlew build
```

---

## How the Simulation Works

### 1. Create the simulation field

The application first asks for the field width and height:

```text
Please enter the width and heigh of the simulation field in x y format:
10 10
```

The field uses zero-based coordinates.

For a `10 x 10` field:

* Valid X coordinates: `0` to `9`
* Valid Y coordinates: `0` to `9`

Both width and height must be greater than zero.

### 2. Add a car

The user provides:

* Car name
* Initial X coordinate
* Initial Y coordinate
* Initial direction
* Movement commands

Example:

```text
Please enter the name of the car:
A

Please enter initial position of car A in x y Direction format:
1 2 N

Please enter the commands for car A:
Valid commands: L (Left), R (Right), F (Forward)
FFRFFFFRRL
```

Supported commands:

| Command | Description               |
| ------- | ------------------------- |
| `F`     | Move forward one position |
| `L`     | Turn left                 |
| `R`     | Turn right                |

Both uppercase and lowercase commands are accepted.

Example:

```text
ffrffffrrl
```

is treated the same as:

```text
FFRFFFFRRL
```

### 3. Run the simulation

The simulation executes one command per car at each simulation step.

Cars move according to their current position and direction.

Example:

```text
Your current list of cars are:
- A, (1,2) N, FFRFFFFRRL
```

After simulation:

```text
After simulation, the result is:
- A, (5,4) S
```

---

## Boundary Behavior

If a car attempts to move outside the simulation field, the forward command is ignored.

The car remains at its current position.

For example, if a car is at:

```text
(0,0) S
```

and executes:

```text
F
```

the movement is ignored because the resulting position would be outside the field.

The car remains at:

```text
(0,0) S
```

---

## Collision Behavior

A collision occurs when two or more cars occupy the same position at the same simulation step.

When a collision occurs:

* All cars involved in the collision are marked as collided.
* The collision position is reported.
* The simulation stops for the collided cars.
* The collision step is reported.

Example:

```text
After simulation, the result is:
- A, collides with B at (5,4) at step 7
- B, collides with A at (5,4) at step 7
```

---

## Input Validation

The application validates user input during the simulation setup.

Examples of invalid input include:

* Zero or negative field width.
* Zero or negative field height.
* Invalid field dimensions.
* Car name that is empty.
* Duplicate car name.
* Invalid starting position.
* Starting position outside the simulation field.
* Starting position already occupied by another car.
* Invalid direction.
* Empty movement commands.
* Unsupported movement commands.

Supported movement commands are:

```text
L
R
F
```

For an invalid command such as:

```text
FFR9F
```

the application reports an error such as:

```text
Invalid input: Invalid command: 9. Valid commands are L (Left), R (Right), and F (Forward).
```

---

## Application Flow

The main menu provides:

```text
Please choose from the following options:
[1] Add a car to field
[2] Run simulation
```

After the simulation completes, the user can choose:

```text
Please choose from the following options:
[1] Start over
[2] Exit
```

Selecting **Start over** creates a new simulation.

Selecting **Exit** terminates the application.

---

## Project Structure

```text
driving-simulation/
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── start.sh
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── drivingsimulation/
    │               ├── Application.java
    │               ├── application/
    │               │   └── SimulationApplicationService.java
    │               ├── cli/
    │               │   ├── CommandLineInterface.java
    │               │   ├── InputReader.java
    │               │   └── OutputFormatter.java
    │               ├── domain/
    │               │   ├── Car.java
    │               │   ├── Direction.java
    │               │   ├── Field.java
    │               │   └── Position.java
    │               └── simulation/
    │                   ├── CollisionDetector.java
    │                   └── SimulationEngine.java
    └── test/
        └── java/
            └── com/
                └── drivingsimulation/
                    ├── CollisionTest.java
                    ├── DirectionTest.java
                    ├── FieldTest.java
                    ├── PositionTest.java
                    ├── application/
                    │   └── SimulationApplicationServiceTest.java
                    ├── cli/
                    │   ├── InputReaderTest.java
                    │   └── OutputFormatterTest.java
                    └── simulation/
                        ├── CarTest.java
                        └── SimulationEngineTest.java
```

## Design Approach

The application separates responsibilities into different layers:

* **Domain** - Represents cars, positions, directions, and the simulation field.
* **Simulation** - Handles movement execution and collision detection.
* **Application** - Coordinates the simulation use cases.
* **CLI** - Handles user input and console output.

The implementation uses object-oriented principles such as:

* Encapsulation
* Single Responsibility
* Immutable value objects where appropriate
* Enum-based direction handling
* Separation of domain logic from CLI concerns
* Unit testing with JUnit 5

## Running from a Fresh Download

After downloading and extracting the repository:

1. Install Java 21.
2. Open a terminal in the project root.
3. Run the tests:

```bash
./gradlew clean test
```

4. Start the application:

```bash
./gradlew run
```

On Windows Command Prompt:

```cmd
gradlew.bat clean test
gradlew.bat run
```

On Windows PowerShell:

```powershell
.\gradlew.bat clean test
.\gradlew.bat run
```

No Git Bash or separate Gradle installation is required on Windows.

## License

This project was created as part of a coding assignment.
