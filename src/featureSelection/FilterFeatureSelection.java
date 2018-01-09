package featureSelection;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.Ranker;

public abstract class FilterFeatureSelection extends AbstractFeatureSelection{
   private final ASEvaluation asEvaluation;
   private final ASSearch asSearch;
   private final String fsMethodName;

   public FilterFeatureSelection(ASEvaluation asEvaluation, String fsMethodName) {
      this.asEvaluation = asEvaluation;
      this.fsMethodName = fsMethodName;
      this.asSearch = new Ranker();
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
}