package driver.categoricalType;

import constants.CategoricalTypeConstants;
import java.util.List;
import preprocessFiles.PreprocessFile;

public abstract class HybridStage implements CategoricalType{
   /**
    * Returns 2 since a hybrid stage should have 2 classes. Ex:
    * (Normal, Attack), (Highrate, Lowrate), ...
    * @param pfL (Actually useless since this function returns a constant of 2)
    * @return 
    */
   @Override
   public int getClassCount(List<PreprocessFile> pfL) {
      return 2; //normal, attack
   }

   @Override
   public CategoricalTypeConstants getCategoricalType() {
      return CategoricalTypeConstants.GENERAL;
   }

   @Override
   public abstract String getRelabel(List<PreprocessFile> pfL);
}