package gp.core;

import gp.core.CSVLoader.Dataset;
import java.io.*;
import java.util.*;

/**
 * Automates the 30 independent runs required by the assignment specs.
 * This script will generate the data needed for your Statistical Test
 * (T-test/Wilcoxon).
 */
public class ExperimentRunner {

    public static void main(String[] args) throws Exception {
        // Grab the training file from the command line arguments
        String trainFile = args[0];

        // Spec 100% compliance: "perform 30 independent runs"
        int runs = 30;

        // Store results of each run to calculate Mean and Standard Deviation later
        List<Double> trainFitness = new ArrayList<>();
        List<Double> testAcc = new ArrayList<>();

        System.out.println("Starting " + runs + " independent runs on " + trainFile + "...\n");

        for (int run = 0; run < runs; run++) {
            // Spec 100% compliance: "each run must use a unique seed value"
            // By multiplying the run index by a prime-like number, we guarantee a unique,
            // replicable seed.
            long seed = run * 12345L;
            Random rng = new Random(seed);

            // Initialize config with this run's unique random number generator
            GPConfig config = new GPConfig(rng);
            setupSymbols(config); // (Person 2 and 3 will configure their specific math/logic nodes here)

            // Load the dataset
            Dataset train = CSVLoader.load(trainFile);

            // Define fitness function: For this assignment, fitness = Accuracy
            FitnessFunction fitness = (root, feats, lbls) -> Metrics.accuracy(lbls, Metrics.predict(root, feats));

            // Initialize the GP engine (Person 1's core logic)
            GPEngine engine = new GPEngine(config, fitness);

            // Run the evolution!
            Individual best = engine.evolve(train.features, train.labels);

            // Record the best fitness from this specific run
            trainFitness.add(best.getFitness());

            // Console output for the demo
            System.out.printf("Run %2d: Seed = %d | Best Training Fitness (Accuracy) = %.4f%n", run + 1, seed,
                    best.getFitness());
        }

        // Calculate and display aggregated statistics for the final report
        double mean = trainFitness.stream().mapToDouble(v -> v).average().orElse(0);

        // Standard Deviation calculation
        double std = Math.sqrt(trainFitness.stream().mapToDouble(v -> Math.pow(v - mean, 2)).average().orElse(0));

        System.out.println("\n=== FINAL RESULTS ===");
        System.out.printf("Over %d runs: Mean Fitness = %.4f ± %.4f%n", runs, mean, std);
        System.out.println("Use these metrics for your final report comparison table!");
    }

    private static void setupSymbols(GPConfig config) {
        /*
         * This method will be filled in by Person 2 (Arithmetic) and Person 3 (Decision
         * Trees).
         * They will inject their nodes (AddNode, IfNode, etc.) into config.functionSet
         * here.
         * Same setup as found in GPRunner.
         */
    }
}