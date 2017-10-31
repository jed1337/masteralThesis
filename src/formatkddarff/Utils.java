package formatkddarff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import weka.core.Instances;

public class Utils {
   public static BufferedReader getBufferedReader(String path) throws FileNotFoundException {
      return new BufferedReader(new FileReader(path));
   }

   public static Instances getInstances(String path) throws FileNotFoundException, IOException {
      return new Instances(Utils.getBufferedReader(path));
   }

   public static void writeStringFile(String filename, String allLines, boolean append) throws IOException {
      try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) {
         bw.write(allLines);
      }
   }
}
