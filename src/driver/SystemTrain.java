package driver;

import classifier.ClassifierHolder;
import constants.AttributeTypeConstants;
import constants.CharConstants;
import constants.DBConstants.DBConnectionConstants;
import constants.DBConstants.EvaluationTableConstants;
import constants.DBConstants.FeatureSelectionTableConstants;
import constants.DBConstants.FeatureTableConstants;
import constants.DBConstants.MainTableConstants;
import constants.FileNameConstants;
import constants.DirectoryConstants;
import customWeka.CustomEvaluation;
import driver.mode.Mode;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import weka.attributeSelection.ASEvaluation;
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
   
   private final int mainID;
   private final Mode mode;

	public SystemTrain(Mode mode) throws IOException, Exception {
      this.classifierHolders = new ArrayList<>();
      this.evaluationSets = new ArrayList<>();
      this.mode = mode;
      
      this.preprocessFiles = setupPreprocessFiles(mode.getPreprocessFiles(), mode.getRelabel());

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

      //Insert into main table
      {
         String query = String.format("INSERT INTO %s.%s (%s, %s, %s) VALUES (?,?,?);",
           DBConnectionConstants.DATABASE_NAME,
           MainTableConstants.TABLE_NAME,
           
           MainTableConstants.SYSTEM_TYPE,
           MainTableConstants.CATEGORICAL_TYPE,
           MainTableConstants.NOISE_LEVEL
         );

         PreparedStatement ps = this.connection.prepareStatement(
            query,
            Statement.RETURN_GENERATED_KEYS
         );
         
         int i=1;
         ps.setString(i++, mode.getSystemType());
         ps.setString(i++, mode.getCategoricalType().name());
         ps.setFloat(i++, mode.getNoiseLevelFloat());

         ps.executeUpdate();
         
         ResultSet rs = ps.getGeneratedKeys();
         rs.next();
         this.mainID = rs.getInt(1);
         System.out.println("Generated primary key: "+this.mainID);
      }
      System.out.println("");
	}

   private Connection setupConnection() throws SQLException, ClassNotFoundException{
      Class.forName(DBConnectionConstants.DRIVER_CLASS);
      System.out.println("MySQL JDBC Driver Registered!");

      Connection con = DriverManager.getConnection(DBConnectionConstants.CONNECTION_URL+DBConnectionConstants.DATABASE_NAME,
         DBConnectionConstants.USERNAME,
         DBConnectionConstants.PASSWORD
      );
      return con;
   }

	private List<PreprocessFile> setupPreprocessFiles(final List<PreprocessFile.PreprocessFileBuilder> pfBuilder, String relabel)
			  throws IOException, Exception {
		for (PreprocessFile.PreprocessFileBuilder pfb : pfBuilder) {
//			pf.setUp();
         pfb = pfb.relabel(relabel);
//			pfb.relabel(
//				AttributeTypeConstants.ATTRIBUTE_CLASS,
//				relabel
//			);
//			Utils.writePreprocessFile(pfb);
		}
      
      ArrayList<PreprocessFile> pfAL = new ArrayList<>();
      for (PreprocessFile.PreprocessFileBuilder pfB : pfBuilder) {
         pfAL.add(pfB.build(fExtractionPath));
      }
		return pfAL;
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
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
   public int[] applyFeatureSelection(ASEvaluation attributeEvaluator)
           throws IOException, NoSuchElementException, Exception {
      FeatureSelection nfs = new FeatureSelection(
         attributeEvaluator,
         getEvaluationSet(this.trainPath)
      );

      applyFeatureSelection(nfs.getSelectedAttributes());

      //Insert Feature_selection table
      {
         String query = String.format("INSERT INTO %s.%s (%s, %s, %s) VALUES (?,?,?);",
           DBConnectionConstants.DATABASE_NAME,
           FeatureSelectionTableConstants.TABLE_NAME,

           FeatureSelectionTableConstants.MAIN_ID,
           FeatureSelectionTableConstants.METHOD,
           FeatureSelectionTableConstants.ORIGIN
         );

         PreparedStatement ps = this.connection.prepareStatement(query);
         
         int i=1;
         ps.setInt   (i++, this.mainID);
         ps.setString(i++, attributeEvaluator.toString());
         ps.setString(i++, this.mode.getSystemType());

         ps.executeUpdate();
      }

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

   /**
    * Writes the test, train, and validation files to a folder <p>
    * also adds the class count
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
   
   public static void checkUpdateCounts(int[] updateCounts) {
      for (int i = 0; i < updateCounts.length; i++) {
         if (updateCounts[i] >= 0) {
            System.out.println("OK; updateCount=" + updateCounts[i]);
         } else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
            System.out.println("OK; updateCount=Statement.SUCCESS_NO_INFO");
         } else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
            System.out.println("Failure; updateCount=Statement.EXECUTE_FAILED");
         }
      }
   }

   public ArrayList<CustomEvaluation> evaluateClassifiers() throws Exception{
      final ArrayList<CustomEvaluation> evaluations = new ArrayList<>();
      
      //Insert Feature table. 
      {
         String query = String.format("INSERT INTO %s.%s (%s, %s) VALUES (?,?);",
           DBConnectionConstants.DATABASE_NAME,
           FeatureTableConstants.TABLE_NAME,

           FeatureTableConstants.MAIN_ID,
           FeatureTableConstants.NAME
         );

         PreparedStatement ps = this.connection.prepareStatement(query);
         
         Instances trainSet = getEvaluationSet(this.trainPath);
                  
         for(int i=0; i< trainSet.numAttributes(); i++){
            int psIndex=1;
            ps.setInt   (psIndex++, this.mainID);
            ps.setString(psIndex++, trainSet.attribute(i).name());
            
            ps.addBatch();
         
         int[] updateCounts = ps.executeBatch();
         checkUpdateCounts(updateCounts);
         }
      }

      for (ClassifierHolder ch : this.classifierHolders) {
         CustomEvaluation eval = evaluateIndividualClassifier(ch);
         evaluations.add(eval);
         
          //Insert to evaluation table
         {
            String query = String.format("INSERT INTO %s.%s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?,?);",
               DBConnectionConstants.DATABASE_NAME,
               EvaluationTableConstants.TABLE_NAME,
               
               EvaluationTableConstants.MAIN_ID,
               EvaluationTableConstants.CLASSIFIER,
               EvaluationTableConstants.CLASS,
               EvaluationTableConstants.ACCURACY,
               EvaluationTableConstants.PREC,
               EvaluationTableConstants.RECALL,
               EvaluationTableConstants.FSCORE,
               EvaluationTableConstants.KAPPA
            );

            PreparedStatement ps = this.connection.prepareStatement(query);
            int avgClassIndex = 1;
            ps.setInt(avgClassIndex++, this.mainID);
            ps.setString(avgClassIndex++, ch.getClassifierName());
            ps.setString(avgClassIndex++, "Average");

//            ps.setDouble(avgClassIndex++, eval.pctCorrect());
            ps.setDouble(avgClassIndex++, eval.correct()/eval.withClass());
            ps.setDouble(avgClassIndex++, eval.weightedPrecision());
            ps.setDouble(avgClassIndex++, eval.weightedRecall());
            ps.setDouble(avgClassIndex++, eval.weightedFMeasure());
            ps.setDouble(avgClassIndex++, eval.kappa());

            ps.addBatch();
            
            final String[] classNames = eval.getClassNames();
            for (int i = 0; i < classNames.length; i++) {
               int psIndex=1;
               ps.setInt   (psIndex++, this.mainID);
               ps.setString(psIndex++, ch.getClassifierName());
               ps.setString(psIndex++, classNames[i]);
               
               double tp = eval.numTruePositives(i);
               double fp = eval.numFalsePositives(i);
               double tn = eval.numTrueNegatives(i);
               double fn = eval.numFalseNegatives(i);
               double total = tp+fp+tn+fn;
               if(total== 0){
                  ps.setDouble(psIndex++, 0);
               }else{
                  ps.setDouble(psIndex++, (tp+tn)/total);
               }
               ps.setDouble(psIndex++, eval.precision(i));
               ps.setDouble(psIndex++, eval.recall(i));
               ps.setDouble(psIndex++, eval.fMeasure(i));
               ps.setNull  (psIndex++, java.sql.Types.FLOAT);

               ps.addBatch();
            }
            
            int[] updateCounts = ps.executeBatch();
            checkUpdateCounts(updateCounts);
         }
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