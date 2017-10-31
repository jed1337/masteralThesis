package formatkddarff;

import weka.core.Instances;

public class UtilsInstances extends Utils{
   public static int getAttributeIndex(Instances instances, String attributeName) {
      for (int i = 0; i < instances.numAttributes(); i++) {
         if (instances.attribute(i).name().equals(attributeName)) {
            return i;
         }
      }
      return -1;
   }
}