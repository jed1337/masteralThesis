package evaluation;

import classifier.ClassifierHolder;
import customWeka.CustomEvaluation;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface Evaluation {
   public void setupTestTrainValidation(String combinedPath) throws IOException, Exception;
   public void applyFeatureSelection(FeatureSelection fs) throws IOException, NoSuchElementException, Exception;
   public ArrayList<CustomEvaluation> evaluateClassifiers(ArrayList<ClassifierHolder> classifierHolders) throws Exception;
}