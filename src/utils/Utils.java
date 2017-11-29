package utils;

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
import java.util.Map;
import java.util.function.Predicate;

public class Utils {
   private static final String NEW_LINE = "\n";

   protected Utils() {}
   
   public static <T> boolean arrayContains(final T[] array, final T v) {
      if (v == null) {
         for (final T e : array) {
            if (e == null) {
               return true;
            }
         }
      } else {
         for (final T e : array) {
            if (e == v || v.equals(e) || ((String) e).equalsIgnoreCase(((String)v)) ) {
               return true;
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
            sb.append(NEW_LINE);
            
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
         new File(folderPath).mkdirs();
      }
      else{
         System.err.println("'"+folderPath+"' already exists.");
      }
   }
   
   public static <K extends Object, V extends Object> void addToMap(Map map, K key, V value) throws IllegalArgumentException{
      if(!map.containsKey(key)){
         map.put(key, value);
      } else{
         throw new IllegalArgumentException(key+" already exists");
      }
   }
}
