package driver.categoricalType;

import constants.CategoricalTypeConstants;
import constants.SpecificAttackTypeEnum;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import preprocessFiles.PreprocessFile;

public final class SpecificAttackType implements CategoricalType{
   /**
    * Returns how many unique specificAttackTypes there are
    * @param pfL
    * @return 
    */
   @Override
   public int getClassCount(List<PreprocessFile> pfL) {
      Set<SpecificAttackTypeEnum> specificTypes = new HashSet<>();
      int specEnumSize = 0;
      for (PreprocessFile pf : pfL) {
         specificTypes.addAll(Arrays.asList(pf.getSpecificAttackType()));
      }
      
      specEnumSize = specificTypes.size();
      
      Set<String> specificTypesString = new HashSet<>();
      int specStringSize = 0;
      for (PreprocessFile pf : pfL) {
         specificTypesString.addAll(Arrays.asList(pf.getSpecificAttackType().getValue()));
      }
      
      specStringSize = specificTypesString.size();
      

      return specEnumSize;
//      return specificTypes.size();
   }

   /**
    * Returns a blank String. That means not to relabel anything.
    * @param pfL
    * @return 
    */
   @Override
   public String getRelabelSpecificAttack(List<PreprocessFile> pfL) {
      return "";
   }
   
   /**
    * Returns a Specific attack type
    * @return 
    */
   @Override
   public CategoricalTypeConstants getCategoricalType() {
      return CategoricalTypeConstants.SPECIFIC;
   }
   
   /**
    * Sets the preprocessFileCount based on the specific attack type
    * @param pfL
    * @param totalInstanceCount 
    */
   @Override
   public final void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
      Set<SpecificAttackTypeEnum> sats = new HashSet(); // Unique values
      pfL.forEach((pf)->{
         sats.add(pf.getSpecificAttackType());
      });
      

      for (SpecificAttackTypeEnum sat : sats) {
         Predicate<PreprocessFile> sameSAT = (pf)->pf.getSpecificAttackType().equals(sat);

         int sameSATCount = (int) pfL.stream()
            .filter(sameSAT)
            .count();

         pfL.stream()
            .filter(sameSAT)
            .forEach((pf)->{
               pf.setInstancesCount(
               totalInstanceCount / (sats.size() * sameSATCount));
            }
         );
      }
   }
}