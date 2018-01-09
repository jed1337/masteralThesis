package featureSelection;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.core.Instances;

public abstract class AbstractFeatureSelection implements FeatureSelection{
   protected abstract ASEvaluation getASEvaluation();
   protected abstract ASSearch getSearchMethod();
   
   @Override
   public void featureSelection(Instances trainSet) throws Exception {
      ASEvaluation attributeEvaluator = getASEvaluation();
      
      attributeEvaluator.buildEvaluator(trainSet);

      AttributeSelection as = new AttributeSelection();
      as.setEvaluator(attributeEvaluator);

      as.setSearch(getSearchMethod());
      
      as.SelectAttributes(trainSet);

//      this.selectedAttributes = as.selectedAttributes();
//
//      System.out.println("Feature selection results:");
//      System.out.println(as.toResultsString());
   }
}