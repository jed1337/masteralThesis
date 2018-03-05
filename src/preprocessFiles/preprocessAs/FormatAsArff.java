package preprocessFiles.preprocessAs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import utils.Utils;
import utils.UtilsInstances;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.RenameNominalValues;
import weka.filters.unsupervised.attribute.StringToNominal;
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
    * Also sets the class index as the last index
    * @param attributeNames
    * @throws Exception
    */
   public void removeAttributes (List<String> attributeNames) throws Exception{
      ArrayList<String> attributeIndeces = new ArrayList<>();
      for (String attributeName : attributeNames) {
         int index = UtilsInstances.getAttributeIndex(this.instances, attributeName);
         if(index!=-1){
            attributeIndeces.add((index+1)+"");
         }
      }
      removeAttributes(String.join(",", attributeIndeces));
      
      this.instances.setClassIndex(this.instances.numAttributes()-1);
   }

   /**
    * Removes attributes (Starting at 1) from the instances
    * @param attributeIndeces
    * @throws FileNotFoundException
    * @throws IOException
    * @throws Exception
    */
   public void removeAttributes(String attributeIndeces)
           throws FileNotFoundException, IOException, Exception {
      Remove remove = new Remove();
      remove.setAttributeIndices(attributeIndeces);
      
      useFilter(instances, remove);
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
      useFilter(this.instances, rnv);
   }
   
   public void stringToNominal(String attributeName) throws Exception{
      String sIndex = UtilsInstances.getAttributeIndex(this.instances, attributeName)+"";
      
      StringToNominal stn = new StringToNominal();
      stn.setAttributeRange(sIndex);
      useFilter(this.instances, stn);
   }

   /**
    * For each instance in this.instances, 
    * if the value of its attributeName is not within
    * classesToRetain, that instance is removed.
    * @param attributeName
    * @param classesToRetain
    */
   public void removeNonMatchingClasses(String attributeName, String... classesToRetain) {
      removeNonMatchingClasses(
         UtilsInstances.getAttributeIndex(instances, attributeName), 
         classesToRetain
      );
   }

   /**
    * Refer to {@link #removeNonMatchingClasses(String, String...)
    * removeNonMatchingClasses(String, String...)} 
    * @param attributeIndex
    * @param classesToRetain 
    */
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
    * {@code keepXInstances("isAttack", "neptune", 2500)}<br>
    * Keep only 2500 instances of the "neptune" class
    * from the "isAttack" attribute.
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
      int cur = 0; //Current number of instnaces of that class
      
      for (int i = instances.numInstances() - 1; i >= 0; i--) {
         Instance inst = instances.get(i);

         if(inst.stringValue(attributeIndex).equalsIgnoreCase(attributeValue)){ 
            cur++;
            if(cur > toKeep){
               instances.delete(i);
            }
         }
      }
      
      if(cur<toKeep){
         System.err.println(String.format(
            "KeepXInstances was unable to keep %d since only %d of class %s exist",
            toKeep,
            cur,
            attributeValue
         ));
      }
   }
   
   private void useFilter(Instances instances, Filter filter) throws Exception{
      filter.setInputFormat(instances);
      this.instances = Filter.useFilter(instances, filter);
   }
}