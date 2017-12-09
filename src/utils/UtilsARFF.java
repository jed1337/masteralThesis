package utils;

import constants.CharConstants;
import preprocessFiles.preprocessAs.FormatAsText;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class UtilsARFF{
   private UtilsARFF(){}
   
   public static FormatAsText createArff(String filename, List<String> paths) 
           throws IOException, IllegalArgumentException{
      if(paths== null){
         throw new IllegalArgumentException("The paths is null!");
      } else if (paths.isEmpty()){
         throw new IllegalArgumentException("The paths is empty!");
      }
      
      FormatAsText fat = new FormatAsText(filename);
      fat.clearFile();
      for (String path : paths) {
         fat.addInstances(path);
      }
      return fat;
   }
   
   public static FormatAsText createArff(String filename, List<String> paths, String attributeName) 
           throws IOException, IllegalArgumentException{
      FormatAsText fat = createArff(filename, paths);
      
      int index = UtilsInstances.getAttributeIndex(
              UtilsInstances.getInstances(paths.get(0)), 
              attributeName
      );
      
      String[] lines = Utils.getFileContents(fat.getPATH()).split(CharConstants.NEW_LINE);
      boolean passedData = false;
      Set<String> indexValues = new HashSet<>();   //A Set is used so that all its values are unique
      
      for (String line : lines) {
         if(line.contains("@data")){
            passedData = true;
            continue;
         }
         if(passedData){
            indexValues.add(line.split(CharConstants.COMMA)[index]);
         }
      }
      
      fat.replaceAllStrings(
         Utils.replaceAttribute(attributeName, indexValues.toArray(new String[0]))
      );
      
      fat.addClassCount(attributeName);
      
      return fat;
   }
}