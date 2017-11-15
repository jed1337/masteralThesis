package format;

import utils.Utils;
import utils.UtilsInstances;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import weka.core.Instance;
import weka.core.Instances;

public class FormatAsText {
   private final String NEW_LINE = "\n";
   private final String PATH;

   public FormatAsText(String PATH) {
      this.PATH = PATH;
   }
   
   public String getArffHeader() throws FileNotFoundException, IOException{
      return Utils.getFileContents(
         this.PATH, 
         s->s.startsWith("@data")
      );
   }
   
   /**
    * @return true if the text in the path has an ARFF header (@relation)
    * @throws IOException
    */
   public boolean hasHeader() throws IOException{
      String pathContents = Utils.getFileContents(this.PATH);
      return pathContents.contains("@relation");
   }

   /**
    * Adds Arff instances to this.PATH
    * Will only append the header from addToPath if there's no header in this.PATH
    * @param addToPath
    * @throws IOException
    */
   public void addInstances(String addToPath) throws IOException {
      String fileContents = Utils.getFileContents(addToPath);
      int afterDataIndex=fileContents.indexOf("@data")+6; //The +6 is because of "@data\n"
      
      Utils.writeFile(
         PATH, 
         hasHeader()? fileContents.substring(afterDataIndex) : fileContents, 
         true);
   }
   
   /**
    * Removes everything from the file in path
    * @throws IOException
    */
   public void clearFile() throws IOException{
      Utils.writeFile(PATH, "", false);  
   }
   
   public void insertString(String toAdd, int position) throws IOException{
      String pathContents = Utils.getFileContents(this.PATH);
      pathContents = pathContents.substring(0, position) 
         + toAdd 
         + pathContents.substring(position, pathContents.length());
      
      Utils.writeFile(this.PATH, pathContents, false);  
   }
   
   public void addClassCount(String attributeName) throws IOException{
      Instances instances = UtilsInstances.getInstances(this.PATH);
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
      System.out.println(sb.toString());
      insertString(sb.toString(), 0);
   }

   public void replaceAllStrings(HashMap<String, String>... hashMaps) throws IOException {
      String allLines = Utils.getFileContents(PATH);
      for (HashMap<String, String> hashMap : hashMaps) {
         for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            allLines = allLines.replaceAll(entry.getKey(), entry.getValue());
         }
      }
      Utils.writeFile(PATH, allLines, false);
   }
}
