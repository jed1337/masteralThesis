package formatkddarff.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {
   public static BufferedReader getBufferedReader(String path) throws FileNotFoundException {
      return new BufferedReader(new FileReader(path));
   }

   public static void writeFile(String filename, String allLines, boolean append) throws IOException {
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) {
         bw.write(allLines);
      }
   }
   
   public static void makeFolders(String folderPath){
      if(!folderPath.isEmpty()){
         new File(folderPath).mkdirs();
      }
      else{
         System.err.println("'"+folderPath+"' already exists.");
      }
   }
}
