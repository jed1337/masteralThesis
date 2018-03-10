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
   @Override
   public String getRelabelSpecificAttack(List<PreprocessFile> pfL) {
      ArrayList<String> relabels = new ArrayList<>();

      for (PreprocessFile pf : pfL) {
         relabels.add(
            String.format("%s:%s", pf.getSpecificAttackType(), pf.getGeneralAttackType())
         );
      }

      return String.join(", ", relabels);
   }

   /**
    * @return CategoricalTypeConstants.GENERAL
    */
   @Override
   public CategoricalTypeConstants getCategoricalType(){
      return CategoricalTypeConstants.GENERAL;
   }

   @Override
<<<<<<< HEAD
   public void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
=======
   public final void setPreprocessFileCount(List<PreprocessFile> pfL, int totalInstanceCount) {
>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
      Set<GeneralAttackTypeEnum> gats = new HashSet(); // Unique values
      pfL.forEach((pf)->{
         gats.add(pf.getGeneralAttackType());
      });
<<<<<<< HEAD
      
      //todo also set distribution with respect to the specific attack type
      //tcpflood t1, t2, udpflood u; 1000 instances
      //t1 = 250, t2 = 250, u = 500;
=======

>>>>>>> 46d7ebb4cbe4bf9b987c4bfdfd55dc9c3014c8e9
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