package database;

import classifier.ClassifierHolder;
import constants.DBConstants.DBConnectionConstants;
import constants.DBConstants.EvaluationTableConstants;
import constants.DBConstants.FeatureSelectionTableConstants;
import constants.DBConstants.FeatureTableConstants;
import constants.DBConstants.MainTableConstants;
import customWeka.CustomEvaluation;
import driver.SystemParameters;
import featureSelection.FeatureSelection;
import globalParameters.GlobalFeatureExtraction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import utils.UtilsDB;
import weka.core.Instances;

public final class Mysql implements Database {
   private final Connection connection;
   private int mainID;

   public Mysql() throws SQLException, ClassNotFoundException {
      Class.forName(DBConnectionConstants.DRIVER_CLASS);
      System.out.println("MySQL JDBC Driver Registered!");

      this.connection = DriverManager.getConnection(DBConnectionConstants.CONNECTION_URL+DBConnectionConstants.DATABASE_NAME,
         DBConnectionConstants.USERNAME,
         DBConnectionConstants.PASSWORD
      );
   }

   @Override
   public void insertMainTable(SystemParameters sp) throws SQLException {
      String query =
         String.format("INSERT INTO %s.%s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?);",
            DBConnectionConstants.DATABASE_NAME,
            MainTableConstants.TABLE_NAME,

            MainTableConstants.SYSTEM_TYPE,
            MainTableConstants.CATEGORICAL_TYPE,
            MainTableConstants.NOISE_LEVEL,
            MainTableConstants.DATASET,
            MainTableConstants.EXTRACTION_TOOL
      );

      PreparedStatement ps = this.connection.prepareStatement(
         query,
         Statement.RETURN_GENERATED_KEYS
      );

      int i=1;
      ps.setString(i++, sp.getSystemType());
      ps.setString(i++, sp.getCategoricalType().name());
      ps.setFloat(i++, sp.getNoiseLevelFloat());
      ps.setString(i++, GlobalFeatureExtraction.getInstance().getDatasetName());
      ps.setString(i++, GlobalFeatureExtraction.getInstance().getName());

      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      rs.next();
      this.mainID = rs.getInt(1);
      System.out.println("Generated primary key: "+this.mainID);
   }

   @Override
   public void insertToFeatureSelectionTable(FeatureSelection fs) throws SQLException {
      String query =
        String.format("INSERT INTO %s.%s (%s, %s) VALUES (?,?);",
          DBConnectionConstants.DATABASE_NAME,
          FeatureSelectionTableConstants.TABLE_NAME,

          FeatureSelectionTableConstants.MAIN_ID,
          FeatureSelectionTableConstants.METHOD
      );

      PreparedStatement ps = this.connection.prepareStatement(query);

      int i=1;
      ps.setInt   (i++, this.mainID);
      ps.setString(i++, fs.getFSMethodName());

      ps.executeUpdate();
   }

   @Override
   public void insertToFeatureTable(Instances instances) throws NoSuchElementException, SQLException {
      String query =
        String.format("INSERT INTO %s.%s (%s, %s) VALUES (?,?);",
          DBConnectionConstants.DATABASE_NAME,
          FeatureTableConstants.TABLE_NAME,

          FeatureTableConstants.MAIN_ID,
          FeatureTableConstants.NAME
      );

      PreparedStatement ps = this.connection.prepareStatement(query);

      for(int i=0; i< instances.numAttributes(); i++){
         int psIndex=1;
         ps.setInt   (psIndex++, this.mainID);
         ps.setString(psIndex++, instances.attribute(i).name());

         ps.addBatch();

         int[] updateCounts = ps.executeBatch();
         UtilsDB.checkUpdateCounts(updateCounts);
      }
   }

   @Override
   public void insertToEvaluationTable(ClassifierHolder ch, CustomEvaluation eval)
           throws SQLException, Exception{
      String query =
        String.format("INSERT INTO %s.%s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?,?,?,?);",
          DBConnectionConstants.DATABASE_NAME,
          EvaluationTableConstants.TABLE_NAME,

          EvaluationTableConstants.MAIN_ID,
          EvaluationTableConstants.CLASSIFIER,
          EvaluationTableConstants.CLASS,
          EvaluationTableConstants.ACCURACY,
          EvaluationTableConstants.PREC,
          EvaluationTableConstants.RECALL,
          EvaluationTableConstants.FSCORE,
          EvaluationTableConstants.KAPPA,
          EvaluationTableConstants.CONFUSION_MATRIX
      );

      //Average of the results
      //This is on the separate area since some columns
      //like Kappa and the confusion matrix only exist with the average
      PreparedStatement ps = this.connection.prepareStatement(query);
      {
         int avgClassIndex = 1;
         ps.setInt(avgClassIndex++, this.mainID);
         ps.setString(avgClassIndex++, ch.getClassifierName());
         ps.setString(avgClassIndex++, "Average");

         ps.setDouble(avgClassIndex++, eval.correct()/eval.withClass());
         ps.setDouble(avgClassIndex++, eval.weightedPrecision());
         ps.setDouble(avgClassIndex++, eval.weightedRecall());
         ps.setDouble(avgClassIndex++, eval.weightedFMeasure());
         ps.setDouble(avgClassIndex++, eval.kappa());
         ps.setString(avgClassIndex++, eval.toMatrixString(""));

         ps.addBatch();
      }

      //Per class evaluation
      {
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
            ps.setNull  (psIndex++, java.sql.Types.VARCHAR);

            ps.addBatch();
         }
      }

      int[] updateCounts = ps.executeBatch();
      UtilsDB.checkUpdateCounts(updateCounts);
   }
}