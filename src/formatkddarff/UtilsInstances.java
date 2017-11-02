package formatkddarff;

import java.io.FileNotFoundException;
import java.io.IOException;
import weka.core.Instances;

public class UtilsInstances extends Utils{
   
   public static Instances getInstances(String path) throws FileNotFoundException, IOException {
      return new Instances(Utils.getBufferedReader(path));
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