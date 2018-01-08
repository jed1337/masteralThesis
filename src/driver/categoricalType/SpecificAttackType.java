package driver.categoricalType;

import constants.CategoricalTypeConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import preprocessFiles.PreprocessFile;

public final class SpecificAttackType implements CategoricalType{
   @Override
   public int getClassCount(List<PreprocessFile> pfL) {
      return (int) pfL.stream()
         .map(pf->pf.getSpecificAttackType())
         .distinct()
         .count();
   }

   /**
    * Returns a blank String. That means not to relabel anything.
    * @param pfL
    * @return 
    */
   @Override
   public String getRelabel(List<PreprocessFile> pfL) {
      return "";
   }
   
   @Override
   public CategoricalTypeConstants getCategoricalType() {
      return CategoricalTypeConstants.SPECIFIC;
   }
   
   @Override
   public final void setPreprocessFileCount(List<PreprocessFile.PreprocessFileBuilder> pfBL, int totalInstanceCount) {
      Set<String> sats = new HashSet(); // Unique values
      pfBL.forEach((pf)->{
         sats.add(pf.getSpecificAttackType());
      });

      for (String sat : sats) {
         Predicate<PreprocessFile> sameSAT = (pf)->pf.getSpecificAttackType().equals(sat);

         int sameSATCount = (int) pfBL.stream()
            .filter(sameSAT)
            .count();

         pfBL.stream()
            .filter(sameSAT)
            .forEach((pf)->{
               pf.setInstancesCount(totalInstanceCount / (sats.size() * sameSATCount));
            }
         );
      }
   }
}