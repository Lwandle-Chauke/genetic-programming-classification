package gp.core;

import java.io.*;

public class ModelSerializer {
    public static void save(Individual individual, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(individual);
        }
    }

    public static Individual load(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Individual) ois.readObject();
        }
    }
}