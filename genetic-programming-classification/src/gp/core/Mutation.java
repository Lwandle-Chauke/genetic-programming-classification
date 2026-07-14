package gp.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutation {

    /**
     * Point mutation required by spec: replace a randomly chosen node with another
     * node of the SAME arity.
     */
    public static Individual mutate(Individual individual, GPConfig config) {
        Individual mutant = individual.copy();

        for (int attempt = 0; attempt < config.maxRetries; attempt++) {
            // Pick a random node to mutate
            GPNode target = selectRandomNode(mutant.getRoot(), config.rng);
            GPNode replacement;

            if (target.getArity() == 0) {
                // It's a Terminal (Feature or Constant). Swap it with another random Terminal.
                replacement = config.randomTerminal();
            } else {
                // It's a Function (+, -, *, /). Swap it with a function of the SAME arity.
                List<GPNode> candidates = new ArrayList<>();
                for (GPNode fn : config.functionSet) {
                    if (fn.getArity() == target.getArity()) {
                        candidates.add(fn);
                    }
                }
                if (candidates.isEmpty())
                    continue;

                replacement = candidates.get(config.rng.nextInt(candidates.size())).copy();

                // CRITICAL FOR POINT MUTATION: Reattach the existing children to the new node
                for (int i = 0; i < target.getArity(); i++) {
                    replacement.setChild(i, target.getChild(i));
                }
            }

            boolean replaced = replaceNode(mutant.getRoot(), target, replacement);
            if (!replaced)
                continue;

            // DEPTH CONTROL CHECK
            if (mutant.getRoot().depth() <= config.maxDepth) {
                return mutant; // Success
            }

            // Depth violation: Reset mutant and try again
            mutant = individual.copy();
        }
        return null;
    }

    // Helper methods (Same logic as Crossover, used for tree traversal)
    private static GPNode selectRandomNode(GPNode root, Random rng) {
        List<GPNode> nodes = new ArrayList<>();
        collectNodes(root, nodes);
        return nodes.get(rng.nextInt(nodes.size()));
    }

    private static void collectNodes(GPNode node, List<GPNode> nodes) {
        nodes.add(node);
        for (int i = 0; i < node.getArity(); i++) {
            GPNode child = node.getChild(i);
            if (child != null)
                collectNodes(child, nodes);
        }
    }

    private static boolean replaceNode(GPNode root, GPNode target, GPNode newNode) {
        if (root == target)
            return false;
        return replaceInChildren(root, target, newNode);
    }

    private static boolean replaceInChildren(GPNode parent, GPNode target, GPNode newNode) {
        for (int i = 0; i < parent.getArity(); i++) {
            GPNode child = parent.getChild(i);
            if (child == target) {
                parent.setChild(i, newNode);
                return true;
            }
            if (child != null && replaceInChildren(child, target, newNode))
                return true;
        }
        return false;
    }
}