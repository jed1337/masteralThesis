package format;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FormatAsTextTest {

    public FormatAsTextTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

   /**
    * Test of getArffHeader method, of class FormatAsText.
    */
   @Test
   public void testGetArffHeader() throws Exception {
      System.out.println("getArffHeader");
      FormatAsText instance = null;
      String expResult = "";
      String result = instance.getArffHeader();
      assertEquals(expResult, result);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of hasHeader method, of class FormatAsText.
    */
   @Test
   public void testHasHeader() throws Exception {
      System.out.println("hasHeader");
      FormatAsText instance = null;
      boolean expResult = false;
      boolean result = instance.hasHeader();
      assertEquals(expResult, result);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of addInstances method, of class FormatAsText.
    */
   @Test
   public void testAddInstances() throws Exception {
      System.out.println("addInstances");
      String addToPath = "";
      FormatAsText instance = null;
      instance.addInstances(addToPath);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of clearFile method, of class FormatAsText.
    */
   @Test
   public void testClearFile() throws Exception {
      System.out.println("clearFile");
      FormatAsText instance = null;
      instance.clearFile();
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of insertString method, of class FormatAsText.
    */
   @Test
   public void testInsertString() throws Exception {
      System.out.println("insertString");
      String toAdd = "";
      int position = 0;
      FormatAsText instance = null;
      instance.insertString(toAdd, position);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of addClassCount method, of class FormatAsText.
    */
   @Test
   public void testAddClassCount() throws Exception {
      System.out.println("addClassCount");
      String attributeName = "";
      FormatAsText instance = null;
      instance.addClassCount(attributeName);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

   /**
    * Test of replaceAllStrings method, of class FormatAsText.
    */
   @Test
   public void testReplaceAllStrings() throws Exception {
      System.out.println("replaceAllStrings");
      HashMap[] hashMaps = null;
      FormatAsText instance = null;
      instance.replaceAllStrings(hashMaps);
      // TODO review the generated test code and remove the default call to fail.
      fail("The test case is a prototype.");
   }

}