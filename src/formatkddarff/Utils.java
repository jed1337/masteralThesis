package formatkddarff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {
   public static BufferedReader getBufferedReader(String path) throws FileNotFoundException {
      return new BufferedReader(new FileReader(path));
   }
   
   public static String getArrayListString(ArrayList<String> strings) {
      StringBuilder sb = new StringBuilder();
      strings.forEach((string)->{
         sb.append(string);
         sb.append(",");
      });
      sb.deleteCharAt(sb.length()-1); //Delete the extra "," at the end
      return sb.toString();
   }

   public static void writeFile(String filename, String allLines, boolean append) throws IOException {
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) {
         bw.write(allLines);
      }
   }
}
