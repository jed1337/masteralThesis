package driver.categoricalType;

import constants.CategoricalTypeConstants;

/**
 * This class exists even though only HybridStageIsAttack extends it
 * since in the future we may want to put more layers in the Hybrid approach.<br>
 * Ex: isAttack, isHighrate, specific highrate type
 * @author 
 */
public abstract class HybridIntermediaryStage implements CategoricalType{
   /**
    * Returns a general attack type since given that this class 
    * is an intermediary stage, no specific categories can be present here
    * @return CategoricalTypeConstants.GENERAL
    */
   @Override
   public CategoricalTypeConstants getCategoricalType() {
      return CategoricalTypeConstants.GENERAL;
   }
}