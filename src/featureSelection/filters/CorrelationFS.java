package featureSelection.filters;

import weka.attributeSelection.CorrelationAttributeEval;

public class CorrelationFS extends FilterFeatureSelection{
   public CorrelationFS() {
      super(new CorrelationAttributeEval(), "Attribute correlation");
   }
}