package format;

import formatFiles.FormatAsText;
import formatFiles.FormatAsArff;
import format.testConstants.FileNameConstants;
import format.testConstants.PathConstants;
import java.io.IOException;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.Utils;
import utils.UtilsTest;
import static org.junit.Assert.*;

public class FormatAsTextTest {
   private FormatAsText fat;
   public FormatAsTextTest() {
   }

   @BeforeClass
   public static void setUpClass() {
   }

   @AfterClass
   public static void tearDownClass() {
   }

   @Before
   public void setUp() throws IOException {
      Utils.duplicateFile(PathConstants.UNFORMATTED_DIR + FileNameConstants.TEST,
         PathConstants.UNFORMATTED_DIR + FileNameConstants.OUTPUT
      );
      
      fat = new FormatAsText(PathConstants.UNFORMATTED_DIR + FileNameConstants.OUTPUT);
   }
   
   private String getContents() throws IOException{
      return Utils.getFileContents(fat.getPATH());
   }

   @After
   public void tearDown() throws IOException {
      fat.clearFile();
      fat = null;
   }

   /**
    * Test of hasHeader method, of class FormatAsText.
    * @throws java.lang.Exception
    */
   @Test
   public void testHasHeader() throws Exception {
      System.out.println("hasHeader");
      boolean expResult = true;
      boolean result = fat.hasHeader();
      assertEquals(expResult, result);
   }

   /**
    * Test of addInstances method, of class FormatAsText.
    * @throws java.lang.Exception
    */
   @Test
   public void testAddInstances() throws Exception {
      System.out.println("addInstances");
      
      fat.addInstances(PathConstants.UNFORMATTED_DIR + FileNameConstants.ADD);
      
      FormatAsArff faa = new FormatAsArff(PathConstants.UNFORMATTED_DIR + FileNameConstants.OUTPUT);
      assertEquals(24, faa.getInstances().numInstances());
   }

   /**
    * Test of clearFile method, of class FormatAsText.
    * @throws java.lang.Exception
    */
   @Test
   public void testClearFile() throws Exception {
      System.out.println("clearFile");
      fat.clearFile();
      assertEquals(Utils.getFileContents(fat.getPATH()), "");
   }

   /**
    * Test of insertString method, of class FormatAsText.
    * @throws java.lang.Exception
    */
   @Test
   public void testInsertString() throws Exception {
      System.out.println("insertString");
      String toAdd = "blah blah";
      String before = getContents();
      
      fat.insertString(toAdd, 0);
      
      assertEquals(toAdd+before, getContents());
   }

   /**
    * Test of addClassCount method, of class FormatAsText.
    * @throws java.lang.Exception
    */
   @Test
   public void testAddClassCount() throws Exception {
      System.out.println("addClassCount");
      String attributeName = "isAttack";
      fat.addClassCount(attributeName);
      assertEquals(
         UtilsTest.getChecksumFromFile(fat.getPATH()),
         UtilsTest.getChecksumFromFile(
            PathConstants.FORMATTED_DIR+FileNameConstants.TEST_WITH_CLASS_COUNT)
      );
   }

   /**
    * Test of replaceAllStrings method, of class FormatAsText.
    * @throws java.lang.Exception
    */
   @Test
   public void testReplaceAllStrings() throws Exception {
      System.out.println("replaceAllStrings");
      HashMap<String, String> hm = new HashMap<>();
      hm.put("tcpFlood", "flood_of_tcp_packets");
      hm.put("httpFlood", "flood_of_http_packets");
      fat.replaceAllStrings(hm);
      
      assertEquals(-1, getContents().indexOf("tcpFlood"));
      assertEquals(-1, getContents().indexOf("httpFlood"));
   }
}
