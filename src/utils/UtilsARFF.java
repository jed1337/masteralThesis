package utils;

import constants.AttributeTypeConstants;
import constants.CharConstants;
import preprocessFiles.preprocessAs.FormatAsText;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import preprocessFiles.preprocessAs.FormatAsArff;
import weka.core.Instances;

public final class UtilsARFF{
   private UtilsARFF(){}

   /**
    * Creates an Arff file containing the combined instances from paths.<br>
    * Automatically changes the header of @param attributeName to remove values that doesn't exist. <p>
    * Ex. <br>
    * ... <br>
    * {@ @}attribute fruitName{apple, banana, kiwi, durian, grape}<br>
    * {@ @}data<br>
    * ... apple<br>
    * ... apple<br>
    * ... kiwi<br>
    * ... durian<br>
    * ... kiwi<br>
    * <p>
    * {@code combineArffAndAddClassCount(filename, paths, "fruitName") }<p>
    * Output:<br>
    * ...<br>
    * {@ @}attribute fruitName{apple, kiwi, durian}<br>
    * {@ @}data<br>
    * ...
    *
    * @param outputPath
    * @param inputPaths
    * @param attributeName
    *
    * @return a FormatAsText instance set to the created Arff file
    *
    * @throws IOException
    * @throws IllegalArgumentException
    */
   public static FormatAsText combineArffAndAddClassCount
         (String outputPath, List<String> inputPaths, String attributeName)
         throws IOException, IllegalArgumentException {
      FormatAsText fat = combineArff(outputPath, inputPaths);

      int attributeIndex = UtilsInstances.getAttributeIndex(
         UtilsInstances.getInstances(inputPaths.get(0)),
         attributeName
      );

      String[] lines = Utils.getFileContents(fat.getPATH()).split(CharConstants.NEW_LINE);
      boolean passedData = false;
      Set<String> indexValues = new HashSet<>();   //A Set is used so that all its values are unique

      for (String line : lines) {
         if(line.contains("@data")){
            passedData = true;
            continue; //Go to next loop iteration to skip the line containing "@data"
         }
         if(passedData){
            indexValues.add(line.split(CharConstants.COMMA)[attributeIndex]);
         }
      }

      fat.replaceAllStrings(
         Utils.replaceAttribute(attributeName, indexValues.toArray(new String[0]))
      );

      fat.addClassCount(attributeName);

      return fat;
   }


   /**
    * Creates an Arff file containing the combined instances from paths<br>
    * @param outputPath
    * @param inputPaths
    * @return
    * @throws IOException
    * @throws IllegalArgumentException
    */
   public static FormatAsText combineArff(String outputPath, List<String> inputPaths)
           throws IOException, IllegalArgumentException{
      if(inputPaths== null){
         throw new IllegalArgumentException("The inputPaths is null!");
      } else if (inputPaths.isEmpty()){
         throw new IllegalArgumentException("The inputPaths is empty!");
      }
      checkIfSameHeader(inputPaths);

      FormatAsText fat = new FormatAsText(outputPath);
      fat.clearFile();
      for (String path : inputPaths) {
         fat.addInstances(path);
      }
      return fat;
   }

   /**
    * Checks if the inputPaths have the same header as one another.<p>
    * Ignores mismatches in the class label
    * @param inputPaths
    * @throws IllegalArgumentException if the inputPaths don't have the same header as one another
    */
   private static void checkIfSameHeader(List<String> inputPaths) throws IllegalArgumentException, IOException{
      //Compare the instances in the first inputPath against all the others
      final Instances first = new FormatAsArff(inputPaths.get(0)).getInstances();
      final String diffClassAttributeCount =
         "Attributes differ at position "+
         (UtilsInstances.getAttributeIndex(first,AttributeTypeConstants.ATTRIBUTE_CLASS)+1)
      ;
      //Start at 1 since it doesn't make sense to compare it to
      //the first element (itself)
      for (int i = 1; i < inputPaths.size(); i++) {
         Instances other = new FormatAsArff(inputPaths.get(i)).getInstances();
         String equalHeaderMessage = first.equalHeadersMsg(other);
         if(equalHeaderMessage != null){
            
            //This is done since it's ok to have different attributes on the
            //class label since we're standardising the labels later on
            if(!equalHeaderMessage.contains(diffClassAttributeCount)){
               throw new IllegalArgumentException("Different headers\n"+equalHeaderMessage);
            }
         }
      }
   }
}