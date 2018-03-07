package featureSelection.filters;

import featureSelection.AbstractFeatureSelection;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.Ranker;

/**
* This approach separates feature selection from classifier learning by selecting
* features independently from it. In this approach, the undesirable features are
* removed before the learning algorithm is used. Separating feature selection from
* classifier learning allows filter models to be faster than wrapper models. This also
* allows filter models to be applied with datasets with a large number of features.
* Another advantage is that filter models can be used with any learning algorithm.
* In contrast, wrapper models need to be executed again when a different learning
* algorithm is used Kohavi & John (1997).
*/
public abstract class FilterFeatureSelection extends AbstractFeatureSelection{
   private final ASEvaluation asEvaluation;
   private final ASSearch asSearch;
   private final String fsMethodName;

   public FilterFeatureSelection(ASEvaluation asEvaluation, String fsMethodName) {
      this.asEvaluation = asEvaluation;
      this.fsMethodName = fsMethodName;
      
      Ranker r = new Ranker();
      r.setThreshold(0.3);
      
      this.asSearch = r;
   }
   
   @Override
   protected final ASSearch getSearchMethod(){
      return this.asSearch;
   }
   
   @Override
   public final String getFSMethodName() {
      return this.fsMethodName;
   }

   @Override
   protected final ASEvaluation getASEvaluation() {
      return this.asEvaluation;
   }
   
   @Override
   protected int[] filterRankedAttributes(AttributeSelection as) throws Exception{
      return as.selectedAttributes();
   }
}