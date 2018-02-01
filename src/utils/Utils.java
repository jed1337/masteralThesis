package utils;

import constants.CharConstants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import org.apache.commons.io.FileUtils;
import preprocessFiles.PreprocessFile;

public final class Utils {
   private Utils() {}

   public static HashMap<String, String> replaceAttribute(String attribute, String... toReplace){
      return replaceAttribute(attribute, String.join(",",toReplace));
   }

   public static HashMap<String, String> replaceAttribute(String attribute, String toReplace){
      final HashMap<String, String> OTHER_REPLACEMENTS = new HashMap<>();
      Utils.addToMap(
        OTHER_REPLACEMENTS,
        "(?m)^@attribute "+attribute+".*",
        "@attribute "+attribute+" {"+toReplace+"}"
      );

      return OTHER_REPLACEMENTS;
   }

   public static <T> boolean arrayContains(final T[] array, final T v) {
      if (v == null) {
         for (final T e : array) {
            if (e == null) {
               return true;
            }
         }
      } else {
         for (final T e : array) {
            if (e == v || e.equals(v)){
               return true;
            }
            if(e instanceof String && v instanceof String){
               if( ((String) e).equalsIgnoreCase( ((String)v)) ){
                  return true;
               }
            }
         }
      }
      return false;
   }

   public static BufferedReader getBufferedReader(String path) throws FileNotFoundException {
      return new BufferedReader(new FileReader(path));
   }

   public static String getFileContents(String filename) throws IOException{
      return getFileContents(filename, s->false);
   }

   /**
    * Returns the String contents within the file in filename
    * @param filename
    * @param breakCondition Stop adding to the current contents 
    * if this condition is met
    * @return The string contents
    * @throws IOException 
    */
   public static String getFileContents(
      String filename, Predicate<String> breakCondition) throws IOException{

      StringBuilder sb = new StringBuilder();
      try (BufferedReader br = Utils.getBufferedReader(filename)) {
         String line;
         while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(CharConstants.NEW_LINE);

            if(breakCondition.test(line)){
               break;
            }
         }
         return sb.toString();
      }
   }

   /**
    * Uses FileUtils.copyDirectory
    * @param sourceDirPath
    * @param destinationDirPath
    * @throws IOException
    */
   public static void duplicateDirectory(String sourceDirPath, String destinationDirPath) throws IOException{
      FileUtils.copyDirectory(
         new File(sourceDirPath), 
         new File(destinationDirPath)
      );
   }

   /**
    * Copies files form the source to the destination.<br>
    * Copy code from https://www.journaldev.com/861/java-copy-file
    * @param source
    * @param destination
    * @throws IOException
    */
   public static void duplicateFile(String source, String destination) throws IOException{
      try (
         InputStream is = new FileInputStream(source);
         OutputStream os = new FileOutputStream(destination);
      ) {
         byte[] buffer = new byte[1024];
         int length;
         while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
         }
      }
   }
   
   /**
    * Writes the instances of the PreprocessFile into its save path 
    * (pf.getFaa().getSavePath())
    * @param pf
    * @throws IOException 
    */
   public static void writePreprocessFile(PreprocessFile pf) throws IOException{
      Utils.writeStringFile(
         pf.getFaa().getSavePath(),
         pf.getFaa().getInstances().toString()
      );
   }

   public static void writeStringFile(String destination, String allLines) throws IOException {
      writeStringFile(destination, allLines, false);
   }

   public static void writeStringFile(String destination, String allLines, boolean append) throws IOException {
      //FileWriter just creates the file if it doesn't exist.
      //It won't create the possible parent directories of that file.
      //This folder creates the parent directories if needed
      makeParentFolder(destination); 
      
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(destination, append))) {
         bw.write(allLines);
      }
      if(append){
         System.out.println("Appended to '"+destination+"'");
      }else{
         System.out.println("Overwritten '"+destination+"'");
      }
      System.out.println("Created '"+destination+"'");
   }
   
   /**
    * Assume that only the Data/ folder exists,
    * {@code
    * makeParentFolder(Data/subdir1/subdir2/file.txt)
    * }
    * Creates subdir1 and subdir2. File.txt, however, isn't created
    * <br>
    * @see <a href="https://stackoverflow.com/questions/2833853/create-whole-path-automatically-when-writing-to-a-new-file">
    * Source: stackoverflow.com</a>
    * @param filePath The path towards the file
    * @return true if all folders were created, false otherwise 
    * (if none, or only some were created)
    */
   public static boolean makeParentFolder(String filePath){
      return makeFolders(new File(filePath).getParentFile());
   }

   public static boolean makeFolders(File file){
      boolean wasCreated = file.mkdirs();
      if (wasCreated) {
         System.out.println("Created the folder: '" + file.getAbsolutePath() + "'");
      } else {
         System.err.println("Some or all folders of '" + file.getAbsolutePath() + "' was not created");
      }
      return wasCreated;
   }

   /**
    * Makes sure that no duplicate key in the Map is added. 
    * <br>
    * Normally, when a duplicate key is added, it replaces the old one.
    * But we don't want that
    * @param <K> A generic type 
    * @param <V> A generic type
    * @param map Where to search duplicates from
    * @param key
    * @param value
    * @throws IllegalArgumentException if a duplicate item is added
    */
   public static <K extends Object, V extends Object> void addToMap(Map map, K key, V value) throws IllegalArgumentException{
      if(!map.containsKey(key)){
         map.put(key, value);
      } else{
         throw new IllegalArgumentException(key+" already exists");
      }
   }
   
   /**
    * Uses the corresponding function in Weka's Utils
    * @see weka.core.Utils.doubleToString#doubleToString(double, int, int)
    * @param value
    * @param width
    * @param afterDecimalPoint
    * @return 
    */
   public static String doubleToString(double value, int width, int afterDecimalPoint) {
      return weka.core.Utils.doubleToString(value, width, afterDecimalPoint);
   }
}
