package formatkddarff;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.instance.Randomize;

public class FormatAsArff extends Format{
   private Instances instances;
   
   private String folderPath="";
   private String fileName="";

   public FormatAsArff(Instances instances) {
      this.instances = instances;
   }

   public FormatAsArff(String path) throws FileNotFoundException, IOException {
      this(path.substring(0, path.lastIndexOf("/")),
           path.substring(path.lastIndexOf("/")+1)
      );
   }
   
   public FormatAsArff(String initFolderPath, String initPath) throws FileNotFoundException, IOException {
      this.fileName = initPath;
      this.instances = Utils.getInstances(initFolderPath+initPath);
   }

   public Instances getInstances() {
      return instances;
   }

   public <T> boolean arrayContains(final T[] array, final T v) {
      if (v == null) {
         for (final T e : array) {
            if (e == null) {
               return true;
            }
         }
      } else {
         for (final T e : array) {
            if (e == v || v.equals(e)) {
               return true;
            }
         }
      }

      return false;
   }
   
   public void randomise(int seed) throws Exception{
      Randomize rand = new Randomize();
      rand.setRandomSeed(seed);
      useFilter(instances, rand);
   }

   public void removeAttributes(String attributeIndeces) throws Exception {
      removeAttributes(attributeIndeces, false);
   }

   public void removeAttributes(String attributeIndeces, boolean isInverted)
           throws FileNotFoundException, IOException, Exception {
      Remove remove;

      remove = new Remove();
      remove.setAttributeIndices(attributeIndeces);
      remove.setInvertSelection(isInverted);
      
      useFilter(instances, remove);
   }

   public void removeNonMatchingClasses(String attributeName, String... classesToRetain) {
      removeNonMatchingClasses(
              UtilsInstances.getAttributeIndex(instances, attributeName), 
              new ArrayList<>(Arrays.asList(classesToRetain)));
   }

   public void removeNonMatchingClasses(String attributeName, ArrayList<String> classesToRetain) {
      removeNonMatchingClasses(UtilsInstances.getAttributeIndex(instances, attributeName), classesToRetain);
   }
   
   public void removeNonMatchingClasses(int attributeIndex, ArrayList<String> classesToRetain) {
      // Iterate from last to first because when we remove an instance, the rest shifts by one position.
      for (int i = instances.numInstances() - 1; i >= 0; i--) {
         Instance inst = instances.get(i);
         if(!classesToRetain.contains(inst.stringValue(attributeIndex))){
            instances.delete(i);
         }
      }
   }
   
   //keepXInstances(isAttack, "neptune", 2500)
   // keep only 2500 instances of neptune. Look for "neptune" in isAttack
   public void keepXInstances(String attribute, String attributeValue, int toKeep){
      keepXInstances(UtilsInstances.getAttributeIndex(instances, attribute), 
                     attributeValue, 
                     toKeep);
   }
   public void keepXInstances(int attributeIndex, String attributeValue, int toKeep){
      int cur = 0;
      
      for (int i = instances.numInstances() - 1; i >= 0; i--) {
         Instance inst = instances.get(i);

         if(inst.stringValue(attributeIndex).equals(attributeValue)){ 
            cur++;
            if(cur > toKeep){
               instances.delete(i);
            }
         }
      }
   }
   
   public void toNominal(String rangeList) throws Exception {
      StringToNominal stn = new StringToNominal();
      stn.setAttributeRange(rangeList);
      
      useFilter(instances, stn);
   }
   
   private void useFilter(Instances instances, Filter filter) throws Exception{
      filter.setInputFormat(instances);
      this.instances = Filter.useFilter(instances, filter);
   }
   
   public void writeArffInstances() throws IOException{
      writeArffInstances(this.folderPath+this.fileName);
   }

   public void writeArffInstances(String saveFileName) throws IOException {
      writeArffInstances(saveFileName.substring(0, saveFileName.lastIndexOf("/")),
           saveFileName.substring(saveFileName.lastIndexOf("/")+1)
      );
   }
   
   public void writeArffInstances(String folderPath, String fileName) throws IOException {
      super.setSaveFileName(folderPath+fileName);
      
      BufferedWriter writer = new BufferedWriter(new FileWriter(folderPath+fileName));
      writer.write(this.instances.toString());
      writer.flush();
      writer.close();
   }
}
