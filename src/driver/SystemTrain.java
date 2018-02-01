package driver;

import classifier.ClassifierHolder;
import constants.AttributeTypeConstants;
import constants.DirectoryConstants;
import constants.FileNameConstants;
import database.Database;
import database.NoDatabase;
import evaluation.TrainTestValidation;
import featureSelection.FeatureSelection;
import featureSelection.NoFeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import preprocessFiles.PreprocessFile;
import utils.Utils;
import utils.UtilsARFF;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

public final class SystemTrain {
//	  private final ArrayList<EvaluationSet> evaluationSets;
//   private final String TRAIN_PATH      = DirectoryConstants.FORMATTED_DIR + FileNameConstants.TRAIN;
//   private final String TEST_PATH       = DirectoryConstants.FORMATTED_DIR + FileNameConstants.TEST;
//   private final String VALIDATION_PATH = DirectoryConstants.FORMATTED_DIR + FileNameConstants.VALIDATION;

   private final Database db;

	private SystemTrain(SystemTrain.Buidler builder, SystemParameters sp) throws IOException, Exception {
      this.db = builder.db;
      
      FeatureSelection fs = builder.fs;
      String combinedPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.COMBINED;
      
//      this.evaluationSets = new ArrayList<>();
      
      List<PreprocessFile> preprocessFiles = setupPreprocessFiles(sp.getPreprocessFiles(), sp.getRelabel());
      this.db.insertMainTable(sp);
      
      createCombinedData(
         preprocessFiles, 
         combinedPath
      );

      execute(fs, combinedPath);
	}

	private SystemTrain(
      SystemTrain.Buidler builder, String combinedPath,
      String systemType, String categoricalType, Float noiseLevel,
      String dataset, String extractionTool)
      throws IOException, Exception {
      
      FeatureSelection fs = builder.fs;
      this.db = builder.db;
      
//      this.evaluationSets = new ArrayList<>();
      
      this.db.insertMainTable(systemType, categoricalType, noiseLevel, dataset, extractionTool);

      execute(fs, combinedPath);
	}
   
