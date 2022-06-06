package sol;

import src.IDataset;
import src.Row;

import java.util.*;

/**
 * A class that implements the IDataset interface,
 * representing a training data set.
 */
public class Dataset implements IDataset {

    private List<String> attributeList;
    private List<Row> dataset;

    /**
     * constructs a dataset
     * @param attributeList the list of attributes involved in the dataset
     * @param dataset actual content of the dataset, represented as a list of
     *                rows
     */
    public Dataset(List<String> attributeList, List<Row> dataset) {
        this.attributeList = attributeList;
        this.dataset = dataset;
    }
    // when shuffling the dataset, remember only shuffle the first n-1 items
    // leaving the last item intact (because it is the decision)

    public List<String> attListNoTarget(String targetAttribute){
        List<String> attList = new LinkedList<String>();
        for (String att : this.attributeList) {
            if (!att.equals(targetAttribute)) {
                attList.add(att);
            }
        }
        return attList;
    }

    /**
     * getter for attributeList
     * @return the attributeList field
     */
    @Override
    public List<String> getAttributeList() {
        return this.attributeList;
    }

    /**
     * getter for dataset
     * @return the dataset field
     */
    @Override
    public List<Row> getDataObjects() {
        return this.dataset;
    }

    /**
     * size for dataset
     * @return the size of the dataset
     */
    @Override
    public int size() {
        return this.dataset.size();
    }

    /**
     * check if we need to make a node or a leaf
     *
     * @param targetAttribute the target attribute to compare to
     * @return true if all elements are the same (make a leaf), false otherwise
     * (make a node)
     */
    public Boolean checkOutcome(String targetAttribute){
        for (Row i : this.dataset) {
            if (!i.getAttributeValue(targetAttribute).
                    equals(this.getLeafDecision(targetAttribute)))
                return false;
        } return true;
    }

    /**
     * Get the values from all rows under this attribute, store it in a
     * linked list
     * @param att an attribute
     * @return values from all rows under this attribute
     */
    private LinkedList<String> getAttributeValues(String att){
        LinkedList<String> strings = new LinkedList<>();
        for (Row i : this.dataset){
            if (!strings.contains(i.getAttributeValue(att)))
            strings.addFirst(i.getAttributeValue(att));
        }
        return strings;
    }

    /**
     * divides the current dataset into a list of multiple dataset
     * @param att the attribute that the division is based on
     * @return a list of datasets, where each row in each dataset have the
     * same value for att. The rows in each dataset are remained, while
     * att is removed from the attributeList (so that it won't be re-selected
     * in later division stages)
     */
    public LinkedList<Dataset> divideDataset(String att){
        LinkedList<Dataset> newDatasetList = new LinkedList<>();
        for (String i : this.getAttributeValues(att)) {
            LinkedList<Row> newDatasetObjects = new LinkedList<>();
            for (Row row: this.dataset) {
                if (i.equals(row.getAttributeValue(att))) {
                    newDatasetObjects.addFirst(row);
                    // row.setAttributeValue(att, null);
                }
            }
            Dataset newDataSet = new Dataset(new ArrayList(newDatasetObjects.get(0).getAttributes()), newDatasetObjects);
            ArrayList<String> newAttributeList = new ArrayList<>();
            for (String att2 : this.attributeList) {
                if (!att.equals(att2)) {
                    newAttributeList.add(att2);
                }
            }
            newDataSet.attributeList = newAttributeList;
            newDatasetList.addFirst(newDataSet);
        }
        return newDatasetList;
    }

    /**
     * A helper that gets the value for a certain attribute, given that
     * all the rows have the same value for this attribute
     * @param att the attribute to get value for
     * @return the value of att from the first row in the dataset
     */
    public String getLeafDecision(String att) {
        return this.getDataObjects().get(0).getAttributeValue(att);
    }

    /**
     * to get the number of occurrences of a string from a linkedlist of strings
     * @param stringL the string list to consider
     * @param string the string to look for
     * @return the number of v in A
     */
    private int occurrenceCount(LinkedList<String> stringL, String string){
        int count = 0;
        for (String i: stringL){
            if (i.equals(string)) {count = count + 1;}
        }
        return count;}

    /**
     * gets the default value from a giving training dataset
     * @param targetAttribute the attribute to get default for
     * @return constructs an arraylist of values of the target attribute
     * with the greatest number (if multiple values have the same greatest
     * number, they're all kept in the arraylist), then randomly selects on
     * from the arraylist
     */
    public String getDefault(String targetAttribute){
        LinkedList<String> listOfValues = new LinkedList<>();
        ArrayList<String> listOfMaxValues = new ArrayList<>();
        int count = 0;
        for (Row i : this.dataset){
            listOfValues.addFirst(i.getAttributeValue(targetAttribute));
        }
        for (String s : listOfValues) {
            if (this.occurrenceCount(listOfValues,s) > count) {
                listOfMaxValues.clear();
                listOfMaxValues.add(s);
                count = this.occurrenceCount(listOfValues,s);
            } else if (this.occurrenceCount(listOfValues, s) == count) {
                listOfMaxValues.add(s);
                count = this.occurrenceCount(listOfValues,s);
            }
        }
        Random number = new Random();
        int upperBound = listOfMaxValues.size();
        int randomIndex = number.nextInt(upperBound);
        return listOfMaxValues.get(randomIndex);
    }

    /**
     * A helper method that prints out the dataset for testing
     * @return the string version of the dataset, which starts with "Dataset:"
     * and includes a list of rows
     */
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("Dataset:");
        for(Row row: this.dataset) {
            String cur = row.toString();
            output.append(cur).append("\r\n");
        }
        return output.toString();
    }
}
