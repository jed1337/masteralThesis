package utils;

import formatFiles.FormatAsArff;
import java.io.FileNotFoundException;
import java.io.IOException;
import weka.core.Instances;

public class UtilsInstances extends Utils{
   private UtilsInstances() {}
   
   public static Instances getInstances(String path) throws FileNotFoundException, IOException {
      Instances instances = new Instances(Utils.getBufferedReader(path));
      instances.setClassIndex(instances.numAttributes()-1);
      return instances;
   }
   
   public static Instances getHeader(String path, String... featuresToRemove) throws IOException, Exception{
      FormatAsArff faa = new FormatAsArff(path);
      faa.removeAllInstances();
      faa.removeAttributes(featuresToRemove);
      return faa.getInstances();
   }

   /**
    * Gets the index of the attribute name
    * Case insensitive
    * Starts at 0
    * @param instances
    * @param attributeName
    * @return
    */
   public static int getAttributeIndex(Instances instances, String attributeName) {
      for (int i = 0; i < instances.numAttributes(); i++) {
         if (instances.attribute(i).name().equalsIgnoreCase(attributeName)) {
            return i;
         }
      }
      System.err.println("Attribute '"+attributeName+"' Not found");
      return -1;
   }
}