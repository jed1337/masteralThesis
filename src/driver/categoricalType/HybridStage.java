package driver.categoricalType;

import constants.CategoricalTypeConstants;

public abstract class HybridStage implements CategoricalType{
//   /**
//    * Returns 2 since a hybrid stage should have 2 classes. Ex:
//    * (Normal, Attack), (Highrate, Lowrate), ...
//    * @param pfL (Actually useless since this function returns a constant of 2)
//    * @return 
//    */
//   @Override
//   public int getClassCount(List<PreprocessFile> pfL) {
//      return 2; //normal, attack
//   }

   /**
    * Returns a general attack type since no specific categories are present here
    * @return 
    */
   @Override
   public CategoricalTypeConstants getCategoricalType() {
      return CategoricalTypeConstants.GENERAL;
   }
}