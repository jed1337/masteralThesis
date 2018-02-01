package evaluation;

import classifier.ClassifierHolder;
import constants.CharConstants;
import constants.DirectoryConstants;
import customWeka.CustomEvaluation;
import database.Database;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.core.Instances;
import weka.gui.explorer.ClassifierPanel;

public class CrossValidation implements Evaluation{
   private Instances combinedInstances;
   private final Database db;

   public CrossValidation(Database db) {
      this.db = db;
   }
   
   
   @Override
   public void setupEvaluationSets(String combinedPath) 
           throws IOException,Exception {
      this.combinedInstances = UtilsInstances.getInstances(combinedPath);
   }

   @Override
   public void applyFeatureSelection(FeatureSelection fs) 
           throws IOException,NoSuchElementException,Exception {
      fs.applyFeatureSelection(
         this.combinedInstances,
         new ArrayList<>()
      );

      this.db.insertToFeatureSelectionTable(fs);
      this.db.insertToFeatureTable(this.combinedInstances);
   }

   @Override
   public ArrayList<CustomEvaluation> evaluateClassifiers(ArrayList<ClassifierHolder> classifierHolders)
           throws Exception {
      final ArrayList<CustomEvaluation> evaluations = new ArrayList<>();

      for (ClassifierHolder ch : classifierHolders) {
         CustomEvaluation eval = evaluateIndividualClassifier(ch);
         evaluations.add(eval);

//         Insert to evaluation table
         this.db.insertToEvaluationTable(ch, eval);
      }
      return evaluations;
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