package driver.categoricalType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import preprocessFiles.PreprocessFile;

public final class HybridStageIsAttack extends HybridStage{
   private final String NORMAL = "normal";
   private final String ATTACK = "attack";
   
   @Override
   public String getRelabel(List<PreprocessFile> pfL) {
      ArrayList<String> relabels = new ArrayList<>();
      
      for (PreprocessFile pf : pfL) {
         relabels.add(
            String.format("%s:%s", pf.getSpecificAttackType(),
               pf.getSpecificAttackType().equalsIgnoreCase(NORMAL)? NORMAL: ATTACK)
         );
      }
      
      return String.join(", ", relabels);
   }
   
   @Override
   public final void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
      Set<String> sats = new HashSet(); // Unique values
      pfL.forEach((pf)->{
         sats.add(pf.getSpecificAttackType());
      });

      /**
       * //Todo, fix
       * The loop repeats for an unnecessary amount of times, initialising the 
       * same value to total instance count
       */
      for (String sat : sats) {
         
         Predicate<PreprocessFile> sameCategory = (pf)->{
            if(sat.equalsIgnoreCase(NORMAL)){
               return pf.getSpecificAttackType().equalsIgnoreCase(NORMAL);
            } else{ //Assumes anything != normal is an attack
               return !pf.getSpecificAttackType().equalsIgnoreCase(NORMAL);
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