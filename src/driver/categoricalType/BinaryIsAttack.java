package driver.categoricalType;

import constants.CategoricalTypeConstants;
import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;

public class BinaryIsAttack implements CategoricalType{
   /**
    * //Todo, make not return the magic number 2
    * @param pfL
    * @return 
    */
   @Override
   public int getClassCount(List<PreprocessFile> pfL) {
      return 2; //normal, attack
   }

   @Override
   public String getRelabel(List<PreprocessFile> pfL) {
      ArrayList<String> relabels = new ArrayList<>();
      
      for (PreprocessFile pf : pfL) {
         for (String specificAttackType : pf.getSpecificAttackTypes()) {
            relabels.add(
               String.format("%s:%s", specificAttackType, 
                  specificAttackType.equalsIgnoreCase("normal")? "normal": "attack")
            );
         }
      }
      
      return String.join(", ", relabels);
   }

   @Override
   public CategoricalTypeConstants getCategoricalType() {
      return CategoricalTypeConstants.BINARY;
   }
}