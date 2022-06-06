package sol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import org.junit.Assert;
import org.junit.Test;
import src.DecisionTreeCSVParser;
import src.Row;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DecisionTreeTest {

    List<Row> dataObjects = DecisionTreeCSVParser.parse("sol/small-test-suite.csv");
    ArrayList<String> dataObjectsAttributes = new ArrayList<String>(dataObjects.get(0).getAttributes());
    Dataset testing1 = new Dataset(dataObjectsAttributes, dataObjects);
    List<Row> dataObjects2 = DecisionTreeCSVParser.parse("sol/small-test-suite2.csv");
    ArrayList<String> dataObjectsAttributes2 = new ArrayList<String>(dataObjects2.get(0).getAttributes());
    Dataset testing2 = new Dataset(dataObjectsAttributes2, dataObjects2);
    List<Row> dataObjects3 = DecisionTreeCSVParser.parse("sol/small-test-suite3.csv");
    ArrayList<String> dataObjectsAttributes3 = new ArrayList<String>(dataObjects3.get(0).getAttributes());
    Dataset testing3 = new Dataset(dataObjectsAttributes3, dataObjects3);


    @Test
    public void testDataset() {
        //        getAttributeList
        List<String> AttList = new LinkedList<String>();
        AttList.add("size");
        AttList.add("gender");
        AttList.add("school");
        AttList.add("age");
        Assert.assertEquals(AttList, testing1.getAttributeList());
        //        size
        Assert.assertEquals(4, testing1.size());
        //        checkOutCome
        Assert.assertEquals(false, testing1.checkOutcome("school"));
        Assert.assertEquals(true, testing2.checkOutcome("school"));
        //        divideDataset
        Dataset I1 = testing1.divideDataset("school").get(0);
        Dataset I2 = testing1.divideDataset("school").get(1);
        Assert.assertEquals(2, I1.size());
        Assert.assertEquals(2, I2.size());
        //        getLeafDecision
        Assert.assertEquals("Brown", testing2.getLeafDecision("school"));
        Row mohan = new Row("row1");
        mohan.setAttributeValue("size", "medium");
        mohan.setAttributeValue("gender", "true");
        mohan.setAttributeValue("age", "10");
        //        getDefault
        Assert.assertEquals("Yale", testing3.getDefault("school"));
        Assert.assertEquals("Brown", testing2.getDefault("school"));

    }

    //tests for getDecision
    @Test
    public void testGetDecision() {
        Row mohan = new Row("row1");
        mohan.setAttributeValue("size", "medium");
        mohan.setAttributeValue("gender", "true");
        mohan.setAttributeValue("age", "10");
        TreeGenerator A = new TreeGenerator();
        A.generateTree(this.testing1,"school");
        Assert.assertEquals("Yale",A.getDecision(mohan));
        TreeGenerator B = new TreeGenerator();
        B.generateTree(this.testing2,"school");
        Assert.assertEquals("Brown",B.getDecision(mohan));
    }

}
