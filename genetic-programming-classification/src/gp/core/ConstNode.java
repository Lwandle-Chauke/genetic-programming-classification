package gp.core;

public class ConstNode implements GPNode {
    private double value;

    public ConstNode(double val) {
        value = val;
    }

    public double evaluate(double[] features) {
        return value;
    }

    public GPNode copy() {
        return new ConstNode(value);
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
        return String.format("%.2f", value);
    }
}