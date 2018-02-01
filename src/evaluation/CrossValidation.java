package evaluation;

import classifier.ClassifierHolder;
import constants.CharConstants;
import constants.DirectoryConstants;
import customWeka.CustomEvaluation;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.core.Attribute;
import weka.core.Instances;

public final class CrossValidation implements Evaluation{
   private Instances combinedInstances;

   @Override
   public void setupEvaluationSets(String combinedPath) 
           throws IOException,Exception {
      this.combinedInstances = UtilsInstances.getInstances(combinedPath);
   }

   @Override
   public Enumeration<Attribute> applyFeatureSelection(FeatureSelection fs) 
           throws IOException,NoSuchElementException,Exception {
      
      //We pass an empty ArrayList<>() since we don't have any other
      //instances to apply the features selection to, just this.combinedInstances
      fs.applyFeatureSelection(
         this.combinedInstances,
         new ArrayList<>()
      );

      return this.combinedInstances.enumerateAttributes();
   }

   @Override
   public HashMap<String, CustomEvaluation> evaluateClassifiers(ArrayList<ClassifierHolder> classifierHolders)
           throws Exception {
      final HashMap<String, CustomEvaluation> hmEval = new HashMap<>();

      for (ClassifierHolder ch : classifierHolders) {
         CustomEvaluation eval = evaluateIndividualClassifier(ch);
         Utils.addToMap(hmEval, ch.getClassifierName(), eval);
      }
      return hmEval;
   }
   
   private CustomEvaluation evaluateIndividualClassifier(ClassifierHolder ch) throws IOException, Exception{
      //Don't build the classifier in Cross validation since Weka says so
      //https://weka.wikispaces.com/Use+WEKA+in+your+Java+code#Classification-Cross-validation
      CustomEvaluation eval = new CustomEvaluation(this.combinedInstances);
      eval.crossValidateModel(ch.getClassifier(), this.combinedInstances, 10, new Random(1));
      
      UtilsClssifiers.writeModel(DirectoryConstants.FORMATTED_DIR, ch);
      
      //System out the results
      StringBuilder sb = new StringBuilder();
      sb.append("=== Classifier ===\n");
      sb.append(ch.getClassifier().toString()).append(CharConstants.NEW_LINE);
      sb.append("=== Cross validation ===\n");
      sb.append(eval.toSummaryString("=== Summary of " + ch.getClassifierName() + "===\n", false));
      sb.append(eval.toClassDetailsString("=== Detailed Accuracy By Class ===\n"));
      sb.append(eval.toMatrixString("=== Confusion Matrix ===\n"));
      
      //Write the results to file
      System.out.println(sb);
      Utils.writeStringFile(DirectoryConstants.FORMATTED_DIR + 
              ch.getResultName(), sb.toString());

      return eval;
   }
}