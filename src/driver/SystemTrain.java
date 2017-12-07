package driver;

import classifier.ClassifierHolder;
import constants.AttributeTypeConstants;
import constants.CharConstants;
import constants.FileNameConstants;
import constants.PathConstants;
import driver.mode.Mode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import featureSelection.FeatureSelection;
import preprocessFiles.PreprocessFile;
import preprocessFiles.SetupTestTrainValidation;
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
   
   private Instances trainSet;
   private Instances testSet;
   private Instances validationSet;

   public SystemTrain(Mode mode)
      throws IOException, Exception {
   
      this.preprocessFiles = setupPreprocessFiles(mode.getPreprocessFiles(), mode.getReplacement());
      
      this.classifierHolders.add(new ClassifierHolder(new J48(), "J48"));
      this.classifierHolders.add(new ClassifierHolder(new IBk(), "KNN"));
      this.classifierHolders.add(new ClassifierHolder(new NaiveBayes(), "NB "));
      this.classifierHolders.add(new ClassifierHolder(new RandomForest(), "RF "));
      this.classifierHolders.add(new ClassifierHolder(new SMO(), "SMO"));

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

//      Split into test train validation
      SetupTestTrainValidation sttv = new SetupTestTrainValidation(this.combinedPath);
      sttv.setTrainTestValidationPaths(
         PathConstants.FORMATTED_DIR + FileNameConstants.TRAIN,
         PathConstants.FORMATTED_DIR + FileNameConstants.TEST,
         PathConstants.FORMATTED_DIR + FileNameConstants.VALIDATION
      );
      
//Setup train test validation
      trainSet = UtilsInstances.getInstances(PathConstants.FORMATTED_DIR + FileNameConstants.TRAIN);
      testSet = UtilsInstances.getInstances(PathConstants.FORMATTED_DIR + FileNameConstants.TEST);
      validationSet = UtilsInstances.getInstances(PathConstants.FORMATTED_DIR + FileNameConstants.VALIDATION);
   }
   
   public void applyFeatureSelection(int[] selectedAttributes) throws Exception{
      FeatureSelection.applyFeatureSelection(trainSet, selectedAttributes);
      FeatureSelection.applyFeatureSelection(trainSet, selectedAttributes);
      FeatureSelection.applyFeatureSelection(validationSet, selectedAttributes);
   }
   
   /**
    * Breaks single responsibility (Apply and return int[])
    * @param attributeEvaluator
    * @param searchMethod
    * @return
    * @throws IOException
    * @throws Exception 
    */
   public int[] applyFeatureSelection(ASEvaluation attributeEvaluator, ASSearch searchMethod) throws IOException, Exception {
      FeatureSelection nfs = new FeatureSelection(
         attributeEvaluator,
         searchMethod,
         this.trainSet
      );
      
      this.trainSet =        nfs.applyFeatureSelection(this.trainSet);
      this.testSet =         nfs.applyFeatureSelection(this.testSet);
      this.validationSet =   nfs.applyFeatureSelection(this.validationSet);

      return nfs.selectedAttributes;
   }

   public void writeTestTrainValidation() throws IOException {
      Utils.writeStringFile(PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN, trainSet.toString());
      Utils.writeStringFile(PathConstants.FORMATTED_DIR+FileNameConstants.TEST, testSet.toString());
      Utils.writeStringFile(PathConstants.FORMATTED_DIR+FileNameConstants.VALIDATION, validationSet.toString());
   }

//TODO turn into single responsibiitys   
   public void testClassifier() throws Exception{
//      Test classifier

      final String accuracyPath = PathConstants.FORMATTED_DIR+"Accuracy.txt";
      Utils.writeStringFile(
              accuracyPath,
              ""      );
      for (ClassifierHolder ch : classifierHolders) {
         ch.getClassifier().buildClassifier(trainSet);
         UtilsClssifiers.writeModel(PathConstants.FORMATTED_DIR, ch);

         Evaluation eval = new Evaluation(trainSet);
         eval.evaluateModel(ch.getClassifier(), testSet);

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