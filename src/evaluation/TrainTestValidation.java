package evaluation;

import classifier.ClassifierHolder;
import constants.AttributeTypeConstants;
import constants.CharConstants;
import constants.DirectoryConstants;
import constants.FileNameConstants;
import customWeka.CustomEvaluation;
import database.Database;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import preprocessFiles.preprocessAs.FormatAsText;
import preprocessFiles.preprocessEvaluationSet.EvaluationSet;
import preprocessFiles.preprocessEvaluationSet.SetupTestTrainValidation;
import utils.Utils;
import utils.UtilsClssifiers;
import weka.core.Instances;

/**
 * Class for performing train test and validation. 
 * Its ratio is train: 4/6, test: 1/6, and validation: 1/6
 * <br> 
 * The paths for train, test, and validation, are statically set as private variables
 */
public class TrainTestValidation implements Evaluation{
   private final String TRAIN_PATH      = DirectoryConstants.FORMATTED_DIR + FileNameConstants.TRAIN;
   private final String TEST_PATH       = DirectoryConstants.FORMATTED_DIR + FileNameConstants.TEST;
   private final String VALIDATION_PATH = DirectoryConstants.FORMATTED_DIR + FileNameConstants.VALIDATION;
   
   private final ArrayList<EvaluationSet> evaluationSets;
   private final Database db;

   public TrainTestValidation(Database db){
      this.db = db;
      
      this.evaluationSets = new ArrayList<>();
      this.evaluationSets.add(new EvaluationSet(this.TRAIN_PATH       , 4));
      this.evaluationSets.add(new EvaluationSet(this.TEST_PATH        , 1));
      this.evaluationSets.add(new EvaluationSet(this.VALIDATION_PATH  , 1));
   }
   
   /**
    * {@inheritDoc}
    * Sets up the train, test, and validation sets.
    */
   @Override
   public void setupEvaluationSets(String combinedPath) throws IOException, Exception{
		new SetupTestTrainValidation(combinedPath, this.evaluationSets);
      writeTestTrainValidation();
	}

   
   //todo check if we can return fs so that the this class doesn't know
   //about the DB.
   //Check if fs contains features so that, we'll pass that to db.isnertToFeaturetable
   //instead of using an actual Instances class
   @Override
   public void applyFeatureSelection(FeatureSelection fs) 
           throws IOException,NoSuchElementException,Exception {
      
      fs.applyFeatureSelection(
         getEvaluationSet(this.TRAIN_PATH), 
         this.evaluationSets
      );
      writeTestTrainValidation();

      this.db.insertToFeatureSelectionTable(fs);
      this.db.insertToFeatureTable(getEvaluationSet(this.TRAIN_PATH));

   }

   @Override
   public ArrayList<CustomEvaluation> evaluateClassifiers(ArrayList<ClassifierHolder> classifierHolders)
           throws Exception {
      final ArrayList<CustomEvaluation> evaluations = new ArrayList<>();

      for (ClassifierHolder ch : classifierHolders) {
         CustomEvaluation eval = evaluateIndividualClassifier(ch);
         evaluations.add(eval);

         //Insert to evaluation table
         this.db.insertToEvaluationTable(ch, eval);
      }
      return evaluations;
   }
   
   private CustomEvaluation evaluateIndividualClassifier(ClassifierHolder ch) throws IOException, Exception{
      //Build the classifier on the trainset
      ch.getClassifier().buildClassifier(getEvaluationSet(this.TRAIN_PATH));
      
      UtilsClssifiers.writeModel(DirectoryConstants.FORMATTED_DIR, ch);

      //Evaluate the classifier on the test set
      CustomEvaluation eval = new CustomEvaluation(getEvaluationSet(this.TRAIN_PATH));
      eval.evaluateModel(ch.getClassifier(), getEvaluationSet(this.TEST_PATH));

      //System out the results
      StringBuilder sb = new StringBuilder();
      sb.append("=== Classifier ===\n");
      sb.append(ch.getClassifier().toString()).append(CharConstants.NEW_LINE);
      sb.append("=== Dedicated test set ===\n");
      sb.append(eval.toSummaryString("=== Summary of " + ch.getClassifierName() + "===\n", false));
      sb.append(eval.toClassDetailsString("=== Detailed Accuracy By Class ===\n"));
      sb.append(eval.toMatrixString("=== Confusion Matrix ===\n"));

      //Write the results to file
      System.out.println(sb);
      Utils.writeStringFile(DirectoryConstants.FORMATTED_DIR+ch.getResultName(), sb.toString());

      return eval;
   }
   
   /**
    * Writes the test, train, and validation files to a folder
    * and also adds the class count
    * @throws IOException
    */
   private void writeTestTrainValidation() throws IOException {
      for (EvaluationSet evaluationSet : this.evaluationSets) {
         final String path = evaluationSet.getName();
         Utils.writeStringFile(path, evaluationSet.getInstances().toString());

         FormatAsText fat = new FormatAsText(path);
         fat.addClassCount(AttributeTypeConstants.ATTRIBUTE_CLASS);
      }
   }
   
   /**
    * Loops through the evaluationSets and finds the evaluation set matching the name and returns it if found
    * @return the evaluationSet matching the name
    * @throws NoSuchElementException if an evaluationSet matching the name can't be found
    */
   private Instances getEvaluationSet(String name) throws NoSuchElementException{
      for (EvaluationSet evaluationSet : this.evaluationSets) {
         if(evaluationSet.getName().equalsIgnoreCase(name)){
            return evaluationSet.getInstances();
         }
      }
      throw new NoSuchElementException("The evaluation set '"+name+"' wasn't found");
   }
}