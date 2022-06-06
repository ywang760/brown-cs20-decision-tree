package sol;

import src.ITreeGenerator;
import src.Row;
import java.util.LinkedList;
import java.util.Random;

/**
 * A class that implements the ITreeGenerator interface
 * used to generate a tree
 */
public class TreeGenerator implements ITreeGenerator<Dataset> {

    private ITreeNode root;

    /**
     * Helper for generateTree, which generates the tree for a given
     * target attribute
     * @param trainingData the dataset to train on
     * @param targetAtt the attribute to predict
     * @return the tree based on the training data and target attribute
     */
    public ITreeNode generateTreeHelper(Dataset trainingData, String targetAtt) {
        if (trainingData.checkOutcome(targetAtt) || trainingData.attListNoTarget(targetAtt).size() == 0) {
            return new Leaf(trainingData.getLeafDecision(targetAtt));
        } else {

            // generate random index
            Random number = new Random();
            int upperBound = trainingData.attListNoTarget(targetAtt).size();
            int randomIndex = number.nextInt(upperBound);
//            System.out.println(randomIndex);
//
//            System.out.println(trainingData);
//            System.out.println(trainingData.attListNoTarget(targetAtt));
            String att = trainingData.attListNoTarget(targetAtt).get(randomIndex); //attribute to get rid of at this level

            LinkedList<Dataset> datasetList = trainingData.divideDataset(att);
            LinkedList<Edge> edgeList = new LinkedList<Edge>();
            String df = trainingData.getDefault(targetAtt);
            for (Dataset ds : datasetList) {
                String attValue = ds.getDataObjects().get(0).getAttributeValue(att);
                Edge newEdge = new Edge(attValue, this.generateTreeHelper(ds, targetAtt));
                edgeList.add(newEdge);
            }
            return new Node(att, edgeList, df);
        }
    }


    /**
     * Generates the tree for a given training dataset.
     *
     * @param trainingData    the dataset to train on
     * @param targetAttribute the attribute to predict
     */
    @Override
    public void generateTree(Dataset trainingData, String targetAttribute) {
        this.root = this.generateTreeHelper(trainingData, targetAttribute);
    }

    /**
     * Looks up the decision for a datum in the decision tree.
     *
     * @param datum the datum to lookup a decision for
     * @return the decision of the row
     */
    @Override
    public String getDecision(Row datum) {
       return this.root.getDecision(datum);
    }

}
