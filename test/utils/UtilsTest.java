package utils;

import format.testConstants.FileNameConstants;
import format.testConstants.PathConstants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Predicate;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTest {
//   /**
//    * Test of replaceAttribute method, of class Utils.
//    */
//   @Test
//   public void testReplaceAttribute_String_StringArr() {
//      System.out.println("replaceAttribute");
//      String attribute = "";
//      String[] toReplace = null;
//      HashMap<String, String> expResult = null;
//      HashMap<String, String> result = Utils.replaceAttribute(attribute, toReplace);
//      assertEquals(expResult, result);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of replaceAttribute method, of class Utils.
//    */
//   @Test
//   public void testReplaceAttribute_String_String() {
//      System.out.println("replaceAttribute");
//      String attribute = "";
//      String toReplace = "";
//      HashMap<String, String> expResult = null;
//      HashMap<String, String> result = Utils.replaceAttribute(attribute, toReplace);
//      assertEquals(expResult, result);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }

   /**
    * Test of arrayContains method, of class Utils.
    */
   @Test
   public void testArrayContains() {
      System.out.println("arrayContains");
      Object[] array;

      array = new Integer[]{1, 2, 3, 4, 5};
      assertEquals(true, Utils.arrayContains(array, 3));
      assertEquals(true, Utils.arrayContains(array, 1));
      assertEquals(false, Utils.arrayContains(array, 3.14));
      assertEquals(false, Utils.arrayContains(array, 6));
      assertEquals(false, Utils.arrayContains(array, "5"));

      array = new String[]{"1", "2", "3", "4", "5"};
      assertEquals(true, Utils.arrayContains(array, "3"));
      assertEquals(true, Utils.arrayContains(array, "1"));
      assertEquals(false, Utils.arrayContains(array, 3.14));
      assertEquals(false, Utils.arrayContains(array, "6"));
      assertEquals(false, Utils.arrayContains(array, 5));
   }

   /**
    * Test of getBufferedReader method, of class Utils returning something
    * @throws java.io.FileNotFoundException
    */
   @Test
   public void testGetBufferedReader() throws FileNotFoundException {
      System.out.println("getBufferedReader");
      BufferedReader result = Utils.getBufferedReader(PathConstants.UNFORMATTED_DIR+FileNameConstants.OUTPUT);
      assertNotNull(result);
   }
   
   /**
    * Test of getBufferedReader method, of class Utils throwing an Exception
    * @throws java.io.FileNotFoundException
    */
   @Test(expected = FileNotFoundException.class) 
   public void testGetBufferedReaderException() throws FileNotFoundException {
      System.out.println("getBufferedReader");
      Utils.getBufferedReader(FileNameConstants.INVALID);
   }

   /**
    * Test of getFileContents method, of class Utils.
    * @throws java.io.IOException
    */
   @Test(expected = IOException.class)
   public void testGetFileContents_Exception() throws IOException {
      System.out.println("getFileContents");
      Utils.getFileContents(FileNameConstants.INVALID);
   }
   
   /**
    * Test of getFileContents method, of class Utils.
    * @throws java.io.IOException
    */
   @Test
   public void testGetFileContents_String() throws IOException {
      System.out.println("getFileContents");
      String expResult ="SmallFile\nI'm SmallFile\nSmallFile\n";
      String result = Utils.getFileContents(PathConstants.UNFORMATTED_DIR+FileNameConstants.SMALL_FILE);
      assertEquals(expResult, result);
   }

   /**
    * Test of getFileContents method, of class Utils.
    * @throws java.io.IOException
    */
   @Test
   public void testGetFileContents_String_Predicate() throws IOException {
      System.out.println("getFileContents");
      
      String filename = PathConstants.UNFORMATTED_DIR+FileNameConstants.SMALL_FILE;
      Predicate<String> breakCondition = (s)->s.equals("I'm SmallFile");
      String expResult = "SmallFile\nI'm SmallFile\n";
      String result = Utils.getFileContents(filename, breakCondition);
      
      assertEquals(expResult, result);
   }
//
//   /**
//    * Test of duplicateFolder method, of class Utils.
//    */
//   @Test
//   public void testDuplicateFolder() throws Exception {
//      System.out.println("duplicateFolder");
//      String sourceDir = "";
//      String destinationDir = "";
//      Utils.duplicateFolder(sourceDir, destinationDir);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of duplicateFile method, of class Utils.
//    */
//   @Test
//   public void testDuplicateFile() throws Exception {
//      System.out.println("duplicateFile");
//      String source = "";
//      String destination = "";
//      Utils.duplicateFile(source, destination);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of writeStringFile method, of class Utils.
//    */
//   @Test
//   public void testWriteStringFile_String_String() throws Exception {
//      System.out.println("writeStringFile");
//      String destination = "";
//      String allLines = "";
//      Utils.writeStringFile(destination, allLines);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of writeStringFile method, of class Utils.
//    */
//   @Test
//   public void testWriteStringFile_3args() throws Exception {
//      System.out.println("writeStringFile");
//      String destination = "";
//      String allLines = "";
//      boolean append = false;
//      Utils.writeStringFile(destination, allLines, append);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//
//   /**
//    * Test of makeFolders method, of class Utils.
//    */
//   @Test
//   public void testMakeFolders() {
//      System.out.println("makeFolders");
//      String folderPath = "";
//      Utils.makeFolders(folderPath);
//      // TODO review the generated test code and remove the default call to fail.
//      fail("The test case is a prototype.");
//   }
//

   /**
    * Test of addToMap method, of class Utils.
    */
   @Test(expected = IllegalArgumentException.class)
   public void testAddToMap_Exception() throws IllegalArgumentException {
      System.out.println("addToMap");
      HashMap<String, Integer> hm = new HashMap<>();
      Utils.addToMap(hm, "First", 1);
      Utils.addToMap(hm, "First", 1);
   }
   
   /**
    * Test of addToMap method, of class Utils.
    */
   @Test
   public void testAddToMap() throws IllegalArgumentException{
      System.out.println("addToMap");
      HashMap<String, Integer> hm = new HashMap<>();
      Utils.addToMap(hm, "First", 1);
      Utils.addToMap(hm, "Second", 2);
      
      assertEquals( ((Integer) 1), hm.get("First"));
      assertEquals( ((Integer) 2), hm.get("Second"));
   }
}