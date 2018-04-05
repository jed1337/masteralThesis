package driver.categoricalType;

import constants.CategoricalTypeConstants;
import constants.PreprocessFileName;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import preprocessFiles.PreprocessFile;
import utils.Utils;

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
      
      checkDistributionValues();
   }

   private void checkDistributionValues() throws IllegalArgumentException {
      if(this.fileDistributions == null){
         throw new IllegalArgumentException("The file distribution needs to be set");
      }
      
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
      
      checkUniquePreprocesFiles(pfL);
      
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
      final Predicate<PreprocessFileName> notInDistribution = (pfName)->(!this.fileDistributions.containsKey(pfName));
      
      pfL.stream()
         .map((pf)->pf.getPreprocessFileName())
         .filter(notInDistribution)
         .forEachOrdered((pfName)->{
            throw new NoSuchElementException(pfName + " wasn't set in this.fileDistributions");
         });
   }

   /**
    * Uses Utils.isListUnique internally
    * @see utils.Utils#isListUnique(List)
    * @param pfL
    * @throws IllegalArgumentException 
    */
   private void checkUniquePreprocesFiles(List<PreprocessFile> pfL) 
         throws IllegalArgumentException {
      boolean uniqueInstances = Utils.isListUnique(
         pfL.stream()
            .map(pf->pf.getPreprocessFileName())
            .collect(Collectors.toList())
      );
      
      if (!uniqueInstances) {
         throw new IllegalArgumentException("The preprocess files given contain duplicates");
      }
   }
}