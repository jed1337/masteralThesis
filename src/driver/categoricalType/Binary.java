package driver.categoricalType;

import constants.CategoricalTypeConstants;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class Binary implements CategoricalType{
   
   /**
    * //Todo, make not return the magic number 2
    * @param pfL
    * @return 
    */
   @Override
   public int getClassCount(List<PreprocessFile> pfL) {
      return (int) pfL.stream()
         .map(pf->pf.getGeneralAttackType())
         .distinct()
         .count();
   }

   @Override
   public String getRelabel(List<PreprocessFile> pfL) {
      ArrayList<String> relabels = new ArrayList<>();
      
      for (PreprocessFile pf : pfL) {
         String generalAttackType = pf.getGeneralAttackType().name();
         
         for (String specificAttackType : pf.getSpecificAttackTypes()) {
            relabels.add(
               String.format("%s:%s", specificAttackType, generalAttackType)
            );
         }
      }
      
      return String.join(", ", relabels);
   }
   
   @Override
   public CategoricalTypeConstants getCategoricalType(){
      return CategoricalTypeConstants.BINARY;
   }
}