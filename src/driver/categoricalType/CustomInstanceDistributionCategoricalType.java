package driver.categoricalType;

import constants.CategoricalTypeConstants;
import constants.PreprocessFileName;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import preprocessFiles.PreprocessFile;

public final class CustomInstanceDistributionCategoricalType implements CategoricalType{
   private final String relabelSpecificAttack;
   private final CategoricalTypeConstants categoricalTypeConstant;
   
   private final EnumMap<PreprocessFileName, Double> fileDistributions;

   public CustomInstanceDistributionCategoricalType(
      String relabelSpecificAttack, 
      CategoricalTypeConstants categoricalTypeConstant, 
      EnumMap<PreprocessFileName, Double> fileDistributions) 
      throws IllegalArgumentException {
         
      this.relabelSpecificAttack = relabelSpecificAttack;
      this.categoricalTypeConstant = categoricalTypeConstant;
      this.fileDistributions = fileDistributions;

      for (Map.Entry<PreprocessFileName, Double> entry : this.fileDistributions.entrySet()) {
         PreprocessFileName key = entry.getKey();
         double value           = entry.getValue();
         
         if(value<=0){
            throw new IllegalArgumentException(String.format(
               "A non-positive value of %f was assigned to %s",
               value,
               key
            ));
         }
      }
   }
   
   /**
    * This disregards the pfL variable since we simply return the relabel string.
    * @param pfL
    * @return 
    */
   @Override
   public String getRelabelSpecificAttack(List<PreprocessFile> pfL) {
      return this.relabelSpecificAttack;
   }

   @Override
   public CategoricalTypeConstants getCategoricalTypeConstant() {
      return this.categoricalTypeConstant;
   }

   /**
    * Sets the class instance depending on the distribution given in the constructor
    * as well as by int totalInstancesCount. <p>
    * 
    * This assumes that pfAL contains unique instances (this ) <br>
    * @param pfL
    * @param totalInstancesCount 
    */
   @Override
   public void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstancesCount) 
      throws NoSuchElementException, IllegalArgumentException{
      checkPFExistence(pfL);
      checkPFUnique(pfL);
      
      double totalDistribution = this.fileDistributions.values().stream()
         .reduce(0.0, Double::sum);
      
      int instancesPerDistributionValue = (int) (totalInstancesCount/totalDistribution);
      
      pfL.forEach((PreprocessFile pf)->{
         final double distributionValue = CustomInstanceDistributionCategoricalType.this.fileDistributions.get(pf.getPreprocessFileName());
         int instanceCount = (int) (instancesPerDistributionValue * distributionValue);
         
         pf.setInstancesCount(instanceCount);
      });
   }

   /**
    * Makes sure that all the preprocess files are declared in this.fileDistribution.
    * @param pfL 
    */
   private void checkPFExistence(List<PreprocessFile> pfL) throws NoSuchElementException{
      pfL.stream()
         .map((pf)->pf.getPreprocessFileName())
         .filter((pfName)->(!this.fileDistributions.containsKey(pfName)))
         .forEachOrdered((pfName)->{
            throw new NoSuchElementException(pfName + " wasn't set in this.fileDistributions");
         });
   }
   
   /**
    * Checks that all the preprocess files are unique. <br>
    * This will still work with duplicate instances 
    * but since we use the pfName in it to differentiate different preprocess files, 
    * they will end up having the wrong number of instances
    * @param pfL 
    */
   private void checkPFUnique(List<PreprocessFile> pfL){
      //A set only contains unique instances
      HashSet<PreprocessFile> set = new HashSet<>(pfL);

      // If there are no duplicates, the set will have
      // the same size as pfL
      // todo check if this works since we've used an object, not a Stirng or some other immutable thing
      if (set.size() < pfL.size()) {
         throw new IllegalArgumentException("The preprocess files given contain duplicates");
      }
   }
}