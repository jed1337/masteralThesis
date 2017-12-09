package driver;

import classifier.ClassifierHolder;
import constants.AttributeTypeConstants;
import constants.CharConstants;
import constants.FileNameConstants;
import constants.PathConstants;
import driver.mode.Mode;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import preprocessFiles.PreprocessFile;
import preprocessFiles.preprocessEvaluationSet.EvaluationSet;
import preprocessFiles.preprocessEvaluationSet.SetupTestTrainValidation;
import utils.Utils;
import utils.UtilsARFF;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public final class SystemTrain {
	private final String combinedPath = PathConstants.FORMATTED_DIR + FileNameConstants.COMBINED;

	private final ArrayList<PreprocessFile> preprocessFiles;
	private final ArrayList<ClassifierHolder> classifierHolders = new ArrayList<>();
	private final ArrayList<EvaluationSet> evaluationSets = new ArrayList<>();

	public SystemTrain(Mode mode)
		throws IOException, Exception {

		this.preprocessFiles = setupPreprocessFiles(mode.getPreprocessFiles(), mode.getReplacement());

		this.classifierHolders.add(new ClassifierHolder(new J48(), "J48"));
		this.classifierHolders.add(new ClassifierHolder(new IBk(), "KNN"));
		this.classifierHolders.add(new ClassifierHolder(new NaiveBayes(), "NB "));
		this.classifierHolders.add(new ClassifierHolder(new RandomForest(), "RF "));
		this.classifierHolders.add(new ClassifierHolder(new SMO(), "SMO"));

      //      Split into test train validation
		this.evaluationSets.add(new EvaluationSet(PathConstants.FORMATTED_DIR + FileNameConstants.TRAIN       , 4));
		this.evaluationSets.add(new EvaluationSet(PathConstants.FORMATTED_DIR + FileNameConstants.TEST        , 1));
		this.evaluationSets.add(new EvaluationSet(PathConstants.FORMATTED_DIR + FileNameConstants.VALIDATION  , 1));
	}

	private ArrayList<PreprocessFile> setupPreprocessFiles(final ArrayList<PreprocessFile> preprocessFiles, String replacement)
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

   private Instances getTrainSet() throws NoSuchElementException{
      return getEvaluationSet(PathConstants.FORMATTED_DIR + FileNameConstants.TRAIN);
   }
   
   private Instances getTestSet() throws NoSuchElementException{
      return getEvaluationSet(PathConstants.FORMATTED_DIR + FileNameConstants.TEST);
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
         getTrainSet()
      );

      applyFeatureSelection(nfs.getSelectedAttributes());

//      this.trainSet =        nfs.applyFeatureSelection(this.trainSet);
//      this.testSet =         nfs.applyFeatureSelection(this.testSet);
//      this.validationSet =   nfs.applyFeatureSelection(this.validationSet);

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

//      FeatureSelection.applyFeatureSelection(this.trainSet, selectedAttributes);
//      FeatureSelection.applyFeatureSelection(this.trainSet, selectedAttributes);
//      FeatureSelection.applyFeatureSelection(this.validationSet, selectedAttributes);
	}

   public void writeTestTrainValidation() throws IOException {
//      Utils.writeStringFile(PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN, trainSet.toString());
//      Utils.writeStringFile(PathConstants.FORMATTED_DIR+FileNameConstants.TEST, testSet.toString());
//      Utils.writeStringFile(PathConstants.FORMATTED_DIR+FileNameConstants.VALIDATION, validationSet.toString());
      for (EvaluationSet evaluationSet : this.evaluationSets) {
         Utils.writeStringFile(evaluationSet.getName(), evaluationSet.getInstances().toString());
      }
   }

//TODO turn into single responsibiitys
   public void testClassifier() throws Exception{
      final String accuracyPath = PathConstants.FORMATTED_DIR+"Accuracy.txt";
      Utils.writeStringFile(
              accuracyPath,
              ""
      );
      for (ClassifierHolder ch : classifierHolders) {
         ch.getClassifier().buildClassifier(getTrainSet());
         UtilsClssifiers.writeModel(PathConstants.FORMATTED_DIR, ch);

         Evaluation eval = new Evaluation(getTrainSet());
         eval.evaluateModel(ch.getClassifier(), getTestSet());

         StringBuilder sb = new StringBuilder();
         sb.append("=== ").append("Dedicated test set").append(" ===\n");
         sb.append(eval.toSummaryString("=== Summary of " + ch.getClassifierName() + "===\n", false));
         sb.append(eval.toClassDetailsString("=== Detailed Accuracy By Class ===\n"));
         sb.append(eval.toMatrixString("=== Confusion Matrix ===\n"));

         System.out.println(sb);
         Utils.writeStringFile(PathConstants.FORMATTED_DIR+ch.getResultName(), sb.toString());

         addToAccuracy(accuracyPath, eval, ch);
      }
   }

   private void addToAccuracy(String fileName, Evaluation eval, ClassifierHolder ch) throws IOException, Exception {
      StringBuilder text = new StringBuilder();
      text.append(ch.getClassifierName());
      text.append(" = ");
      text.append(Utils.doubleToString(eval.pctCorrect(), 12, 4));
      text.append(CharConstants.NEW_LINE);

      Utils.writeStringFile(
         fileName,
         text.toString(),
         true
      );
   }
}