package sol;

import src.Row;

/**
 * A class representing a tree portion that contains a final decision
 */
public class Leaf implements ITreeNode{

    private String decision;

    /**
     * constructs a leaf
     * @param decision the final decision
     */
    public Leaf(String decision) {
        this.decision = decision;
    }

    /**
     * Recursively traverses decision tree to return tree's decision for a row.
     *
     * @param forDatum the datum to lookup a decision for
     * @return the decision tree's decision
     */
    @Override
    public String getDecision(Row forDatum) {
        return this.decision;
    }

    /**
     * a helper that prints out the leaf for testing
     * @return the string with ()
     */
    @Override
    public String toString() {
        return "(" + this.decision + ")";
    }
}
