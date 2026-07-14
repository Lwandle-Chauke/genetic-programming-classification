package gp.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Crossover {

    public static Individual[] crossover(Individual parent1, Individual parent2, GPConfig config) {
        Individual child1 = parent1.copy();
        Individual child2 = parent2.copy();

        // Try to crossover without violating the maxDepth constraint
        for (int attempt = 0; attempt < config.maxRetries; attempt++) {
            // Select a random crossover point in both children
            GPNode node1 = selectRandomNode(child1.getRoot(), config.rng);
            GPNode node2 = selectRandomNode(child2.getRoot(), config.rng);

            // Save copies of the subtrees we are about to swap
            GPNode savedSubtree1 = node1.copy();
            GPNode savedSubtree2 = node2.copy();

            // Perform the swap
            boolean success1 = replaceSubtree(child1.getRoot(), node1, savedSubtree2);
            boolean success2 = replaceSubtree(child2.getRoot(), node2, savedSubtree1);

            // If we failed to swap (e.g., tried to replace the root directly in a bad way),
            // revert and retry
            if (!success1 || !success2) {
                replaceSubtree(child1.getRoot(), node1, savedSubtree1);
                replaceSubtree(child2.getRoot(), node2, savedSubtree2);
                continue;
            }

            // DEPTH CONTROL CHECK: Ensure children didn't grow too large
            if (child1.getRoot().depth() <= config.maxDepth &&
                    child2.getRoot().depth() <= config.maxDepth) {
                return new Individual[] { child1, child2 }; // Success!
            }

            // Depth violation occurred. Revert the swap and try a different point.
            replaceSubtree(child1.getRoot(), node1, savedSubtree1);
            replaceSubtree(child2.getRoot(), node2, savedSubtree2);
        }
        // If we fail after maxRetries, return null (engine will fallback to using
        // copies of parents)
        return null;
    }

    // Helper: Grabs a random node from anywhere in the tree
    private static GPNode selectRandomNode(GPNode root, Random rng) {
        List<GPNode> nodes = new ArrayList<>();
        collectNodes(root, nodes);
        return nodes.get(rng.nextInt(nodes.size()));
    }

    // Helper: Recursively collects all nodes into a flat list
    private static void collectNodes(GPNode node, List<GPNode> nodes) {
        nodes.add(node);
        for (int i = 0; i < node.getArity(); i++) {
            GPNode child = node.getChild(i);
            if (child != null)
                collectNodes(child, nodes);
        }
    }

    // Helper: Finds the 'target' node and replaces it with 'newSubtree'
    private static boolean replaceSubtree(GPNode root, GPNode target, GPNode newSubtree) {
        if (root == target)
            return false; // Don't replace the absolute root
        return replaceInChildren(root, target, newSubtree);
    }

    private static boolean replaceInChildren(GPNode parent, GPNode target, GPNode newSubtree) {
        for (int i = 0; i < parent.getArity(); i++) {
            GPNode child = parent.getChild(i);
            if (child == target) {
                parent.setChild(i, newSubtree);
                return true;
            }
            if (child != null && replaceInChildren(child, target, newSubtree))
                return true;
        }
        return false;
    }
}