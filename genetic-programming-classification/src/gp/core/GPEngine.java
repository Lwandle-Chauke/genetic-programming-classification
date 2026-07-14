package gp.core;

import java.util.*;
import java.util.stream.Collectors;

public class GPEngine {
    private GPConfig config;
    private FitnessFunction fitnessFunc;

    public GPEngine(GPConfig config, FitnessFunction fitnessFunc) {
        this.config = config;
        this.fitnessFunc = fitnessFunc;
    }

    public Individual evolve(double[][] trainFeatures, int[] trainLabels) {
        // 1. Initialize Population using Ramped Half-and-Half
        List<Individual> population = TreeGenerator.generateRampedHalfAndHalf(config, config.populationSize);
        evaluatePopulation(population, trainFeatures, trainLabels);

        // Keep track of the best individual ever seen
        Individual bestSoFar = getBest(population);
        if (bestSoFar == null) {
            GPNode tree = TreeGenerator.growTree(config, config.initMaxDepth, false);
            bestSoFar = new Individual(tree);
            bestSoFar.setFitness(safeEvaluate(tree, trainFeatures, trainLabels));
        }

        // 2. Main Evolutionary Loop
        for (int gen = 0; gen < config.maxGenerations; gen++) {
            List<Individual> newPop = new ArrayList<>();

            // Elitism: Copy the best N individuals straight to the next generation
            List<Individual> sorted = population.stream()
                    .filter(ind -> Double.isFinite(ind.getFitness()))
                    .sorted(Comparator.comparingDouble(Individual::getFitness).reversed())
                    .collect(Collectors.toList());
            for (int i = 0; i < config.eliteCount && i < sorted.size(); i++)
                newPop.add(sorted.get(i).copy());

            // 3. Breed the rest of the new generation
            while (newPop.size() < config.populationSize) {
                // Select parents via Tournament
                Individual p1 = Selection.tournament(population, config.tournamentSize, config.rng);
                Individual p2 = Selection.tournament(population, config.tournamentSize, config.rng);
                Individual child1, child2;

                // Crossover
                if (config.rng.nextDouble() < config.crossoverProb) {
                    Individual[] kids = Crossover.crossover(p1, p2, config);
                    if (kids != null) {
                        child1 = kids[0];
                        child2 = kids[1];
                    } else { // Fallback if crossover continually violates depth
                        child1 = p1.copy();
                        child2 = p2.copy();
                    }
                } else {
                    child1 = p1.copy();
                    child2 = p2.copy();
                }

                // Mutation
                if (config.rng.nextDouble() < config.mutationProb) {
                    Individual m = Mutation.mutate(child1, config);
                    if (m != null)
                        child1 = m;
                }
                if (config.rng.nextDouble() < config.mutationProb) {
                    Individual m = Mutation.mutate(child2, config);
                    if (m != null)
                        child2 = m;
                }

                // Add to new population
                newPop.add(child1);
                if (newPop.size() < config.populationSize)
                    newPop.add(child2);
            }

            // 4. Evaluate new generation
            evaluatePopulation(newPop, trainFeatures, trainLabels);
            population = newPop;

            // 5. Check for new global best
            Individual genBest = getBest(population);
            if (genBest != null && (bestSoFar == null || genBest.getFitness() > bestSoFar.getFitness()))
                bestSoFar = genBest.copy();

            // Print stats as required by the "Training Demonstration" spec[cite: 1]
            double avg = averageFitness(population);
            double bestFit = bestSoFar != null ? bestSoFar.getFitness() : 0.0;
            System.out.printf("Gen %d: best = %.4f, avg = %.4f%n", gen, bestFit, avg);
            System.out.println("Best tree: " + bestSoFar.getRoot()); // <-- ADD THIS
        }
        return bestSoFar;
    }

    private void evaluatePopulation(List<Individual> pop, double[][] features, int[] labels) {
        for (Individual ind : pop)
            ind.setFitness(safeEvaluate(ind.getRoot(), features, labels));
    }

    // Safety wrapper to prevent math errors (like divide by zero) from crashing
    // evolution
    private double safeEvaluate(GPNode root, double[][] features, int[] labels) {
        try {
            double fit = fitnessFunc.evaluate(root, features, labels);
            return Double.isFinite(fit) ? fit : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    private Individual getBest(List<Individual> pop) {
        return pop.stream()
                .filter(ind -> Double.isFinite(ind.getFitness()))
                .max(Comparator.comparingDouble(Individual::getFitness))
                .orElse(null);
    }

    private double averageFitness(List<Individual> pop) {
        return pop.stream()
                .filter(ind -> Double.isFinite(ind.getFitness()))
                .mapToDouble(Individual::getFitness)
                .average().orElse(0.0);
    }
}