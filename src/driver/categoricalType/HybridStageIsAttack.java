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
         final SpecificAttackTypeEnum sat = pf.getSpecificAttackType();
         relabels.add(String.format("%s:%s", sat,
            sat == SpecificAttackTypeEnum.NORMAL? "normal": "attack")
         );
      }
      
      return String.join(", ", relabels);
   }
   
   @Override
   public void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
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

   @Override
   public float normalToNoiseRatio(List<PreprocessFile> pfL) {
      System.err.println("Dummy value");
      return -1.0f;
   }
}