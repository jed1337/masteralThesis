package driver.categoricalType;

import constants.CategoricalTypeConstants;
import constants.GeneralAttackTypeEnum;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import preprocessFiles.PreprocessFile;

public final class GeneralAttackType implements CategoricalType{
   /**
    * Returns how many unique generalAttackTypes there are
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

   /**
    * Relables the specific attack type to become the general attack type
    * @param pfL
    * @return 
    */
   @Override
   public String getRelabel(List<PreprocessFile> pfL) {
      ArrayList<String> relabels = new ArrayList<>();
      
      for (PreprocessFile pf : pfL) {
         relabels.add(
            String.format("%s:%s", pf.getSpecificAttackType(), pf.getGeneralAttackType())
         );
      }
      
      return String.join(", ", relabels);
   }
   
   /**
    * Return general
    * @return 
    */
   @Override
   public CategoricalTypeConstants getCategoricalType(){
      return CategoricalTypeConstants.GENERAL;
   }
   
   @Override
   public final void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
      Set<GeneralAttackTypeEnum> gats = new HashSet(); // Unique values
      pfL.forEach((pf)->{
         gats.add(pf.getGeneralAttackType());
      });

      for (GeneralAttackTypeEnum gat : gats) {
         Predicate<PreprocessFile> sameGAT = (pf)->pf.getGeneralAttackType() == gat;

         int sameAttackTypeCount = (int) pfL.stream()
            .filter(sameGAT)
            .count();

         pfL.stream()
            .filter(sameGAT)
            .forEach((pf)->{
               pf.setInstancesCount(
                  totalInstanceCount / (gats.size() * sameAttackTypeCount)
               );
            }
         );
      }
   }
}