package gp.core;

import java.util.List;
import java.util.Random;

/**
 * Central configuration object holding all hyperparameters.
 */
public class GPConfig {
    // Population parameters – specifically requested to be 200 by the assignment
    // spec!
    public int populationSize = 200;
    public int maxGenerations = 100; // Max generations from the spec

    // Depth control parameters to prevent code bloat
    public int maxDepth = 8;
    public int initMinDepth = 2;
    public int initMaxDepth = 6;

    // Genetic operator probabilities (These are design decisions 'dd')
    public double crossoverProb = 0.85; // 85% chance to crossover
    public double mutationProb = 0.15; // 15% chance to mutate
    public int tournamentSize = 7; // Selection tournament size
    public int eliteCount = 2; // Keep best 2 automatically (Elitism)
    public int maxRetries = 20; // Max attempts to fix depth constraint violations

    // Function and terminal sets (Populated by Person 2 and Person 3)
    public List<GPNode> functionSet;
    public List<GPNode> terminalSet;

    // The shared Random Number Generator (seeded for reproducibility as required)
    public Random rng;

    public GPConfig(Random rng) {
        this.rng = rng;
    }

    // Utility methods to grab a random node type
    public GPNode randomFunction() {
        return functionSet.get(rng.nextInt(functionSet.size())).copy();
    }

    public GPNode randomTerminal() {
        return terminalSet.get(rng.nextInt(terminalSet.size())).copy();
    }
}