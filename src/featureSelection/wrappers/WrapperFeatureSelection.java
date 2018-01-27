package featureSelection.wrappers;

import featureSelection.AbstractFeatureSelection;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Classifier;

/**
 * Uses a separate Classifier to select features
 */
public abstract class WrapperFeatureSelection extends AbstractFeatureSelection{
   private final WrapperSubsetEval wEval;
   private final ASSearch asSearch;
   private final String fsMethodName;

   public WrapperFeatureSelection(Classifier classifier, String fsMethodName) {
      this.wEval = new WrapperSubsetEval();
      this.wEval.setClassifier(classifier);
      this.wEval.setFolds(5);
      
      this.fsMethodName = fsMethodName;
      
      this.asSearch = new BestFirst();
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
      return this.wEval;
   }
   
   @Override
   protected int[] filterRankedAttributes(AttributeSelection as) throws Exception{
      //No filtering, just return the already selected attributes
      return as.selectedAttributes();
   }
}