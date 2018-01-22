package featureSelection;

import java.util.ArrayList;
import preprocessFiles.preprocessEvaluationSet.EvaluationSet;
import utils.UtilsFeatureSelection;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.core.Instances;

public abstract class AbstractFeatureSelection implements FeatureSelection{
   protected abstract ASEvaluation getASEvaluation();
   protected abstract ASSearch getSearchMethod();
   
   @Override
   public void applyFeatureSelection(Instances trainSet, ArrayList<EvaluationSet> evaluationSets) throws Exception {
      ASEvaluation attributeEvaluator = getASEvaluation();
      
      attributeEvaluator.buildEvaluator(trainSet);

      AttributeSelection as = new AttributeSelection();
      as.setEvaluator(attributeEvaluator);

      as.setSearch(getSearchMethod());
      
      as.SelectAttributes(trainSet);
      
      int[] selectedAttributes = as.selectedAttributes();

      System.out.println("Feature selection results:");
      System.out.println(as.toResultsString());
      
      for (EvaluationSet evaluationSet : evaluationSets) {
         evaluationSet.setInstances(
            UtilsFeatureSelection.applyFeatureSelection(
               evaluationSet.getInstances(),selectedAttributes
            )
         );
      }
   }
}