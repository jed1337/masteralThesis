package utils;

import formatFiles.FormatAsText;
import java.io.IOException;
import java.util.List;

public class UtilsARFF extends Utils{
   private UtilsARFF(){}
   
   public static void createArff(String filename, List<String> paths) throws IOException{
      createArff(filename, paths, "isAttack");
   }
      
   public static void createArff(String filename, List<String> paths, String attributeName) throws IOException{
      FormatAsText fat = new FormatAsText(filename);
      fat.clearFile();
      
      for (String path : paths) {
         fat.addInstances(path);
      }
      
      fat.addClassCount(attributeName);
   }
}