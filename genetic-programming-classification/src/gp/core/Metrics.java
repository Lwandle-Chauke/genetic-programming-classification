package gp.core;

public class Metrics {
    public static double accuracy(int[] trueLabels, int[] predictions) {
        int correct = 0;
        for (int i = 0; i < trueLabels.length; i++)
            if (trueLabels[i] == predictions[i])
                correct++;
        return (double) correct / trueLabels.length;
    }

    public static double f1Score(int[] trueLabels, int[] predictions, int positiveClass) {
        int tp = 0, fp = 0, fn = 0;
        for (int i = 0; i < trueLabels.length; i++) {
            if (trueLabels[i] == positiveClass && predictions[i] == positiveClass)
                tp++;
            else if (trueLabels[i] != positiveClass && predictions[i] == positiveClass)
                fp++;
            else if (trueLabels[i] == positiveClass && predictions[i] != positiveClass)
                fn++;
        }
        double precision = (tp + fp == 0) ? 0 : (double) tp / (tp + fp);
        double recall = (tp + fn == 0) ? 0 : (double) tp / (tp + fn);
        return (precision + recall == 0) ? 0 : 2 * precision * recall / (precision + recall);
    }

    public static int[] predict(GPNode root, double[][] features) {
        int[] preds = new int[features.length];
        for (int i = 0; i < features.length; i++) {
            double val = root.evaluate(features[i]);
            preds[i] = (val >= 0.5) ? 1 : 0; // assuming binary classification
        }
        return preds;
    }
}