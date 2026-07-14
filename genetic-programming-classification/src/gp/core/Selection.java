package gp.core;

import java.util.List;
import java.util.Random;

public class Selection {
    /**
     * Tournament Selection: Picks N random individuals and returns the best one.
     * This prevents premature convergence compared to picking the absolute best
     * every time.
     */
    public static Individual tournament(List<Individual> population, int tournamentSize, Random rng) {
        Individual best = null;
        for (int i = 0; i < tournamentSize; i++) {
            // Pick a random contender from the population
            Individual contender = population.get(rng.nextInt(population.size()));

            // If it's our first pick, or it beats the current best, it becomes the new best
            if (best == null || contender.getFitness() > best.getFitness())
                best = contender;
        }
        // Return a COPY so the parent remains unaltered in the main population
        return best.copy();
    }
}