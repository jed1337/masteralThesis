package format;

import utils.Utils;
import utils.UtilsInstances;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import weka.core.Instance;
import weka.core.Instances;

public class FormatAsText extends Format{
   private final String NEW_LINE = "\n";
   private final String path;

   public FormatAsText(String path) {
      this.path = path;
   }
   
   public String getArffHeader() throws FileNotFoundException, IOException{
      return Utils.getFileContents(
         this.path, 
         s->s.startsWith("@data")
      );
   }
//   
//   public String getArffHeader() throws FileNotFoundException, IOException{
//      StringBuilder sb = new StringBuilder();
//
//      try (BufferedReader br = Utils.getBufferedReader(this.path)) {
//         String line;
//
//         while ((line = br.readLine()) != null) {
//            //apply boolean check function here
//            
//            sb.append(line);
//            sb.append(NEW_LINE);
//            
//            if (line.startsWith("@data")) {
//               break;
//            }
//         }
//      }
//      return sb.toString();
//   }
//
//   public void addInstances(String addToPath) throws IOException {
//      StringBuilder sb = new StringBuilder();
//
//      try (BufferedReader br = Utils.getBufferedReader(addToPath)) {
//         String line;
//         boolean passedDelimiter = false;
//         
//         while ((line = br.readLine()) != null) {
//            if (!passedDelimiter && line.startsWith("@data")) {
//               passedDelimiter = true;
//               continue; //Skip this afterwards so that it's not added
//            }
//            if (passedDelimiter) {
//               sb.append(line);
//               sb.append(NEW_LINE);
//            }
//         }
//      }
//      Utils.writeFile(path, sb.toString(), true);
//   }
   
   public void addInstances(String addToPath) throws IOException {
      String fileContents = Utils.getFileContents(addToPath);
      int afterDataIndex=fileContents.indexOf("@data")+6; //The +6 is because 
      
      Utils.writeFile(
         path, 
         fileContents.substring(afterDataIndex), 
         true);
   }
   
   /**
    * Removes everything from the file in path
    * @throws IOException
    */
   public void clearFile() throws IOException{
      Utils.writeFile(path, "", false);  
   }
   
   public void insertString(String toAdd, int position) throws IOException{
      String pathContents = Utils.getFileContents(this.path);
      pathContents = pathContents.substring(0, position) 
         + toAdd 
         + pathContents.substring(position, pathContents.length());
      
      Utils.writeFile(path, pathContents, false);  
   }
   
   public void addClassCount(String attributeName) throws IOException{
      Instances instances = UtilsInstances.getInstances(this.path);
      int attributeIndex = UtilsInstances.getAttributeIndex(instances, attributeName);
      Map<String, MutableInt> freq = new HashMap<>();
      MutableInt count;
      
      for (int i = instances.numInstances() - 1; i >= 0; i--) {
         Instance inst = instances.get(i);
         String attributeValue = inst.stringValue(attributeIndex);
         
         count = freq.get(attributeValue);
         if (count == null) {
            freq.put(attributeValue, new MutableInt());
         } else {
            count.increment();
         }
      }
      
      StringBuilder sb = new StringBuilder();
      freq.forEach((k,v)->{
         sb.append("% ");
         sb.append(attributeName);
         sb.append(" ");
         sb.append(k);
         sb.append(":\t");
         sb.append(v.get());
         sb.append(NEW_LINE);
      });
      
      insertString(sb.toString(), 0);
   }

   public void replaceAllStrings(HashMap<String, String>... hashMaps) throws IOException {
      String allLines = Utils.getFileContents(path);
      for (HashMap<String, String> hashMap : hashMaps) {
         for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            allLines = allLines.replaceAll(entry.getKey(), entry.getValue());
         }
      }
      Utils.writeFile(path, allLines, false);
   }
}
