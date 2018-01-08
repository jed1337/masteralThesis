package driver.categoricalType;

import constants.CategoricalTypeConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import preprocessFiles.PreprocessFile;

public final class GeneralAttackType implements CategoricalType{
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
         relabels.add(
            String.format("%s:%s", pf.getSpecificAttackType(), pf.getGeneralAttackType())
         );
      }
      
      return String.join(", ", relabels);
   }
   
   @Override
   public CategoricalTypeConstants getCategoricalType(){
      return CategoricalTypeConstants.BINARY;
   }
   
   @Override
   public final void setPreprocessFileCount(List<PreprocessFile.PreprocessFileBuilder> pfBL, int totalInstanceCount) {
      Set<Enum<constants.GeneralAttackTypeEnum>> gats = new HashSet(); // Unique values
      pfBL.forEach((pf)->{
         gats.add(pf.getGeneralAttackType());
      });

      for (Enum<constants.GeneralAttackTypeEnum> gat : gats) {
         Predicate<PreprocessFile> sameGAT = (pf)->pf.getGeneralAttackType() == gat;

         int sameAttackTypeCount = (int) pfBL.stream()
            .filter(sameGAT)
            .count();

         pfBL.stream()
            .filter(sameGAT)
            .forEach((pf)->{
               pf.setInstancesCount(totalInstanceCount / (gats.size() * sameAttackTypeCount));
            }
         );
      }
   }
}