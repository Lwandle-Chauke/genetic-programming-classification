package gp.core;

public class AddNode implements GPNode {
    private GPNode left, right;

    public AddNode(GPNode l, GPNode r) {
        left = l;
        right = r;
    }

    public double evaluate(double[] features) {
        return left.evaluate(features) + right.evaluate(features);
    }

    public GPNode copy() {
        GPNode leftCopy = (left == null) ? null : left.copy();
        GPNode rightCopy = (right == null) ? null : right.copy();
        return new AddNode(leftCopy, rightCopy);
    }

    public int getArity() {
        return 2;
    }

    public GPNode getChild(int i) {
        return i == 0 ? left : right;
    }

    public void setChild(int i, GPNode child) {
        if (i == 0)
            left = child;
        else
            right = child;
    }

    public String toString() {
        return "(+ " + left + " " + right + ")";
    }
}