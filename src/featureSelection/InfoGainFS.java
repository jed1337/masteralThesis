package featureSelection;

import weka.attributeSelection.InfoGainAttributeEval;

public class InfoGainFS extends FilterFeatureSelection{
   public InfoGainFS(){
      super(new InfoGainAttributeEval(), "Information gain");
   }
}