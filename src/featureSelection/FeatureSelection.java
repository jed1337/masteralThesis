package featureSelection;

import java.util.ArrayList;
import preprocessFiles.preprocessEvaluationSet.EvaluationSet;
import weka.core.Instances;

public interface FeatureSelection {
   /**
    * @param trainSet The instances to apply feature selection to
    * @param evaluationSets The same selected features are applied to 
    * each of the evaluationSets
    * @throws Exception 
    */
   public abstract void applyFeatureSelection(Instances trainSet, ArrayList<EvaluationSet> evaluationSets) throws Exception;
   
   /**
    * @return The name of the feature selection used
    * (Ex. NB, J48, "Information gain", ...)
    */
   public abstract String getFSMethodName();
}
