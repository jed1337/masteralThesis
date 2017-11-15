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
import java.util.function.Predicate;

public class Utils {
   private static final String NEW_LINE = "\n";
   
   public static BufferedReader getBufferedReader(String path) throws FileNotFoundException {
      return new BufferedReader(new FileReader(path));
   }

   public static void writeFile(String filename, String allLines) throws IOException {
      writeFile(filename, allLines, false);
   }
   
   public static void writeFile(String filename, String allLines, boolean append) throws IOException {
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) {
         bw.write(allLines);
      }
      System.out.println("Created '"+filename+"'");
   }
   
   public static void makeFolders(String folderPath){
      if(!folderPath.isEmpty()){
         new File(folderPath).mkdirs();
      }
      else{
         System.err.println("'"+folderPath+"' already exists.");
      }
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
   
   public static void createArff(String filename, List<String> paths) throws IOException{
//      String header = new FormatAsText(paths.get(0)).getArffHeader();
      
      FormatAsText fat = new FormatAsText(filename);
      fat.clearFile();
//      fat.insertString(header, 0);
      
      for (String path : paths) {
         fat.addInstances(path);
      }
      
      fat.addClassCount("isAttack");
   }
}
