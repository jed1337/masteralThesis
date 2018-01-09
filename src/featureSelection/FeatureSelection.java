package featureSelection;

import java.util.ArrayList;
import preprocessFiles.preprocessEvaluationSet.EvaluationSet;
import weka.core.Instances;

public interface FeatureSelection {
   public abstract void applyFeatureSelection(Instances trainSet, ArrayList<EvaluationSet> evaluationSets) throws Exception;
   public abstract String getFSMethodName();
}
