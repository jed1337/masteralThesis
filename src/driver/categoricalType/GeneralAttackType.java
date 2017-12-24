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
   public final void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
      Set<Enum<constants.GeneralAttackType>> gats = new HashSet(); // Unique values
      pfL.forEach((pf)->{
         gats.add(pf.getGeneralAttackType());
      });

      for (Enum<constants.GeneralAttackType> gat : gats) {
         Predicate<PreprocessFile> sameGAT = (pf)->pf.getGeneralAttackType() == gat;

         int sameAttackTypeCount = (int) pfL.stream()
            .filter(sameGAT)
            .count();

         pfL.stream()
            .filter(sameGAT)
            .forEach((pf)->{
               pf.setInstancesCount(
               totalInstanceCount / (gats.size() * sameAttackTypeCount));
            }
         );
      }
   }
}