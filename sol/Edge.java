package sol;

/**
 * A class that connects ITreeNodes
 */
public class Edge {

    private String edge;
    private ITreeNode next;

    /**
     * A constructor for edge
     * @param edge the attribute value for the prior node
     * @param next the next ITreeNode
     */
    public Edge(String edge, ITreeNode next) {
        this.edge = edge;
        this.next = next;
    }

    /**
     * getter for edge
     * @return the edge field
     */
    public String getEdge() {
        return this.edge;
    }

    /**
     * getter for next
     * @return the next field
     */
    public ITreeNode getNext(){
        return this.next;
    }

    /**
     *
     * @return the string representation of the
     */
    @Override
    public String toString() {
        return "-" + this.edge + "-" + this.next;
    }
}
