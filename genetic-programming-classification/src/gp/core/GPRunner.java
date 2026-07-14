package gp.core;

import gp.core.CSVLoader.Dataset;
import java.util.*;

public class GPRunner {
    public static void main(String[] args) throws Exception {
        Map<String, String> params = parseArgs(args);
        String trainFile = params.get("--train");
        String testFile = params.get("--test");
        String modelFile = params.get("--model");
        long seed = params.containsKey("--seed") ? Long.parseLong(params.get("--seed")) : System.currentTimeMillis();

        Random rng = new Random(seed);
        GPConfig config = new GPConfig(rng);
        setupSymbols(config); // define functions & terminals for your problem

        if (trainFile != null) {
            Dataset train = CSVLoader.load(trainFile);
            FitnessFunction fitness = (root, feats, lbls) -> {
                int[] pred = Metrics.predict(root, feats);
                return Metrics.accuracy(lbls, pred);
            };
            GPEngine engine = new GPEngine(config, fitness);
            Individual best = engine.evolve(train.features, train.labels);
            if (modelFile != null)
                ModelSerializer.save(best, modelFile);
            System.out.println("Best training fitness: " + best.getFitness());
        }

        if (testFile != null && modelFile != null) {
            Individual model = ModelSerializer.load(modelFile);
            Dataset test = CSVLoader.load(testFile);
            int[] pred = Metrics.predict(model.getRoot(), test.features);
            double acc = Metrics.accuracy(test.labels, pred);
            double f1 = Metrics.f1Score(test.labels, pred, 1);
            System.out.printf("Test accuracy = %.4f, F1 = %.4f%n", acc, f1);
        }
    }

    private static void setupSymbols(GPConfig config) {
        // Function set – Person 2 will add more operators ( -, *, / )
        config.functionSet = Arrays.asList(new AddNode(null, null));

        // Terminal set: all 10 features + constants
        List<GPNode> terminals = new ArrayList<>();

        // Add all 10 feature nodes (indices 0 .. 9)
        for (int i = 0; i < 10; i++) {
            terminals.add(new FeatureNode(i));
        }

        // Add constant terminals (range -5.0 to 5.0 step 1.0)
        for (double c = -5.0; c <= 5.0; c += 1.0) {
            terminals.add(new ConstNode(c));
        }

        config.terminalSet = terminals;
    }

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length; i += 2)
            if (i + 1 < args.length)
                map.put(args[i], args[i + 1]);
        return map;
    }
}