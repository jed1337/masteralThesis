package driver;

import classifier.ClassifierHolder;
import constants.AttributeTypeConstants;
import constants.DirectoryConstants;
import constants.FileNameConstants;
import customWeka.CustomEvaluation;
import database.Database;
import database.NoDatabase;
import evaluation.Evaluation;
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

      final List<PreprocessFile> pfL = setupPreprocessFiles(sp.getPreprocessFiles(), sp.getRelabel());
      writePreprocessFile(pfL);
      
      this.db.insertMainTable(sp);

      String combinedPath = DirectoryConstants.FORMATTED_DIR + FileNameConstants.COMBINED;
      createCombinedData(
         pfL,
         combinedPath
      );

      ArrayList<ClassifierHolder> classifierHolders = getClassifierHolders();

      Evaluation classify = builder.eval;
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

   private ArrayList<ClassifierHolder> getClassifierHolders() throws Exception {
      ArrayList<ClassifierHolder> classifierHolders = new ArrayList<>();
      classifierHolders.add(new ClassifierHolder(new J48(), "J48"));
      classifierHolders.add(new ClassifierHolder(new IBk(), "KNN"));
      classifierHolders.add(new ClassifierHolder(new NaiveBayes(), "NB "));
      
      //We just extracted rf so that we can set print to be true (We stopped this since we don't even check the RF's output)
      RandomForest rf = new RandomForest();
//      rf.setPrintClassifiers(true);
      classifierHolders.add(new ClassifierHolder(rf, "RF "));
      
      classifierHolders.add(new ClassifierHolder(new SMO(), "SMO"));
      return classifierHolders;
   }

   private List<PreprocessFile> setupPreprocessFiles(final List<PreprocessFile> pfL, String relabel)
			  throws IOException, Exception {
		for (PreprocessFile pf : pfL) {
			pf.setUp();
			pf.relabel(
				AttributeTypeConstants.ATTRIBUTE_CLASS,
				relabel
			);
		}
		return pfL;
	}

   private void writePreprocessFile(final List<PreprocessFile> pfL) throws IOException {
      for (PreprocessFile pf : pfL) {
         Utils.writePreprocessFile(pf);
      }
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
      private Evaluation eval = NoEvaluation.getInstance();

      public SystemTrain.Buidler featureSelection(FeatureSelection fs) {
         this.fs = fs;
         return this;
      }

      public SystemTrain.Buidler database(Database db) {
         this.db = db;
         return this;
      }

      public SystemTrain.Buidler evaluation(Evaluation eval) {
         this.eval = eval;
         return this;
      }

      public SystemTrain build(SystemParameters sp) throws Exception{
         return new SystemTrain(this, sp);
      }
   }
}