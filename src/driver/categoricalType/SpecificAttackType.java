package driver.categoricalType;

import constants.CategoricalTypeConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import preprocessFiles.PreprocessFile;

public final class SpecificAttackType implements AttackType{
   @Override
   public int getClassCount(List<PreprocessFile> pfL) {
      Set<String> specificTypes = new HashSet<>();
      for (PreprocessFile pf : pfL) {
         specificTypes.addAll(Arrays.asList(pf.getSpecificAttackTypes()));
      }
      
      return specificTypes.size();
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
      return CategoricalTypeConstants.NOMINAL;
   }
}