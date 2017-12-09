package featureSelection;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public final class FeatureSelection {
   private final int[] selectedAttributes;
   
   /**
    * Sets selectedAttributes.
    * @param attributeEvaluator (Wrappers, filters, etc)
    * @param searchMethod (Best first, greedy stepwise, etc)
    * @param trainSet
    * @throws Exception 
    */
   public FeatureSelection(ASEvaluation attributeEvaluator, ASSearch searchMethod, Instances trainSet) throws Exception{
      attributeEvaluator.buildEvaluator(trainSet);
      
      AttributeSelection as = new AttributeSelection();
      as.setEvaluator(attributeEvaluator);
      as.setSearch(searchMethod);
      as.SelectAttributes(trainSet);
      
      this.selectedAttributes = as.selectedAttributes();
      
      System.out.println("Feature selection results:");
      System.out.println(as.toResultsString());
   }
   
   /**
    * Since we have set selectedAttributes via constructor, (instances, selectedAttributes) can be passed <p>
    * to the static version of this method
    * 
    * @param instances
    * @return
    * @throws Exception 
    */
   public Instances applyFeatureSelection(Instances instances) throws Exception{
      return FeatureSelection.applyFeatureSelection(instances, this.selectedAttributes);
   }

   public int[] getSelectedAttributes() {
      return selectedAttributes;
   }
   
   /**
    * If there are already selectedAttributes available, apply feature selection. <p>
    * No need to instantiate a class for that.
    * @param instances
    * @param selectedAttributes
    * @return
    * @throws Exception 
    */
   public static Instances applyFeatureSelection(Instances instances, int[] selectedAttributes) throws Exception{
      Remove remove = new Remove();
      remove.setAttributeIndicesArray(selectedAttributes);
      remove.setInvertSelection(true);
      remove.setInputFormat(instances);

      return Filter.useFilter(instances, remove);
   }
}