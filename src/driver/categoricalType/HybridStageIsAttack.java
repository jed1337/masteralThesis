package driver.categoricalType;

import constants.SpecificAttackTypeEnum;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class HybridStageIsAttack extends HybridIntermediaryStage{
   @Override
   public String getRelabelSpecificAttack(List<PreprocessFile> pfL) {
      ArrayList<String> relabels = new ArrayList<>();
      for (PreprocessFile pf : pfL) {
<<<<<<< HEAD
         final SpecificAttackTypeEnum sat = pf.getSpecificAttackType();
         relabels.add(String.format("%s:%s", sat,
            sat == SpecificAttackTypeEnum.NORMAL? "normal": "attack")
=======
         relabels.add(
            String.format("%s:%s", pf.getSpecificAttackType(),
               pf.getSpecificAttackType().getValue().equalsIgnoreCase(NORMAL)? NORMAL: ATTACK)
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
         );
      }
      
      return String.join(", ", relabels);
   }
   
   @Override
<<<<<<< HEAD
   public void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
      int halfTotalInstancesCount = totalInstanceCount/2;
=======
   public final void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
      Set<String> sats = new HashSet(); // Unique values
      pfL.forEach((pf)->{
         sats.add(pf.getSpecificAttackType().getValue());
      });

      /**
       * //Todo, fix
       * The loop repeats for an unnecessary amount of times, initialising the 
       * same value to total instance count
       */
      for (String sat : sats) {
         
         Predicate<PreprocessFile> sameCategory = (pf)->{
            if(sat.equalsIgnoreCase(NORMAL)){
               return pf.getSpecificAttackType().getValue().equalsIgnoreCase(NORMAL);
            } else{ //Assumes anything != normal is an attack
               return !pf.getSpecificAttackType().getValue().equalsIgnoreCase(NORMAL);
            }
         };
            
         Predicate<PreprocessFile> sameSAT = (pf)->pf.getSpecificAttackType().equals(sat);
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9

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