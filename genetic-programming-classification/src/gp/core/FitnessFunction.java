package gp.core;

@FunctionalInterface
public interface FitnessFunction {
    /**
     * Evaluate the tree on the whole dataset.
     * The assignment specifies this must map to Accuracy[cite: 1].
     */
    double evaluate(GPNode root, double[][] features, int[] labels);
}