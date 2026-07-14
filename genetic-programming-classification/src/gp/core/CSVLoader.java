package gp.core;

import java.io.*;
import java.util.*;

public class CSVLoader {

    public static Dataset load(String filePath) throws IOException {
        List<double[]> featList = new ArrayList<>();
        List<Integer> labelList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true; // <-- THE FIX: Flag to track the first line

            while ((line = br.readLine()) != null) {
                // 1. Skip the header row (the row with "class", "age", etc.)
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // 2. Skip empty lines
                if (line.trim().isEmpty())
                    continue;

                String[] parts = line.split(",");

                // 3. Parse features
                double[] feats = new double[parts.length - 1];
                for (int i = 0; i < feats.length; i++) {
                    feats[i] = Double.parseDouble(parts[i]);
                }

                // 4. Parse label (last column)
                int label = Integer.parseInt(parts[parts.length - 1]);

                featList.add(feats);
                labelList.add(label);
            }
        }

        double[][] features = featList.toArray(new double[0][]);
        int[] labels = labelList.stream().mapToInt(i -> i).toArray();
        return new Dataset(features, labels);
    }

    public static class Dataset {
        public final double[][] features;
        public final int[] labels;

        public Dataset(double[][] f, int[] l) {
            features = f;
            labels = l;
        }
    }
}