# 🧬 GP Core Engine (Person 1 Framework)

**Project:** COS314 Assignment 3 - Breast Cancer Classification  
**Role:** Core GP Engine (Foundation)  

## 📝 Overview
This repository contains the foundational Genetic Programming (GP) Engine. It provides the reusable backbone that will be used by both the Arithmetic Classifier (Person 2) and the Decision Tree Classifier (Person 3). 

Currently, the engine is fully functional and uses a placeholder `AddNode` and constants to demonstrate the successful implementation of the evolutionary loop, genetic operators, and automated experiment running.

---

## 🛠️ What I Built (My Responsibilities)

I have successfully implemented all requirements specified for **Person 1**:

*   **Tree Data Structure & Node Representation:** Built the highly extensible `GPNode` interface and `Individual` wrapper. Provided baseline implementations (`FeatureNode`, `ConstNode`, `AddNode`).
*   **Initialization:** Implemented the required **Ramped Half-and-Half** random tree generation method (`TreeGenerator.java`).
*   **Fitness Evaluation:** Implemented the `FitnessFunction` mapping to predictive Accuracy (`Metrics.java`).
*   **Selection:** Implemented **Tournament Selection** to pick the best candidates for breeding without premature convergence (`Selection.java`).
*   **Crossover:** Implemented Subtree Crossover (`Crossover.java`).
*   **Mutation:** Implemented **Point Mutation** which replaces a node with a random node of the *same arity* while keeping the original children intact (`Mutation.java`).
*   **Depth Control:** Enforced strict max-depth constraints (`maxDepth = 8`) during initialization, crossover, and mutation to prevent code bloat.
*   **Shared Responsibilities Handled:** 
    *   Data Preprocessing: `CSVLoader.java` handles missing headers and target variable splitting.
    *   Automated Experiments: `ExperimentRunner.java` automatically executes 30 independent runs with unique seeds.
    *   Metrics Tracking: Calculates and displays Mean Fitness, Standard Deviation, Accuracy, and F1-Score.

---

## 📁 File Structure
```text
ProjectRoot/
│
├── README.md
├── Breast_train.csv      
├── Breast_test.csv        
│
└── src/
    └── gp/
        └── core/
            ├── AddNode.java           (Template for Person 2)
            ├── ConstNode.java         (Terminal Node)
            ├── Crossover.java         (Genetic Operator)
            ├── CSVLoader.java         (Data Pre-processing)
            ├── ExperimentRunner.java  (Automates 30 runs)
            ├── FeatureNode.java       (Terminal Node)
            ├── FitnessFunction.java   (Evaluation Interface)
            ├── GPConfig.java          (Hyperparameters)
            ├── GPEngine.java          (Main Evolutionary Loop)
            ├── GPNode.java            (Core Interface)
            ├── GPRunner.java          (Training Demo & Testing)
            ├── Individual.java        (Population Member Wrapper)
            ├── Metrics.java           (Accuracy & F1 Math)
            ├── ModelSerializer.java   (Save/Load Models)
            ├── Mutation.java          (Genetic Operator)
            ├── Selection.java         (Tournament)
            └── TreeGenerator.java     (Ramped Half-and-Half)
```

---

## ⚙️ How to Compile

This framework requires **Java 8+**. No external libraries are needed.

1. Open your terminal/command prompt.
2. Navigate to the root directory of this project.
3. Compile all files into an `out` folder:
```bash
mkdir out
javac -d out src/gp/core/*.java
```

---

## 🚀 How to Run & Test My Code

### 1. Training Demonstration (Single Run)
To prove the evolutionary loop works, this command will run a single instance of 100 generations. It will print the Best and Average fitness per generation, display the best mathematical tree, and output final test metrics.
```bash
java -cp out gp.core.GPRunner --train Breast_train.csv --test Breast_test.csv --seed 12345
```

### 2. 30 Independent Experiments
To run the 30 independent experiments required by the assignment (using unique seeds for each run) and generate the statistical data (Mean & Standard Deviation) for the final report:
```bash
java -cp out gp.core.ExperimentRunner Breast_train.csv
```

---