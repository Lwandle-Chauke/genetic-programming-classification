package gp.core;

import java.io.Serializable;

/**
 * Represents a single program/tree in the population.
 * Implements Comparable to easily sort the population by fitness.
 */
public class Individual implements Serializable, Comparable<Individual> {
    private static final long serialVersionUID = 1L;

    private GPNode root; // The top node of this individual's tree
    private double fitness; // Training accuracy
    private double testFitness; // Testing accuracy

    public Individual(GPNode root) {
        this.root = root;
        this.fitness = Double.NaN;
        this.testFitness = Double.NaN;
    }

    public GPNode getRoot() {
        return root;
    }

    public void setRoot(GPNode root) {
        this.root = root;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getTestFitness() {
        return testFitness;
    }

    public void setTestFitness(double testFitness) {
        this.testFitness = testFitness;
    }

    /**
     * Creates a deep copy of the individual.
     * Crucial for Elitism and Crossover to prevent reference leaking.
     */
    public Individual copy() {
        Individual copy = new Individual(root.copy());
        copy.setFitness(this.fitness);
        copy.setTestFitness(this.testFitness);
        return copy;
    }

    /** Sorts individuals in descending order (highest fitness first). */
    @Override
    public int compareTo(Individual o) {
        return Double.compare(o.fitness, this.fitness);
    }

    @Override
    public String toString() {
        return root.toString() + " [fitness=" + fitness + "]";
    }
}