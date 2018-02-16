package driver;

import classifier.ClassifierHolder;
import constants.AttributeTypeConstants;
import constants.DirectoryConstants;
import constants.FileNameConstants;
import customWeka.CustomEvaluation;
import database.Database;
import database.NoDatabase;
import evaluation.Classify;
import evaluation.NoEvaluation;
import featureSelection.FeatureSelection;
import featureSelection.NoFeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import preprocessFiles.PreprocessFile;
import utils.Utils;
import utils.UtilsARFF;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;

/**
 * A class to train the system given a wide variety of different parameters.
 */
public final class SystemTrain {
   private final Database db;

	private SystemTrain(SystemTrain.Buidler builder, SystemParameters sp) throws IOException, Exception {
      this.db = builder.db;

      List<PreprocessFile> preprocessFiles = setupPreprocessFiles(sp.getPreprocessFiles(), sp.getRelabel());
      this.db.insertMainTable(sp);

      String combinedPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.COMBINED;
      createCombinedData(
         preprocessFiles,
         combinedPath
      );

      execute(builder, combinedPath);
	}

	private SystemTrain(
      SystemTrain.Buidler builder, String combinedPath,
      String systemType, String categoricalType, Float noiseLevel,
      String dataset, String extractionTool)
      throws IOException, Exception {

      this.db = builder.db;
      this.db.insertMainTable(systemType, categoricalType, noiseLevel, dataset, extractionTool);

      execute(builder, combinedPath);
	}

   private void execute(SystemTrain.Buidler builder, String combinedPath) throws Exception {
      ArrayList<ClassifierHolder> classifierHolders = new ArrayList<>();
      classifierHolders.add(new ClassifierHolder(new J48(), "J48"));
      classifierHolders.add(new ClassifierHolder(new IBk(), "KNN"));
      classifierHolders.add(new ClassifierHolder(new NaiveBayes(), "NB "));
      classifierHolders.add(new ClassifierHolder(new RandomForest(), "RF "));
      classifierHolders.add(new ClassifierHolder(new SMO(), "SMO"));

      Classify classify = builder.eval;
      classify.setupEvaluationSets(combinedPath);
      
      Enumeration<Attribute> selectedAttributes =
         classify.applyFeatureSelection(builder.fs);
      
      this.db.insertToFeatureSelectionTable(builder.fs);
      this.db.insertToFeatureTable(selectedAttributes);
      
      HashMap<String, CustomEvaluation> hmEval =
         classify.evaluateClassifiers(classifierHolders);
      
      for (Map.Entry<String, CustomEvaluation> entry : hmEval.entrySet()) {
         String classifierName  = entry.getKey();
         CustomEvaluation cEval = entry.getValue();
         
         this.db.insertToEvaluationTable(classify.getType(), classifierName, cEval);
      }
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

   /**
    * Required parameters: either SystemParameters, or a path to the combinedPath.
    * Since it's an "either or" scenario, there are 2 build functions here.
    * <br>
    * Optional parameters: FeatureSelection, Database
    */
   public static class Buidler{
      //Initialised to null objects
      private FeatureSelection fs = NoFeatureSelection.getInstance();
      private Database db = NoDatabase.getInstance();
      private Classify eval = NoEvaluation.getInstance();

      public SystemTrain.Buidler featureSelection(FeatureSelection fs) {
         this.fs = fs;
         return this;
      }

      public SystemTrain.Buidler database(Database db) {
         this.db = db;
         return this;
      }

      public SystemTrain.Buidler evaluation(Classify eval) {
         this.eval = eval;
         return this;
      }

      public SystemTrain build(SystemParameters sp) throws Exception{
         return new SystemTrain(this, sp);
      }

      /**
       * This has a lot of parameters since those parameters needed to know
       * What stuff to insert to the Database
       * @param combinedPath The path to the arff file containing all the instances
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