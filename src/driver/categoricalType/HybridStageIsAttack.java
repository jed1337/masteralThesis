package driver.categoricalType;

import java.util.ArrayList;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class HybridStageIsAttack extends HybridStage{
   
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
}