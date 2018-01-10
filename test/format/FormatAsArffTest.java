package format;

import constants.AttributeTypeConstants;
import preprocessFiles.preprocessAs.FormatAsArff;
import format.testConstants.FileNameConstants;
import format.testConstants.PathConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.UtilsInstances;
import weka.core.Instance;
import weka.core.Instances;

public class FormatAsArffTest {
   private FormatAsArff faa;
   private int isAttackIndex;

   private final String tcpFlood = "tcpflood";

   @BeforeClass
   public static void setUpClass() {}

   @AfterClass
   public static void tearDownClass() {}

   @Before
   public void setUp() throws IOException {
      faa = new FormatAsArff(PathConstants.UNFORMATTED_DIR+FileNameConstants.TEST);
      isAttackIndex = UtilsInstances.getAttributeIndex(
         faa.getInstances(), 
         AttributeTypeConstants.ATTRIBUTE_CLASS
      );
   }

   @After
   public void tearDown() {
      faa = null;
      isAttackIndex = -1;
   }

   /**
    * Test of getSavePath method, of class FormatAsArff.
    * @throws java.io.IOException
    */
   @Test
   public void testGetSavePath() throws IOException {
      System.out.println("getSavePath");
      String expResult = PathConstants.UNFORMATTED_DIR+FileNameConstants.TEST;
      String result = faa.getSavePath();
      assertEquals(expResult, result);
   }

   @Test(expected=IOException.class)
   public void testGetSavePathError() throws IOException {
      System.out.println("getSavePath error");
      new FormatAsArff("badpath");
   }

   /**
    * Test of setSavePath method, of class FormatAsArff.
    */
   @Test
   public void testSetSavePath() {
      System.out.println("setSavePath");
      String newPath = "newPath";
      faa.setSavePath(newPath);
      assertEquals(newPath, faa.getSavePath());
   }

   /**
    * Test of getInstances method, of class FormatAsArff.
    */
   @Test
   public void testGetInstances() {
      System.out.println("getInstances");
      Instances expResult = null;
      Instances result = faa.getInstances();
      assertNotEquals(expResult, result);
   }

   /**
    * Test of randomise method, of class FormatAsArff.
    * @throws java.lang.Exception
    */
   @Test
   public void testRandomise() throws Exception {
      System.out.println("randomise");
      String contents = faa.getInstances().toString();
      faa.randomise(11);
      String randomisedContents = faa.getInstances().toString();

      assertNotEquals(contents, randomisedContents);
   }

   /**
    * Test of removeAllInstances method, of class FormatAsArff.
    */
   @Test
   public void testRemoveAllInstances() {
      System.out.println("removeAllInstances");
      faa.removeAllInstances();
      assertEquals(faa.getInstances().numInstances(), 0);
   }

   /**
    * Test of removeAttributes method, of class FormatAsArff.
    * @throws java.lang.Exception
    */
   @Test
   public void testRemoveAttributes_StringArr() throws Exception {
      System.out.println("removeAttributes");
      String land = "land";
      String service = "SERVICE";
      List<String> al = new ArrayList<>();
      al.add(land);
      al.add(service);

      assertNotEquals(UtilsInstances.getAttributeIndex(faa.getInstances(), land), -1);
      assertNotEquals(UtilsInstances.getAttributeIndex(faa.getInstances(), service), -1);

      faa.removeAttributes(al);

      assertEquals(UtilsInstances.getAttributeIndex(faa.getInstances(), land), -1);
      assertEquals(UtilsInstances.getAttributeIndex(faa.getInstances(), service), -1);
   }


   /**
    * Test of removeNonMatchingClasses method, of class FormatAsArff.
    */
   @Test
   public void testRemoveNonMatchingClasses_String_StringArr() {
      System.out.println("removeNonMatchingClasses");
      faa.removeNonMatchingClasses(AttributeTypeConstants.ATTRIBUTE_CLASS, tcpFlood);

      for (Instance instance : faa.getInstances()) {
         String actual = instance.stringValue(isAttackIndex).toLowerCase();

         assertEquals(actual, tcpFlood);
      }
   }

   /**
    * Test of keepXInstances method, of class FormatAsArff.
    */
   @Test
   public void testKeepXInstances_3args_1() {
      System.out.println("keepXInstances");
      int retainCount = 3;
      faa.keepXInstances(AttributeTypeConstants.ATTRIBUTE_CLASS, tcpFlood, retainCount);

      int actual = 0;
      for (Instance instance : faa.getInstances()) {
         if(instance.stringValue(isAttackIndex).equalsIgnoreCase(tcpFlood)){
            actual++;
         }
      }
      assertEquals(retainCount, actual);
   }
}