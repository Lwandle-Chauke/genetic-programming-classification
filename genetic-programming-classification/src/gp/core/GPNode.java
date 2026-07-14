package gp.core;

import java.io.Serializable;

/**
 * Fundamental building block of a GP individual.
 * Every node in the expression tree implements this interface.
 * Person 2 (Arithmetic) and Person 3 (Decision Tree) will create classes that
 * implement this.
 */
public interface GPNode extends Serializable {

    /**
     * Evaluate this node on the given feature vector.
     * 
     * @param features array of input features from the CSV.
     * @return computed value (double for arithmetic, 0/1 for decision trees).
     */
    double evaluate(double[] features);

    /**
     * Deep copy – creates a distinct copy of the whole subtree.
     * CRITICAL for crossover and mutation so we don't accidentally modify parents!
     */
    GPNode copy();

    /**
     * Number of child nodes this node expects.
     * 0 for terminals (constants/features), 2 for binary operators (+, -, *, /).
     */
    int getArity();

    /** Get the child at the given index. */
    GPNode getChild(int index);

    /**
     * Set the child at given index.
     * Used heavily by Person 1 during tree building, crossover, and mutation.
     */
    void setChild(int index, GPNode child);

    /** Human-readable string representation for printing the final model. */
    String toString();

    /**
     * Compute subtree depth (number of edges from this node to deepest leaf).
     * This is VERY important for Depth Control during Crossover/Mutation.
     */
    default int depth() {
        int maxDepth = 0;
        for (int i = 0; i < getArity(); i++) {
            GPNode child = getChild(i);
            if (child != null) {
                maxDepth = Math.max(maxDepth, child.depth());
            }
        }
        return 1 + maxDepth;
    }

    /** Count total nodes in this subtree. Useful for tracking model complexity. */
    default int size() {
        int total = 1;
        for (int i = 0; i < getArity(); i++) {
            GPNode child = getChild(i);
            if (child != null) {
                total += child.size();
            }
        }
        return total;
    }
}