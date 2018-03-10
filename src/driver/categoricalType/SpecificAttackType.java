package driver.categoricalType;

import constants.CategoricalTypeConstants;
import constants.SpecificAttackTypeEnum;
<<<<<<< HEAD
=======
import java.util.Arrays;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import preprocessFiles.PreprocessFile;

public final class SpecificAttackType implements CategoricalType{
   /**
<<<<<<< HEAD
=======
    * Returns how many unique specificAttackTypes there are
    * @param pfL
    * @return 
    */
   @Override
   public int getClassCount(List<PreprocessFile> pfL) {
      Set<SpecificAttackTypeEnum> specificTypes = new HashSet<>();
      for (PreprocessFile pf : pfL) {
         specificTypes.addAll(Arrays.asList(pf.getSpecificAttackType()));
      }
      
      return specificTypes.size();
   }

   /**
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
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
<<<<<<< HEAD
   public void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
=======
   public final void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
      Set<SpecificAttackTypeEnum> sats = new HashSet(); // Unique values
      pfL.forEach((pf)->{
         sats.add(pf.getSpecificAttackType());
      });

      for (SpecificAttackTypeEnum sat : sats) {
<<<<<<< HEAD
         Predicate<PreprocessFile> sameSAT = (pf)->pf.getSpecificAttackType().equals(sat);
=======
         Predicate<PreprocessFile> sameSAT = (pf)->pf.getSpecificAttackType() == sat;
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9

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