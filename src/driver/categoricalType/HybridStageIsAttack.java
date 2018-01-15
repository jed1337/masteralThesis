package driver.categoricalType;

import constants.SpecificAttackTypeEnum;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class HybridStageIsAttack extends HybridStage{
   @Override
   public String getRelabelSpecificAttack(List<PreprocessFile> pfL) {
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
   public void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
//<editor-fold defaultstate="collapsed" desc="Old Code">
//      Set<SpecificAttackTypeEnum> sats = new HashSet(); // Unique values
//      pfL.forEach((pf)->{
//         sats.add(pf.getSpecificAttackType());
//      });
//
//      /**
//       * //Todo, fix
//       * The loop repeats for an unnecessary amount of iterations,
//       * initialising the same value to total instance count
//       */
//      for (SpecificAttackTypeEnum sat : sats) {
//         Predicate<PreprocessFile> sameSpecificAttackTypeCategory = (pf)->{
//            if(sat == SpecificAttackTypeEnum.NORMAL){
//               return pf.getSpecificAttackType() == SpecificAttackTypeEnum.NORMAL;
//            } else{ //Assumes anything != normal is an attack
//               return pf.getSpecificAttackType() != SpecificAttackTypeEnum.NORMAL;
//            }
//         };
//
//         Predicate<PreprocessFile> sameSAT = (pf)->pf.getSpecificAttackType().equals(sat);
//         int sameSATCount = (int) pfL.stream()
//            .filter(sameSAT)
//            .count();
//
//         pfL.stream()
//            .filter(sameSpecificAttackTypeCategory)
//            .filter(sameSAT)
//            .forEach((pf)->{
//               // divided by 2 since we have categories: attack or not
//               int attackTypeCategoryTotalInstanceCount = totalInstanceCount/2;
//               pf.setInstancesCount(
//                 attackTypeCategoryTotalInstanceCount / (sats.size() * sameSATCount)
//               );
//            }
//         );
//      }
//</editor-fold>

      //Divided by 2 since we have 2 attack type categories: normal, and attack
      int halfTotalInstancesCount = totalInstanceCount/2;

      int normalPFLs = (int) pfL.stream()
         .filter(pf->pf.getSpecificAttackType()==SpecificAttackTypeEnum.NORMAL)
         .count();
      
      int distinctAttackPFLs = (int) pfL.stream()
         .filter(pf->pf.getSpecificAttackType()!=SpecificAttackTypeEnum.NORMAL)
         .map(pf->pf.getSpecificAttackType())//Turn into specificAttackTypeEnum
         .distinct()//get only 1 of each specific attacktype enum
         .count();

      for (PreprocessFile pf : pfL) {
         if(pf.getSpecificAttackType()==SpecificAttackTypeEnum.NORMAL){
            pf.setInstancesCount(
               halfTotalInstancesCount/normalPFLs
            );
         }
         else{ //An attack (Not normal)
            //If there is more than 1 instance of a specific attack type,
            //split the number of instances for that specific attack type between them
            int sameSpecificAttackTypeCount = (int) pfL.stream()
               .filter(streamPF-> streamPF.getSpecificAttackType() == pf.getSpecificAttackType())
               .count();
            
            pf.setInstancesCount(
               halfTotalInstancesCount/(distinctAttackPFLs * sameSpecificAttackTypeCount)
            );
         }
      }
   }
}