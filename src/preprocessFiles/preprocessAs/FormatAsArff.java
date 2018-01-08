package preprocessFiles.preprocessAs;

import utils.UtilsInstances;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Utils;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RenameNominalValues;
import weka.filters.unsupervised.instance.Randomize;

public final class FormatAsArff {
   private Instances instances;
   private String savePath;
   
   public FormatAsArff(String path) throws FileNotFoundException, IOException {
      this.savePath = path;
      this.instances = UtilsInstances.getInstances(path);
   }

   public String getSavePath() {
      return savePath;
   }

   public void setSavePath(String savePath) {
      this.savePath = savePath;
   }
   
   public Instances getInstances() {
      return instances;
   }

   public void randomise(int seed) throws Exception{
      Randomize rand = new Randomize();
      rand.setRandomSeed(seed);
      useFilter(instances, rand);
   }
   
   public void removeAllInstances(){
      for (int i = instances.numInstances() - 1; i >= 0; i--) {
         instances.delete(i);
      }
   }
   
   /**
    * Removes all the attributes whose name is in attributeNames from instances
    * Calls removeAttributes(List<String>)
    * @param attributeNames
    * @throws Exception
    */
   public void removeAttributes (String... attributeNames) throws Exception{
      removeAttributes(Arrays.asList(attributeNames));
//      for (String attributeName : attributeNames) {
//         int index = UtilsInstances.getAttributeIndex(this.instances, attributeName);
//         if(index!=-1){
//            attributeIndeces.add((index+1)+"");
//         }
//      }
//      removeAttributes(String.join(",", attributeIndeces));
      
//      this.instances.setClassIndex(this.instances.numAttributes()-1);
   }
   
   /**
    * Removes all the attributes whose name is in attributeNames from instances
    * Calls removeAttributes(String)
    * @param attributeNames
    * @throws Exception
    */
   public void removeAttributes (List<String> attributeNames) throws Exception{
      ArrayList<String> attributeIndeces = new ArrayList<>();
      attributeNames.stream()
         .map((attributeName)->UtilsInstances.getAttributeIndex(this.instances, attributeName))
         .filter((index)->(index!=-1))
         .forEachOrdered((index)->{
            attributeIndeces.add((index+1)+"");
      });
      removeAttributes(String.join(",", attributeIndeces));
      
   }

   /**
    * Removes attributes (Starting at 1) from the instances
    * Also sets the class index as the last index
    * @param attributeIndeces
    * @throws FileNotFoundException
    * @throws IOException
    * @throws Exception
    */
   public void removeAttributes(String attributeIndeces)
           throws FileNotFoundException, IOException, Exception {
      Remove remove;

      remove = new Remove();
      remove.setAttributeIndices(attributeIndeces);
      
      useFilter(instances, remove);
      this.instances.setClassIndex(this.instances.numAttributes()-1);
   }
   
   /**
    * 
    * For example <br>
    * <i>{@literal @}attribute myAttribute {first, second, third}<br><br></i>
    * {@code 
    *    renameNominalValues("myAttribute", "first:uno, second:dos, third:tres")
    * }
    * <br><br>
    * becomes:<br>
    * <i>{@literal @}attribute myAttribute {uno, dos, tres}</i>
    * 
    * @param attributes Attributes to replace
    * @param replacement in the format <b>CurrentValue:NewValue</b>
    * @throws Exception 
    */
   public void renameNominalValues(String attributes, String replacement) throws Exception{
      RenameNominalValues rnv = new RenameNominalValues();
      rnv.setSelectedAttributes(attributes);
      rnv.setValueReplacements(replacement);
      useFilter(instances, rnv);
   }

   public void removeNonMatchingClasses(String attributeName, String... classesToRetain) {
      removeNonMatchingClasses(
         UtilsInstances.getAttributeIndex(instances, attributeName), 
         classesToRetain);
   }

   public void removeNonMatchingClasses(int attributeIndex, String... classesToRetain) {
      // Iterate from last to first because when we remove an instance, the rest shifts by one position.
      for (int i = instances.numInstances() - 1; i >= 0; i--) {
         Instance inst = instances.get(i);
         if(!Utils.arrayContains(classesToRetain, inst.stringValue(attributeIndex))){
            instances.delete(i);
         }
      }
   }

   /**
    * keepXInstances("isAttack", "neptune", 2500)
    * Keep only 2500 instances of "neptune" from "isAttack"
    * @param attribute
    * @param attributeValue
    * @param toKeep
    */
   public void keepXInstances(String attribute, String attributeValue, int toKeep){
      keepXInstances(
         UtilsInstances.getAttributeIndex(instances, attribute), 
         attributeValue, 
         toKeep
      );
   }
   
   public void keepXInstances(int attributeIndex, String attributeValue, int toKeep){
      int cur = 0;
      
      for (int i = instances.numInstances() - 1; i >= 0; i--) {
         Instance inst = instances.get(i);

         if(inst.stringValue(attributeIndex).equalsIgnoreCase(attributeValue)){ 
            cur++;
            if(cur > toKeep){
               instances.delete(i);
            }
         }
      }
   }
   
   private void useFilter(Instances instances, Filter filter) throws Exception{
      filter.setInputFormat(instances);
      this.instances = Filter.useFilter(instances, filter);
   }
}