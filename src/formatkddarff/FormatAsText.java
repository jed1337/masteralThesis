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

   /**
    * Adds instances to the file in @param addToPath
    * @param addToPath The relative path from the NB folder to check
    * @param delimiter The String to look for (e.g. @data). Skip that then add lines.
    * If the delimiter is empty (""), this function will add all lines to path
    * @throws IOException
    */
   public void addInstances(String addToPath, String delimiter) throws IOException {
      StringBuilder sb = new StringBuilder();

      try (BufferedReader br = Utils.getBufferedReader(addToPath)) {
         String line;
         
//       passedDelimiter is true if the delimiter's empty
         boolean passedDelimiter = false || delimiter.isEmpty();

         while ((line = br.readLine()) != null) {
            if (!passedDelimiter && line.startsWith(delimiter)) {
               passedDelimiter = true;
               continue; //Skip this afterwards so that it's not added
            }
            if (passedDelimiter) {
               sb.append(line);
               sb.append(NEW_LINE);
            }
         }
      }
      Utils.writeFile(path, sb.toString(), true);
   }
   
   /**
    * Removes everything from the file in path
    * @throws IOException
    */
   public void clearFile() throws IOException{
      Utils.writeFile(path, "", false);  
   }

   public void replaceAllStrings(HashMap<String, String>... hashMaps) throws IOException {
      String allLines = getFileContents(path);
      for (HashMap<String, String> hashMap : hashMaps) {
         for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            allLines = allLines.replaceAll(entry.getKey(), entry.getValue());
         }
      }
      Utils.writeFile(path, allLines, false);
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
