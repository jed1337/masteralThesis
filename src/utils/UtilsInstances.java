package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import preprocessFiles.preprocessAs.FormatAsArff;
import preprocessFiles.utils.MutableInt;
import weka.core.Instance;
import weka.core.Instances;

public final class UtilsInstances{
   private UtilsInstances() {}
   
   /**
    * Gets the Instances specified in the path and sets the class index 
    * as the last attribute
    * @param path Where to get the instances from
    * @return
    * @throws FileNotFoundException
    * @throws IOException 
    */
   public static Instances getInstances(String path) throws FileNotFoundException, IOException {
      Instances instances = new Instances(Utils.getBufferedReader(path));
      instances.setClassIndex(instances.numAttributes()-1);
      return instances;
   }
   
   /**
    * Gets the header from the arff file specified in path and
    * removes all the features to remove
    * @param path The path to get the arff file from
    * @param featuresToRemove 
    * @return
    * @throws IOException
    * @throws Exception 
    */
   public static Instances getHeader(String path, List<String> featuresToRemove) throws IOException, Exception{
      FormatAsArff faa = new FormatAsArff(path);
      faa.removeAllInstances();
      faa.removeAttributes(featuresToRemove);
      return faa.getInstances();
   }
   
   public static String getClassAttributeName(String instancesPath) throws FileNotFoundException, IOException{
      return getClassAttributeName(
         UtilsInstances.getInstances(instancesPath)
      );
   }
   
   /**
    * Return the class attribute name. Assumes that the class attribute has been set.
    * @param instances
    * @return 
    */
   public static String getClassAttributeName(Instances instances){
      return instances.classAttribute().name();
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
         throw new IOException(
            "Attribute "+attributeName+" not found. getClassCount can't continue"
         );
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