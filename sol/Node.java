package sol;

import src.Row;
import java.util.LinkedList;

/**
 * A class representing a tree part that doesn't contain a final decision
 */
public class Node implements ITreeNode{

    private String attribute;
    private LinkedList<Edge> childEdges;
    private String defaultDecision;

    public Node(String attribute, LinkedList<Edge> childEdge, String defaultD) {
        this.attribute = attribute;
        this.childEdges = childEdge;
        this.defaultDecision = defaultD;
    }

    /**
     * Recursively traverses decision tree to return tree's decision for a row.
     *
     * @param forDatum the datum to lookup a decision for
     * @return the decision tree's decision
     */
    @Override
    public String getDecision(Row forDatum) {
        String i = forDatum.getAttributeValue(this.attribute);
        for (Edge e : this.childEdges)
        { if (i.equals(e.getEdge()))
            {return e.getNext().getDecision(forDatum);}
        } return this.defaultDecision;
    }

    /**
     * a helper that prints out the node
     * @return "Node:", its attribute, and its child edges
     */
    @Override
    public String toString() {
        String output = "Node: ";
        output += this.attribute + ", " + this.childEdges;
        return output;
    }

}
