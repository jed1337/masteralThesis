package database;

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
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import utils.UtilsDB;
import weka.core.Attribute;

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
      insertMainTable(
         sp.getSystemType(),
         sp.getCategoricalType().name(),
         sp.getNoiseLevelFloat(),
         GlobalFeatureExtraction.getInstance().getDatasetName(),
         GlobalFeatureExtraction.getInstance().getName()
      );
   }
   
   @Override
   public void insertMainTable(
      String systemType, String categoricalType, Float noiseLevel, 
      String dataset, String extractionTool)
      throws SQLException{

      String query =
         String.format("INSERT INTO %s.%s (%s, %s, %s, %s, %s, %s) VALUES (?,?,?,?,?,?);",
            DBConnectionConstants.DATABASE_NAME,
            MainTableConstants.TABLE_NAME,

            MainTableConstants.SYSTEM_TYPE,
            MainTableConstants.CATEGORICAL_TYPE,
            MainTableConstants.NOISE_LEVEL,
            MainTableConstants.DATASET,
            MainTableConstants.EXTRACTION_TOOL,
            MainTableConstants.TIMESTAMP
      );

      PreparedStatement ps = this.connection.prepareStatement(
         query,
         Statement.RETURN_GENERATED_KEYS
      );
      
      //Taken from https://alvinalexander.com/java/java-timestamp-example-current-time-now
      Calendar calendar = Calendar.getInstance();
      Timestamp timestamp = new Timestamp(calendar.getTime().getTime());

      int i=1;
      ps.setString(i++, systemType);
      ps.setString(i++, categoricalType);
      ps.setFloat (i++, noiseLevel);
      ps.setString(i++, dataset);
      ps.setString(i++, extractionTool);
      ps.setTimestamp(i++, timestamp);

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
   public void insertToFeatureTable(Enumeration<Attribute> attributes) throws NoSuchElementException, SQLException {
      String query =
        String.format("INSERT INTO %s.%s (%s, %s) VALUES (?,?);",
          DBConnectionConstants.DATABASE_NAME,
          FeatureTableConstants.TABLE_NAME,

          FeatureTableConstants.MAIN_ID,
          FeatureTableConstants.NAME
      );

      PreparedStatement ps = this.connection.prepareStatement(query);
      
      while(attributes.hasMoreElements()){
         int psIndex=1; //Gets reinitialised to 1 every time the loop runs
         
         ps.setInt   (psIndex++, this.mainID);
         ps.setString(psIndex++, attributes.nextElement().name());

         ps.addBatch();

         int[] updateCounts = ps.executeBatch();
         UtilsDB.checkUpdateCounts(updateCounts);
      }

//      for(int i=0; i< attributes.numAttributes(); i++){
//         int psIndex=1;
//         ps.setInt   (psIndex++, this.mainID);
//         ps.setString(psIndex++, attributes.attribute(i).name());
//
//         ps.addBatch();
//
//         int[] updateCounts = ps.executeBatch();
//         UtilsDB.checkUpdateCounts(updateCounts);
//      }
   }

   @Override
   public void insertToEvaluationTable(String classifierName, CustomEvaluation eval)
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
//         ps.setString(avgClassIndex++, classifierName.getClassifierName());
         ps.setString(avgClassIndex++, classifierName);
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
//            ps.setString(psIndex++, classifierName.getClassifierName());
            ps.setString(psIndex++, classifierName);
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