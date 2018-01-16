package driver.categoricalType;

import constants.CategoricalTypeConstants;

public abstract class HybridStage implements CategoricalType{
   /**
    * Returns a general attack type since no specific categories are present here
    * @return 
    */
   @Override
   public CategoricalTypeConstants getCategoricalType() {
      return CategoricalTypeConstants.GENERAL;
   }
}