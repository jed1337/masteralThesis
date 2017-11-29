package utils;

import formatFiles.FormatAsText;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import weka.core.Instances;

public class UtilsARFF extends Utils{
   private UtilsARFF(){}
   
   public static void createArff(String filename, List<String> paths, HashMap<String, String>... hashMaps) throws IOException, Exception{
      FormatAsText fat = new FormatAsText(filename);
      fat.clearFile();
      for (String path : paths) {
         fat.addInstances(path);
      }
      
      fat.replaceAllStrings(hashMaps);
      
      Instances tempHeader = UtilsInstances.getHeader(filename);
      String att = tempHeader.attribute(tempHeader.numAttributes()-1).name();
      
      fat.addClassCount(att);
   }
}