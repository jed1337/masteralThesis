package driver;

import classifier.ClassifierHolder;
import constants.AttributeTypeConstants;
import constants.CharConstants;
import constants.DirectoryConstants;
import constants.FileNameConstants;
import customWeka.CustomEvaluation;
import database.DBInterface;
import database.NoDatabase;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import preprocessFiles.PreprocessFile;
import preprocessFiles.preprocessAs.FormatAsText;
import preprocessFiles.preprocessEvaluationSet.EvaluationSet;
import preprocessFiles.preprocessEvaluationSet.SetupTestTrainValidation;
import utils.Utils;
import utils.UtilsARFF;
import utils.UtilsClssifiers;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public final class SystemTrain {
	private final ArrayList<EvaluationSet> evaluationSets;
	private final String combinedPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.COMBINED;
   private final String trainPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.TRAIN;
   private final String testPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.TEST;
   private final String validationPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.VALIDATION;

//	private final ArrayList<ClassifierHolder> classifierHolders;
//	private final List<PreprocessFile> preprocessFiles;

   private final DBInterface db;

//   private final FeatureSelection fs;

	public SystemTrain(SystemParameters sp, FeatureSelection fs) throws IOException, Exception {
      this.evaluationSets = new ArrayList<>();
//      FeatureSelection fs = fs;

      List<PreprocessFile> preprocessFiles = setupPreprocessFiles(sp.getPreprocessFiles(), sp.getRelabel());

      ArrayList<ClassifierHolder> classifierHolders = new ArrayList<>();
      classifierHolders.add(new ClassifierHolder(new J48(), "J48"));
      classifierHolders.add(new ClassifierHolder(new IBk(), "KNN"));
      classifierHolders.add(new ClassifierHolder(new NaiveBayes(), "NB "));
      classifierHolders.add(new ClassifierHolder(new RandomForest(), "RF "));
      classifierHolders.add(new ClassifierHolder(new SMO(), "SMO"));

      this.evaluationSets.add(new EvaluationSet(this.trainPath       , 4));
      this.evaluationSets.add(new EvaluationSet(this.testPath        , 1));
      this.evaluationSets.add(new EvaluationSet(this.validationPath  , 1));

//      this.db = new Mysql();
      this.db = NoDatabase.getInstance();
      this.db.insertMainTable(sp);
      
      createCombinedData(preprocessFiles, this.combinedPath);
      setupTestTrainValidation(this.combinedPath);
      applyFeatureSelection(fs);
      evaluateClassifiers(classifierHolders);
	}

   private List<PreprocessFile> setupPreprocessFiles(final List<PreprocessFile> pfL, String relabel)
			  throws IOException, Exception {
		for (PreprocessFile pf : pfL) {
			pf.setUp();
			pf.relabel(
				AttributeTypeConstants.ATTRIBUTE_CLASS,
				relabel
			);
			Utils.writePreprocessFile(pf);
		}
		return pfL;
	}

	public void setupTestTrainValidation(String combinedPath) throws IOException, Exception{
		SetupTestTrainValidation sttv = new SetupTestTrainValidation(combinedPath);
//		SetupTestTrainValidation sttv = new SetupTestTrainValidation(DirectoryConstants.FORMATTED_DIR+"Combined General (Shortened).arff");
		sttv.setTrainTestValidationPaths(this.evaluationSets);

      writeTestTrainValidation();
	}

   /**
    * Combines the data in the this.preprocessFiles and stores the output to this.combinedPath
    * @throws IOException
    * @throws IllegalArgumentException 
    */
   private void createCombinedData(List<PreprocessFile> pfL, String combinedPath) throws IOException, IllegalArgumentException {
      UtilsARFF.combineArffAndAddClassCount(
         combinedPath,
         pfL.stream()
            .map(tl->tl.getFaa().getSavePath())
            .collect(Collectors.toList()),
         AttributeTypeConstants.ATTRIBUTE_CLASS
      );
   }

   /**
    * Loops through the evaluationSets and finds the evaluation set matching the name and returns it if found
    * @return
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

   public void applyFeatureSelection(FeatureSelection fs)
           throws IOException, NoSuchElementException, Exception {
      fs.applyFeatureSelection(
         getEvaluationSet(this.trainPath), 
         this.evaluationSets
      );
      writeTestTrainValidation();

      this.db.insertToFeatureSelectionTable(fs);
      this.db.insertToFeatureTable(getEvaluationSet(this.trainPath));
   }

   /**
    * Writes the test, train, and validation files to a folder
    * and also adds the class count
    * @throws IOException
    */
   public void writeTestTrainValidation() throws IOException {
      for (EvaluationSet evaluationSet : this.evaluationSets) {
         final String path = evaluationSet.getName();
         Utils.writeStringFile(path, evaluationSet.getInstances().toString());

         FormatAsText fat = new FormatAsText(path);
         fat.addClassCount(AttributeTypeConstants.ATTRIBUTE_CLASS);
      }
   }

   public ArrayList<CustomEvaluation> evaluateClassifiers(ArrayList<ClassifierHolder> classifierHolders) throws Exception{
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
      ch.getClassifier().buildClassifier(getEvaluationSet(this.trainPath));
      UtilsClssifiers.writeModel(DirectoryConstants.FORMATTED_DIR, ch);

      CustomEvaluation eval = new CustomEvaluation(getEvaluationSet(this.trainPath));
      eval.evaluateModel(ch.getClassifier(), getEvaluationSet(this.testPath));

      StringBuilder sb = new StringBuilder();
      sb.append("=== Classifier ===\n");
      sb.append(ch.getClassifier().toString()).append(CharConstants.NEW_LINE);
      sb.append("=== Dedicated test set ===\n");
      sb.append(eval.toSummaryString("=== Summary of " + ch.getClassifierName() + "===\n", false));
      sb.append(eval.toClassDetailsString("=== Detailed Accuracy By Class ===\n"));
      sb.append(eval.toMatrixString("=== Confusion Matrix ===\n"));

      System.out.println(sb);
      Utils.writeStringFile(DirectoryConstants.FORMATTED_DIR+ch.getResultName(), sb.toString());

      return eval;
   }
}