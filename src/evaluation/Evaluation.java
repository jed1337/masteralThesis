package evaluation;

import classifier.ClassifierHolder;
import customWeka.CustomEvaluation;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface Evaluation {
   /**
    * @param combinedPath The combined arff file. All further processing
    * (feature selection, evaluation) is done on this arff path. 
    * <br>
    * However, it isn't modified, instead additional files based on it are created,
    * and those are the ones that are modified
    * @throws IOException
    * @throws Exception 
    */
   public void setupEvaluationSets(String combinedPath) throws IOException, Exception;
   
   public void applyFeatureSelection(FeatureSelection fs) throws IOException, NoSuchElementException, Exception;
   public ArrayList<CustomEvaluation> evaluateClassifiers(ArrayList<ClassifierHolder> classifierHolders) throws Exception;
}