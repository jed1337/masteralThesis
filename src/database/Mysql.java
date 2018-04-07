package database;

import constants.CharConstants;
import constants.DBConstants.DBConnectionConstants;
import constants.DBConstants.EvaluationTableConstants;
import constants.DBConstants.FeatureSelectionTableConstants;
import constants.DBConstants.FeatureTableConstants;
import constants.DBConstants.MainTableConstants;
import constants.NoiseDatasetNames;
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
import java.util.Arrays;
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

      this.connection = DriverManager.getConnection(
         DBConnectionConstants.CONNECTION_URL+DBConnectionConstants.DATABASE_NAME,
         DBConnectionConstants.USERNAME,
         DBConnectionConstants.PASSWORD
      );
   }

   @Override
   public void insertMainTable(SystemParameters sp) throws SQLException {
      insertMainTable(
         sp.getSystemType(),
         sp.getCategoricalType().name(),
         
         sp.getNoiseDatasetName(), 
         sp.getNoiseToAttackcRatio(),
         
         GlobalFeatureExtraction.getInstance().getDatasetName(),
         GlobalFeatureExtraction.getInstance().getName()
      );
   }
   
   @Override
   public void insertMainTable(
      String systemType, String categoricalType, NoiseDatasetNames noiseDataset, float noiseToAttackRatio, String dataset, String extractionTool)
      
      throws SQLException{

      String query = getQueryString(DBConnectionConstants.DATABASE_NAME,
         MainTableConstants.TABLE_NAME,

         MainTableConstants.SYSTEM_TYPE,
         MainTableConstants.CATEGORICAL_TYPE,
         MainTableConstants.NOISE_DATASET,
         MainTableConstants.NORMAL_TO_NOISE_RATIO,
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
      
      ps.setString(i++, noiseDataset.toString());
      ps.setFloat(i++, noiseToAttackRatio);
      
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
      String query = getQueryString(
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
      String query = getQueryString(
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
   }

   @Override
   public void insertToEvaluationTable(String classifyType, String classifierName, CustomEvaluation eval)
           throws SQLException, Exception{
      String query =getQueryString(
         DBConnectionConstants.DATABASE_NAME,
         EvaluationTableConstants.TABLE_NAME,

         EvaluationTableConstants.MAIN_ID,
         EvaluationTableConstants.TYPE,
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
         ps.setString(avgClassIndex++, classifyType);
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
            ps.setString(psIndex++, classifyType);
            ps.setString(psIndex++, classifierName);
            ps.setString(psIndex++, classNames[i]);

            //Used to manually calculate the accuracy per class 
            //since True positive rate == recall (They point to the same function)
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
   
   /**
    * This function was created in order to better dynamically create the 
    * insert statement in the database
    * <p>
    * Returns something like
    * <br>
    * INSERT INTO db.table (col1, col2, col3, col4, col5, col6) VALUES (?,?,?,?,?,?);
    * @param columns
    * @return 
    */
   private String getQueryString(String database, String table, String... columns){
      return String.format("INSERT INTO %s.%s (%s) VALUES (%s)",
         database,
         table,
         getConcatenatedColumns(columns),
         getConcatenatedQuestionMarks(columns.length)
      );
   }
   
   /**
    * Used in {@link database.Mysql#getQueryString(String, String, String...) getQueryString}
    * <p>
    * Input:
    * {@code
    *    getConcatenatedColumns("col1", "col2", "col3")
    * }
    * <br>
    * Output: "col1,col2,col3"
    * @param columns
    * @return 
    */
   private String getConcatenatedColumns(String... columns){
      return String.join(CharConstants.COMMA, columns);
   }
   
   /**
    * Used in {@link database.Mysql#getQueryString(String, String, String...) getQueryString}
    * <p>
    * Input:
    * {@code
    *    getConcatenatedQuestionMarks(3)
    * }
    * <br>
    * Output: "?,?,?"
    * @param qCount the number of question marks to produce
    * @see <a href="https://stackoverflow.com/a/2804866">Stack Overflow</a>
    * @return 
    */
   private String getConcatenatedQuestionMarks(int qCount){
      String[] questionMarks = new String[qCount];
      Arrays.fill(questionMarks, "?");
      return String.join(CharConstants.COMMA, questionMarks);
   }
}