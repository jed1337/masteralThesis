package driver;

import classifier.ClassifierHolder;
import constants.AttributeTypeConstants;
import constants.CharConstants;
import constants.DAOConstants;
import constants.FileNameConstants;
import constants.DirectoryConstants;
import customWeka.CustomEvaluation;
import driver.mode.Mode;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import preprocessFiles.PreprocessFile;
import preprocessFiles.preprocessAs.FormatAsText;
import preprocessFiles.preprocessEvaluationSet.EvaluationSet;
import preprocessFiles.preprocessEvaluationSet.SetupTestTrainValidation;
import utils.Utils;
import utils.UtilsARFF;
import utils.UtilsClssifiers;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public final class SystemTrain {
	private final String combinedPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.COMBINED;
   private final String trainPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.TRAIN;
   private final String testPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.TEST;
   private final String validationPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.VALIDATION;
   
	private final ArrayList<ClassifierHolder> classifierHolders;
	private final ArrayList<EvaluationSet> evaluationSets;
	private final List<PreprocessFile> preprocessFiles;
   
   private final Connection connection;

	public SystemTrain(Mode mode) throws IOException, Exception {
      this.classifierHolders = new ArrayList<>();
      this.evaluationSets = new ArrayList<>();
		this.preprocessFiles = setupPreprocessFiles(mode.getPreprocessFiles(), mode.getReplacement());

		this.classifierHolders.add(new ClassifierHolder(new J48(), "J48"));
		this.classifierHolders.add(new ClassifierHolder(new IBk(), "KNN"));
		this.classifierHolders.add(new ClassifierHolder(new NaiveBayes(), "NB "));
		this.classifierHolders.add(new ClassifierHolder(new RandomForest(), "RF "));
		this.classifierHolders.add(new ClassifierHolder(new SMO(), "SMO"));

		this.evaluationSets.add(new EvaluationSet(this.trainPath       , 4));
		this.evaluationSets.add(new EvaluationSet(this.testPath        , 1));
		this.evaluationSets.add(new EvaluationSet(this.validationPath  , 1));
      
      this.connection = setupConnection();
      System.out.println("");
	}
   
   private Connection setupConnection() throws SQLException, ClassNotFoundException{
      Class.forName(DAOConstants.DRIVER_CLASS);
      System.out.println("MySQL JDBC Driver Registered!");
      
      Connection con = DriverManager.getConnection(
         DAOConstants.CONNECTION_URL+DAOConstants.DATABASE_NAME,
         DAOConstants.USERNAME,
         DAOConstants.PASSWORD
      );
      return con;
   }

	private List<PreprocessFile> setupPreprocessFiles(final List<PreprocessFile> preprocessFiles, String replacement)
			  throws IOException, Exception {
		for (PreprocessFile pf : preprocessFiles) {
			pf.setUp();
			pf.rename(
				AttributeTypeConstants.ATTRIBUTE_CLASS,
				replacement
			);
			Utils.writePreprocessFile(pf);
		}
		return preprocessFiles;
	}

	public void setupTestTrainValidation() throws IOException, Exception{
//      Combine data
		UtilsARFF.createArff(
			this.combinedPath,
			this.preprocessFiles.stream()
				.map(tl->tl.getFaa().getSavePath())
				.collect(Collectors.toList()),
			AttributeTypeConstants.ATTRIBUTE_CLASS
		);

		SetupTestTrainValidation sttv = new SetupTestTrainValidation(this.combinedPath);
		sttv.setTrainTestValidationPaths(this.evaluationSets);

      writeTestTrainValidation();
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

	/**
	 * Breaks single responsibility (Apply and return int[])
	 * @param attributeEvaluator
	 * @param searchMethod
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
   public int[] applyFeatureSelection(ASEvaluation attributeEvaluator, ASSearch searchMethod)
           throws IOException, NoSuchElementException, Exception {
      FeatureSelection nfs = new FeatureSelection(
         attributeEvaluator,
         searchMethod,
         getEvaluationSet(this.trainPath)
      );

      applyFeatureSelection(nfs.getSelectedAttributes());

      return nfs.getSelectedAttributes();
   }

	public void applyFeatureSelection(int[] selectedAttributes) throws Exception{
      for (EvaluationSet evaluationSet : this.evaluationSets) {
         evaluationSet.setInstances(
            FeatureSelection.applyFeatureSelection(
               evaluationSet.getInstances(),
               selectedAttributes
            )
         );
      }

      writeTestTrainValidation();
	}

   public void writeTestTrainValidation() throws IOException {
      for (EvaluationSet evaluationSet : this.evaluationSets) {
         final String path = evaluationSet.getName();
         Utils.writeStringFile(path, evaluationSet.getInstances().toString());

         FormatAsText fat = new FormatAsText(path);
         fat.addClassCount(AttributeTypeConstants.ATTRIBUTE_CLASS);
      }
   }

   public ArrayList<CustomEvaluation> evaluateClassifiers() throws Exception{
      final ArrayList<CustomEvaluation> evaluations = new ArrayList<>();
      
      for (ClassifierHolder ch : this.classifierHolders) {
         CustomEvaluation eval = evaluateIndividualClassifier(ch);
         evaluations.add(eval);
      }
      return evaluations;
   }
   
   /**
    * Collates the evaluations getting the data specified in customFunction
    * @param customFunction 
    * @param path The path to place the collated files at
    * @param testName The name used for the current test (Appended inside the file and not used as a file name)
    * @return
    * @throws Exception 
    */
   public ArrayList<CustomEvaluation> customEvaluateClassifiers(
           BiFunction<CustomEvaluation, ClassifierHolder, String> customFunction, String path, String testName)
           throws Exception{
      final ArrayList<CustomEvaluation> evaluations = new ArrayList<>();
      
      //Append
      Utils.writeStringFile(path, 
         CharConstants.NEW_LINE+ testName +CharConstants.NEW_LINE, 
         true
      );
      
      for (ClassifierHolder ch : this.classifierHolders) {
         CustomEvaluation eval = evaluateIndividualClassifier(ch);
         evaluations.add(eval);

         //Append
         Utils.writeStringFile(
            path,
            customFunction.apply(eval, ch),
            true
         ); 
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