<div align="center">

# Genetic Programming Classification

### Comparative implementation of Symbolic Genetic Programming and Decision Tree Genetic Programming for medical classification using Java.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Artificial Intelligence](https://img.shields.io/badge/Artificial%20Intelligence-4285F4?style=for-the-badge)
![Machine Learning](https://img.shields.io/badge/Machine%20Learning-43A047?style=for-the-badge)
![Genetic Programming](https://img.shields.io/badge/Genetic%20Programming-8E24AA?style=for-the-badge)

**Author:** **Lwandle Chauke and Team**

</div>

---

# Overview

This project investigates the application of **Genetic Programming (GP)** for supervised classification.

Two independent Genetic Programming approaches were implemented and compared using the Breast Cancer Wisconsin Diagnostic Dataset:

- Symbolic Genetic Programming
- Decision Tree Genetic Programming

The objective was to evaluate how different GP representations perform when classifying unseen medical data while comparing accuracy, F-measure, runtime and statistical significance.

---

# Project Highlights

- Genetic Programming implementation
- Decision Tree evolution
- Symbolic expression evolution
- Classification performance evaluation
- Statistical significance testing
- Seed-based reproducible experiments
- Medical dataset classification
- Comprehensive technical documentation

---

# Algorithms Implemented

## Symbolic Genetic Programming

Individuals are represented as mathematical expressions constructed from arithmetic operators.

The algorithm evolves expressions capable of classifying breast cancer cases through evolutionary optimisation.

Features include:

- Population evolution
- Tournament selection
- Tree crossover
- Point mutation
- Fitness-based selection
- Expression evaluation

---

## Decision Tree Genetic Programming

Individuals evolve as decision trees instead of mathematical expressions.

Each generation attempts to improve classification accuracy while maintaining valid tree structures.

Features include:

- Tree evolution
- Decision node optimisation
- Fitness evaluation
- Genetic operators
- Controlled tree depth

---

# Dataset

The project uses the **Breast Cancer Wisconsin Diagnostic Dataset**.

The dataset contains patient diagnostic features used to classify:

- Non-recurrence
- Recurrence

Each instance contains multiple clinical attributes that were encoded into numerical values before training.

---

# Evaluation Metrics

Both Genetic Programming approaches were evaluated using:

- Training Accuracy
- Test Accuracy
- F-Measure
- Runtime
- Statistical Significance

---

# Experimental Configuration

Key evolutionary parameters included:

| Parameter | Value |
|------------|-------|
| Population Size | 200 |
| Initialisation | Ramped Half-and-Half |
| Mutation | Point Mutation |
| Fitness Function | Classification Accuracy |
| Maximum Generations | 100 |

Several additional parameters such as crossover rate, tournament size and tree depth were experimentally configured during development.

---

# Experimental Process

Each algorithm was evaluated through:

- 30 independent runs
- Unique random seed values
- Selection of the best-performing evolved model
- Testing using unseen data

This ensured fair comparison and reproducibility.

---

# Tech Stack

- Java
- Object-Oriented Programming
- Artificial Intelligence
- Machine Learning
- Genetic Programming
- Evolutionary Computing

---

# Repository Structure

```text
genetic-programming-classification/

├── src/
│
├── datasets/
│   ├── train.csv
│   └── test.csv
│
├── docs/
│   ├── Project-Report.pdf
│   ├── Experimental-Results.pdf
│   └── Statistical-Analysis.pdf
│
├── README.md
├── .gitignore
└── LICENSE
```

---

# ⚙️ Running the Project

Clone the repository

```bash
git clone https://github.com/Lwandle-Chauke/genetic-programming-classification.git
```

Compile

```bash
javac Main.java
```

Run

```bash
java Main
```

The application requests:

- Dataset path
- Random seed
- Algorithm selection

before beginning the evolutionary process.

---

# Documentation

The repository includes:

- Model design
- Evolutionary configuration
- Experimental setup
- Performance comparison
- Statistical significance analysis
- Final conclusions

---

# Key Concepts Demonstrated

- Artificial Intelligence
- Machine Learning
- Evolutionary Algorithms
- Genetic Programming
- Decision Trees
- Supervised Classification
- Performance Evaluation
- Statistical Analysis

---

# Team Project

This project was completed collaboratively as part of a university team assignment.

It demonstrates experience developing AI solutions within a team environment while evaluating multiple evolutionary learning approaches.

---

# What I Learned

This project significantly strengthened my understanding of:

- Genetic Programming
- Evolutionary Machine Learning
- Tree-based learning
- Symbolic computation
- Classification metrics
- Experimental evaluation
- Statistical significance testing
- Java software development

It also deepened my appreciation for how evolutionary algorithms can be applied to supervised learning problems.

---

# Future Improvements

Potential future enhancements include:

- Multi-objective Genetic Programming
- Ensemble classifiers
- Random Forest comparison
- Support Vector Machine comparison
- Neural Network benchmark
- Hyperparameter optimisation
- Cross-validation support
- Interactive visualisation of evolved trees

---

# About Me

I'm **Lwandle Chauke**, a Computer Science graduate with interests in:

- Artificial Intelligence
- Machine Learning
- Cybersecurity
- Software Engineering
- Evolutionary Computing

I enjoy building intelligent systems and exploring computational techniques for solving complex real-world problems.

**GitHub**

https://github.com/Lwandle-Chauke

---

<div align="center">

If you found this project interesting, feel free to star the repository!

</div>
