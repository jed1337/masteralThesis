package driver.categoricalType;

import constants.SpecificAttackTypeEnum;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import preprocessFiles.PreprocessFile;

public final class HybridStageIsAttack extends HybridStage{
//   private final String NORMAL = "normal";
//   private final String ATTACK = "attack";
   
   @Override
   public String getRelabel(List<PreprocessFile> pfL) {
      ArrayList<String> relabels = new ArrayList<>();
      
      for (PreprocessFile pf : pfL) {
         final SpecificAttackTypeEnum sat = pf.getSpecificAttackType();
         relabels.add(String.format("%s:%s", sat,
            sat == SpecificAttackTypeEnum.NORMAL? "normal": "attack")
         );
      }
      
      return String.join(", ", relabels);
   }
   
   @Override
   public final void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
      Set<SpecificAttackTypeEnum> sats = new HashSet(); // Unique values
      pfL.forEach((pf)->{
         sats.add(pf.getSpecificAttackType());
      });

      /**
       * //Todo, fix
       * The loop repeats for an unnecessary amount of times, initialising the 
       * same value to total instance count
       */
      for (SpecificAttackTypeEnum sat : sats) {
         Predicate<PreprocessFile> sameCategory = (pf)->{
            if(sat == SpecificAttackTypeEnum.NORMAL){
               return pf.getSpecificAttackType() == SpecificAttackTypeEnum.NORMAL;
            } else{ //Assumes anything != normal is an attack
               return pf.getSpecificAttackType() != SpecificAttackTypeEnum.NORMAL;
            }
         };
            
         Predicate<PreprocessFile> sameSAT = (pf)->pf.getSpecificAttackType().equals(sat);

         int sameSATCount = (int) pfL.stream()
            .filter(sameSAT)
            .count();

         pfL.stream()
            .filter(sameCategory)
            .forEach((pf)->{
               pf.setInstancesCount(
                 totalInstanceCount / (sats.size() * sameSATCount)
               );
            }
         );
      }
   }
}