   private void execute(FeatureSelection fs, String combinedPath) throws Exception {
      ArrayList<ClassifierHolder> classifierHolders = new ArrayList<>();
      classifierHolders.add(new ClassifierHolder(new J48(), "J48"));
      classifierHolders.add(new ClassifierHolder(new IBk(), "KNN"));
      classifierHolders.add(new ClassifierHolder(new NaiveBayes(), "NB "));
      classifierHolders.add(new ClassifierHolder(new RandomForest(), "RF "));
      classifierHolders.add(new ClassifierHolder(new SMO(), "SMO"));

//      this.evaluationSets.add(new EvaluationSet(this.TRAIN_PATH       , 4));
//      this.evaluationSets.add(new EvaluationSet(this.TEST_PATH        , 1));
//      this.evaluationSets.add(new EvaluationSet(this.VALIDATION_PATH  , 1));
//
//      setupTestTrainValidation(combinedPath);
//      applyFeatureSelection(fs);
//      evaluateClassifiers(classifierHolders);
      TrainTestValidation ttv = new TrainTestValidation(this.db);
      ttv.setupTestTrainValidation(combinedPath);
      ttv.applyFeatureSelection(fs);
      ttv.evaluateClassifiers(classifierHolders);
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

//	public void setupTestTrainValidation(String combinedPath) throws IOException, Exception{
//		SetupTestTrainValidation sttv = new SetupTestTrainValidation(combinedPath);
//		sttv.setTrainTestValidationPaths(this.evaluationSets);
//
//      writeTestTrainValidation();
//	}

//   /**
//    * Loops through the evaluationSets and finds the evaluation set matching the name and returns it if found
//    * @return the evaluationSet matching the name
//    * @throws NoSuchElementException if an evaluationSet matching the name can't be found
//    */
//   private Instances getEvaluationSet(String name) throws NoSuchElementException{
//      for (EvaluationSet evaluationSet : this.evaluationSets) {
//         if(evaluationSet.getName().equalsIgnoreCase(name)){
//            return evaluationSet.getInstances();
//         }
//      }
//      throw new NoSuchElementException("The evaluation set '"+name+"' wasn't found");
//   }

//   public void applyFeatureSelection(FeatureSelection fs)
//           throws IOException, NoSuchElementException, Exception {
//      fs.applyFeatureSelection(
//         getEvaluationSet(this.TRAIN_PATH), 
//         this.evaluationSets
//      );
//      writeTestTrainValidation();
//
//      this.db.insertToFeatureSelectionTable(fs);
//      this.db.insertToFeatureTable(getEvaluationSet(this.TRAIN_PATH));
//   }

//   /**
//    * Writes the test, train, and validation files to a folder
//    * and also adds the class count
//    * @throws IOException
//    */
//   public void writeTestTrainValidation() throws IOException {
//      for (EvaluationSet evaluationSet : this.evaluationSets) {
//         final String path = evaluationSet.getName();
//         Utils.writeStringFile(path, evaluationSet.getInstances().toString());
//
//         FormatAsText fat = new FormatAsText(path);
//         fat.addClassCount(AttributeTypeConstants.ATTRIBUTE_CLASS);
//      }
//   }

//   public ArrayList<CustomEvaluation> evaluateClassifiers(ArrayList<ClassifierHolder> classifierHolders) throws Exception{
//      final ArrayList<CustomEvaluation> evaluations = new ArrayList<>();
//
//      for (ClassifierHolder ch : classifierHolders) {
//         CustomEvaluation eval = evaluateIndividualClassifier(ch);
//         evaluations.add(eval);
//
//         //Insert to evaluation table
//         this.db.insertToEvaluationTable(ch, eval);
//      }
//      return evaluations;
//   }

//   private CustomEvaluation evaluateIndividualClassifier(ClassifierHolder ch) throws IOException, Exception{
//      ch.getClassifier().buildClassifier(getEvaluationSet(this.TRAIN_PATH));
//      UtilsClssifiers.writeModel(DirectoryConstants.FORMATTED_DIR, ch);
//
//      CustomEvaluation eval = new CustomEvaluation(getEvaluationSet(this.TRAIN_PATH));
//      eval.evaluateModel(ch.getClassifier(), getEvaluationSet(this.TEST_PATH));
//
//      StringBuilder sb = new StringBuilder();
//      sb.append("=== Classifier ===\n");
//      sb.append(ch.getClassifier().toString()).append(CharConstants.NEW_LINE);
//      sb.append("=== Dedicated test set ===\n");
//      sb.append(eval.toSummaryString("=== Summary of " + ch.getClassifierName() + "===\n", false));
//      sb.append(eval.toClassDetailsString("=== Detailed Accuracy By Class ===\n"));
//      sb.append(eval.toMatrixString("=== Confusion Matrix ===\n"));
//
//      System.out.println(sb);
//      Utils.writeStringFile(DirectoryConstants.FORMATTED_DIR+ch.getResultName(), sb.toString());
//
//      return eval;
//   }
 
   /**
    * Required parameters: either SystemParameters, or a path to the combinedPath.
    * Since it's an "either or" scenario, there are 2 build functions here.
    * Optional parameters: FeatureSelection, Database<br>
    */
   public static class Buidler{
      //Initialised to null objects
      private FeatureSelection fs = NoFeatureSelection.getInstance();
      private Database db = NoDatabase.getInstance();

      public SystemTrain.Buidler featureSelection(FeatureSelection fs) {
         this.fs = fs;
         return this;
      }

      public SystemTrain.Buidler database(Database db) {
         this.db = db;
         return this;
      }
      
      public SystemTrain build(SystemParameters sp) throws Exception{
         return new SystemTrain(this, sp);
      }
      
      /**
       * This has a lot of parameters since those parameters needed to know
       * What stuff to insert to the Database
       * @param combinedPath
       * @param systemType
       * @param categoricalType
       * @param noiseLevel
       * @param dataset
       * @param extractionTool
       * @return
       * @throws Exception 
       */
      public SystemTrain build(
            String combinedPath, 
            String systemType, String categoricalType, Float noiseLevel, 
            String dataset, String extractionTool)
            throws Exception{
         
         return new SystemTrain(
            this,
            combinedPath,
            systemType,
            categoricalType,
            noiseLevel,
            dataset,
            extractionTool
         );
      }
   }
}