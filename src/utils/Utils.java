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

public class Utils {
   protected Utils() {}

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
    * Copies all the files in sourceDir to destinationDir
    * Loop through folders code from https://stackoverflow.com/questions/19190584/
    * @param sourceDir
    * @param destinationDir
    * @throws IOException
    */
   public static void duplicateFolder(String sourceDir, String destinationDir) throws IOException{
      final File directory = new File(sourceDir);
      for (final File file : directory.listFiles()) {
         duplicateFile(file.getAbsolutePath(), destinationDir+file.getName());
      }
   }

   /**
    * Copies files form the source to the destination.
    * Copy code from https://www.journaldev.com/861/java-copy-file
    * @param source
    * @param destination
    * @throws IOException
    */
   public static void duplicateFile(String source, String destination) throws IOException{
      try (
         InputStream is = new FileInputStream(source);
         OutputStream os = new FileOutputStream(destination);
      )      {
         byte[] buffer = new byte[1024];
         int length;
         while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
         }
      }
   }

   public static void writeStringFile(String destination, String allLines) throws IOException {
      writeStringFile(destination, allLines, false);
   }

   public static void writeStringFile(String destination, String allLines, boolean append) throws IOException {
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(destination, append))) {
         bw.write(allLines);
      }
      System.out.println("Created '"+destination+"'");
   }

   public static void makeFolders(String folderPath){
      if(!folderPath.isEmpty()){
         if(new File(folderPath).mkdirs()){
            System.out.println("Created the folder: '"+folderPath+"'");
         }else{
            System.err.println("Some or all folders of '"+folderPath+"' was not created");
         }
      }
      else{
         System.err.println("The folder '"+folderPath+"' already exists.");
      }
   }

   public static <K extends Object, V extends Object> void addToMap(Map map, K key, V value) throws IllegalArgumentException{
      if(!map.containsKey(key)){
         map.put(key, value);
      } else{
         throw new IllegalArgumentException(key+" already exists");
      }
   }
   
   public static String doubleToString(double value, int width, int afterDecimalPoint) {
      return weka.core.Utils.doubleToString(value, width, afterDecimalPoint);
   }
}
