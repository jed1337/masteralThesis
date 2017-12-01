package utils;

import classifier.ClassifierHolder;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import weka.classifiers.Classifier;
import weka.core.Instances;

public class UtilsClssifiersTest {

    public UtilsClssifiersTest() {
    }

//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//   /**
//    * Test of readModel method, of class UtilsClssifiers.
//    */
//   @Test
//   public void testReadModel() throws Exception {
//      System.out.println("readModel");
//      String filename = "";
//      Classifier expResult = null;
//      Classifier result = UtilsClssifiers.readModel(filename);
//      assertEquals(expResult, result);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of writeModel method, of class UtilsClssifiers.
//    */
//   @Test
//   public void testWriteModel_3args() throws Exception {
//      System.out.println("writeModel");
//      Classifier classifier = null;
//      Instances instances = null;
//      String filename = "";
//      UtilsClssifiers.writeModel(classifier, instances, filename);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of writeModel method, of class UtilsClssifiers.
//    */
//   @Test
//   public void testWriteModel_ClassifierHolder() throws Exception {
//      System.out.println("writeModel");
//      ClassifierHolder ch = null;
//      UtilsClssifiers.writeModel(ch);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of saveCrossValidationToFile method, of class UtilsClssifiers.
//    */
//   @Test
//   public void testSaveCrossValidationToFile_ArrayList_int() throws Exception {
//      System.out.println("saveCrossValidationToFile");
//      ArrayList<ClassifierHolder> chAL = null;
//      int folds = 0;
//      UtilsClssifiers.saveCrossValidationToFile(chAL, folds);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of saveCrossValidationToFile method, of class UtilsClssifiers.
//    */
//   @Test
//   public void testSaveCrossValidationToFile_ClassifierHolder_int() throws Exception {
//      System.out.println("saveCrossValidationToFile");
//      ClassifierHolder ch = null;
//      int folds = 0;
//      UtilsClssifiers.saveCrossValidationToFile(ch, folds);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of saveTestEvaluationToFile method, of class UtilsClssifiers.
//    */
//   @Test
//   public void testSaveTestEvaluationToFile_ArrayList_Instances() throws Exception {
//      System.out.println("saveTestEvaluationToFile");
//      ArrayList<ClassifierHolder> chAL = null;
//      Instances testSet = null;
//      UtilsClssifiers.saveTestEvaluationToFile(chAL, testSet);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of saveTestEvaluationToFile method, of class UtilsClssifiers.
//    */
//   @Test
//   public void testSaveTestEvaluationToFile_ClassifierHolder_Instances() throws Exception {
//      System.out.println("saveTestEvaluationToFile");
//      ClassifierHolder ch = null;
//      Instances testSet = null;
//      UtilsClssifiers.saveTestEvaluationToFile(ch, testSet);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }

}