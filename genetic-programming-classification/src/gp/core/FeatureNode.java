package gp.core;

public class FeatureNode implements GPNode {
    private int index;

    public FeatureNode(int idx) {
        index = idx;
    }

    public double evaluate(double[] features) {
        return features[index];
    }

    public GPNode copy() {
        return new FeatureNode(index);
    }

    public int getArity() {
        return 0;
    }

    public GPNode getChild(int i) {
        throw new UnsupportedOperationException();
    }

    public void setChild(int i, GPNode child) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return "f[" + index + "]";
    }
}