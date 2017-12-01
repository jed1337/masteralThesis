package formatFiles;

import constants.CharConstants;
import utils.Utils;
import utils.UtilsInstances;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import weka.core.Instance;
import weka.core.Instances;

public class FormatAsText {
   private final String PATH;

   public FormatAsText(String PATH) {
      this.PATH = PATH;
   }

   public String getPATH() {
      return PATH;
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
    * @throws IllegalArgumentException if @data was not found in addToPath
    */
   public void addInstances(String addToPath) throws IOException, IllegalArgumentException{
      String addContent = Utils.getFileContents(addToPath);

      // The +6 is because of "@data\n"
      // We lowercased addContent since .indexOf() is case sensitive
      int afterDataIndex=addContent.toLowerCase().indexOf("@data")+6;

      if(afterDataIndex == 5){
         throw new IllegalArgumentException("@data not found in addToPath");
      }

      Utils.writeStringFile(
         PATH,
         hasHeader()? addContent.substring(afterDataIndex) : addContent,
         true);
   }

   /**
    * Removes everything from the file in path
    * @throws IOException
    */
   public void clearFile() throws IOException{
      Utils.writeStringFile(PATH, "");
   }

   public void insertString(String toAdd, int position) throws IOException{
      String pathContents = Utils.getFileContents(this.PATH);
      pathContents = pathContents.substring(0, position)
         + toAdd
         + pathContents.substring(position, pathContents.length());

      Utils.writeStringFile(this.PATH, pathContents);
   }

   public void addClassCount(String attributeName) throws IOException{
      Instances instances = UtilsInstances.getInstances(this.PATH);
      int attributeIndex = UtilsInstances.getAttributeIndex(instances, attributeName);
      Map<String, MutableInt> freq = new HashMap<>();
      MutableInt count;

      if(attributeIndex == -1){
         throw new IOException
            ("Attribute "+attributeName+" not found. The class count can't continue");
      }

      for (int i = instances.numInstances() - 1; i >= 0; i--) {
         Instance inst = instances.get(i);
         String attributeValue = inst.stringValue(attributeIndex);

         count = freq.get(attributeValue);
         if (count == null) {
            Utils.addToMap(freq, attributeValue, new MutableInt());
            // freq.put(attributeValue, new MutableInt());
         } else {
            count.increment();
         }
      }

      StringBuilder sbHeader = new StringBuilder();
      freq.forEach((name, amount)->{
         sbHeader.append("% ");
         sbHeader.append(attributeName);
         sbHeader.append(" ");
         sbHeader.append(name);
         sbHeader.append(":\t");
         sbHeader.append(amount.get());
         sbHeader.append(CharConstants.NEW_LINE);
      });
      System.out.println(sbHeader.toString());
      insertString(sbHeader.toString(), 0);
   }

   public void replaceAllStrings(HashMap<String, String>... hashMaps) throws IOException {
      String allLines = Utils.getFileContents(PATH);
      for (HashMap<String, String> hashMap : hashMaps) {
         for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            allLines = allLines.replaceAll(entry.getKey(), entry.getValue());
         }
      }
      Utils.writeStringFile(PATH, allLines);
   }

   private class MutableInt {
      int value;

      public MutableInt() {
         this.value = 1;
      }

      public void increment() {
         ++value;
      }

      public int get() {
         return value;
      }
   }
}
