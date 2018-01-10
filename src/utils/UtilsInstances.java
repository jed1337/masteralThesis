package utils;

import preprocessFiles.preprocessAs.FormatAsArff;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import preprocessFiles.utils.MutableInt;
import weka.core.Instance;
import weka.core.Instances;

public final class UtilsInstances{
   private UtilsInstances() {}
   
   public static Instances getInstances(String path) throws FileNotFoundException, IOException {
      Instances instances = new Instances(Utils.getBufferedReader(path));
      instances.setClassIndex(instances.numAttributes()-1);
      return instances;
   }
   
   public static Instances getHeader(String path, List<String> featuresToRemove) throws IOException, Exception{
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
   
   public static HashMap<String, MutableInt> getClassCount(String attributeName, Instances instances) throws IOException{
      final int attributeIndex = UtilsInstances.getAttributeIndex(instances, attributeName);
      final HashMap<String, MutableInt> classCount = new HashMap<>();
      MutableInt count;

      if(attributeIndex == -1){
         throw new IOException
            ("Attribute "+attributeName+" not found. The class count can't continue");
      }

      for (int i = instances.numInstances() - 1; i >= 0; i--) {
         Instance inst = instances.get(i);
         String attributeValue = inst.stringValue(attributeIndex);

         count = classCount.get(attributeValue);
         if (count == null) {
            Utils.addToMap(classCount, attributeValue, new MutableInt());
         } else {
            count.increment();
         }
      }
      
      return classCount;
   }
}