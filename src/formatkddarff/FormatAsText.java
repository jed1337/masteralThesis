package formatkddarff;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FormatAsText extends Format{
   private final String NEW_LINE = "\n";
   private final String path;

   public FormatAsText(String path) {
      this.path = path;
   }

   public void addInstances(String addToPath, String delimiter) throws IOException {
      StringBuilder sb = new StringBuilder();

      try (BufferedReader br = Utils.getBufferedReader(addToPath)) {
         String line;
         boolean passedData = false;

         while ((line = br.readLine()) != null) {
            if (line.startsWith(delimiter)) {
               passedData = true;
               continue; //Skip this afterwards so that it's not added
            }
            if (passedData) {
               sb.append(line);
               sb.append(NEW_LINE);
            }
         }
      }
      Utils.writeStringFile(path, sb.toString(), true);
   }

   public void replaceAllStrings(HashMap<String, String>... hashMaps) throws IOException {
      String allLines = getFileContents(path);
      for (HashMap<String, String> hashMap : hashMaps) {
         for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            allLines = allLines.replaceAll(entry.getKey(), entry.getValue());
         }
      }
      Utils.writeStringFile(path, allLines, false);
   }
    
   public String getFileContents(String filename) throws IOException{
      StringBuilder sb = new StringBuilder();
      try (BufferedReader br = Utils.getBufferedReader(filename)) {
         String line;
         while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(NEW_LINE);
         }
         return sb.toString();
      }
   }
}
