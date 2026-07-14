package gp.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the Ramped Half-and-Half initialization required by the assignment.
 */
public class TreeGenerator {

    /**
     * Generates a population where trees have varying depths, and half are "full"
     * (all branches go to max depth) and half are "grow" (branches can end early).
     */
    public static List<Individual> generateRampedHalfAndHalf(GPConfig config, int populationSize) {
        List<Individual> pop = new ArrayList<>();
        int depthRange = config.initMaxDepth - config.initMinDepth + 1;
        int perDepth = populationSize / depthRange; // Evenly distribute across depth ranges
        int remainder = populationSize % depthRange;

        for (int depth = config.initMinDepth; depth <= config.initMaxDepth; depth++) {
            int count = perDepth + (remainder-- > 0 ? 1 : 0);
            int half = count / 2;

            // First half: Full trees (forces terminals only at the very bottom)
            for (int i = 0; i < half; i++)
                pop.add(new Individual(growTree(config, depth, true)));

            // Second half: Grow trees (terminals can appear before max depth)
            for (int i = half; i < count; i++)
                pop.add(new Individual(growTree(config, depth, false)));
        }
        return pop;
    }

    public static GPNode growTree(GPConfig config, int maxDepth, boolean fullMode) {
        return growRecursive(config, 0, maxDepth, fullMode);
    }

    private static GPNode growRecursive(GPConfig config, int currDepth, int maxDepth, boolean fullMode) {
        // If we hit the depth limit, we MUST pick a terminal (feature or constant)
        boolean forceTerminal = (currDepth >= maxDepth);
        boolean pickTerminal = forceTerminal;

        // If we are in "grow" mode, we might pick a terminal early
        if (!fullMode && !forceTerminal) {
            double prob = 0.1 + 0.4 * ((double) currDepth / maxDepth);
            pickTerminal = config.rng.nextDouble() < prob;
        }

        if (pickTerminal) {
            return config.randomTerminal();
        } else {
            // Otherwise, pick a function and recursively build its children
            GPNode node = config.randomFunction();
            for (int i = 0; i < node.getArity(); i++) {
                node.setChild(i, growRecursive(config, currDepth + 1, maxDepth, fullMode));
            }
            return node;
        }
    }
}