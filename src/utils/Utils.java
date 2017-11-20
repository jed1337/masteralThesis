package utils;

import format.FormatAsText;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Utils {
   private static final String NEW_LINE = "\n";

   protected Utils() {}
   
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
      
   public static void duplicateFile(String source, String destination) throws IOException{
      String sourceContents = getFileContents(source);
      writeFile(destination, sourceContents);
   }
   
   public static void writeFile(String destination, String allLines) throws IOException {
      writeFile(destination, allLines, false);
   }
   
   public static void writeFile(String destination, String allLines, boolean append) throws IOException {
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

   public static void createArff(String filename, List<String> paths) throws IOException{
      createArff(filename, paths, "isAttack");
   }
      
   public static void createArff(String filename, List<String> paths, String attributeName) throws IOException{
      FormatAsText fat = new FormatAsText(filename);
      fat.clearFile();
      
      for (String path : paths) {
         fat.addInstances(path);
      }
      
      fat.addClassCount(attributeName);
   }
   
   public static <K extends Object, V extends Object> void addToMap(Map map, K key, V value) throws IllegalArgumentException{
      if(!map.containsKey(key)){
         map.put(key, value);
      } else{
         throw new IllegalArgumentException(key+" already exists");
      }
   }
}
