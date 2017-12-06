package featureSelection;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public final class FeatureSelection {
   public final int[] selectedAttributes;
   
   public FeatureSelection(ASEvaluation attributeEvaluator, ASSearch searchMethod, Instances trainSet) throws Exception{
      attributeEvaluator.buildEvaluator(trainSet);
      
      AttributeSelection as = new AttributeSelection();
      as.setEvaluator(attributeEvaluator);
      as.setSearch(searchMethod);
      as.SelectAttributes(trainSet);
      
      this.selectedAttributes = as.selectedAttributes();
      
      System.out.println("Results:");
      System.out.println(as.toResultsString());
   }
   
   public Instances applyFeatureSelection(Instances instances) throws Exception{
      return applyFeatureSelection(instances, this.selectedAttributes);
   }
   
   public static Instances applyFeatureSelection(Instances instances, int[] selectedAttributes) throws Exception{
      Remove remove = new Remove();
      remove.setAttributeIndicesArray(selectedAttributes);
      remove.setInvertSelection(true);
      remove.setInputFormat(instances);

      return Filter.useFilter(instances, remove);
   }

   public int[] getSelectedAttributes() {
      return selectedAttributes;
   }
}