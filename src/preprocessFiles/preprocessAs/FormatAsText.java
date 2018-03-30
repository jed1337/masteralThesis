package preprocessFiles.preprocessAs;

import constants.CharConstants;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import preprocessFiles.utils.MutableInt;
import utils.Utils;
import utils.UtilsInstances;
import weka.core.Instances;

public final class FormatAsText {
   private final String PATH;

   public FormatAsText(String PATH) {
      this.PATH = PATH;
   }

   public String getPATH() {
      return PATH;
   }

   /**
    * @return {@code true} if {@code this.PATH} has an ARFF header 
    * (contains the String {@code @relation})<br>
    * {@code false} otherwise
    * @throws IOException
    */
   public boolean hasHeader() throws IOException{
      return Utils.getFileContents(this.PATH).contains("@relation");
   }

   /**
    * Adds Arff instances from {@code addToPath} 
    * to {@code this.PATH}
    * <p>
    * Will only append the header from {@code addToPath} if {@code @data} 
    * doesn't exist in {@code this.PATH}<br>
    * But the instances get copied regardless
    * <p>
    * Warning: This function doesn't check if the headers in {@code this.PATH}
    * is the same in {@code addToPath}
    * @param addToPath 
    * @throws IOException
    * @throws IllegalArgumentException if {@code @data} was not found in addToPath
    */
   public void addInstances(String addToPath) throws IOException, IllegalArgumentException{
      final String addContent = Utils.getFileContents(addToPath);
      final String dataMarker = "@data";

      // The +6 is because of "@data\n"
      // We lowercased addContent since .indexOf() is case sensitive
      final int afterDataIndex=addContent.toLowerCase().indexOf(dataMarker)+6;

      if(afterDataIndex == dataMarker.length()){
         throw new IllegalArgumentException(dataMarker+" not found in addToPath");
      }

      Utils.writeStringFile(
         PATH,
         hasHeader()? addContent.substring(afterDataIndex) : addContent,
         true
      );
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
      HashMap<String, MutableInt> freq = UtilsInstances.getClassCount(attributeName, instances);

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

   /**
    * entry.getKey() = String to search, entry.getValue() = String to replace it with<p>
    * Despite each HashMap being able to store multiple key-value pairs, the parameter here is
    * a HashMap var args so that HashMaps form different sources can be passed.
    * @param hashMaps
    * @throws IOException 
    */
   public void replaceAllStrings(HashMap<String, String>... hashMaps) throws IOException {
      String allLines = Utils.getFileContents(PATH);
      for (HashMap<String, String> hashMap : hashMaps) {
         for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            allLines = allLines.replaceAll(entry.getKey(), entry.getValue());
         }
      }
      Utils.writeStringFile(PATH, allLines);
   }
}
