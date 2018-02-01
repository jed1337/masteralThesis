package database;

import classifier.ClassifierHolder;
import customWeka.CustomEvaluation;
import driver.SystemParameters;
import featureSelection.FeatureSelection;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import weka.core.Instances;

public interface Database {
   /**
    * Calls database.Database#insertMainTable(String, String, Float, String, String)
    * @see database.Database#insertMainTable(String, String, Float, String, String)
    * @param sp
    * @throws SQLException 
    */
   public void insertMainTable(SystemParameters sp) throws SQLException;
   
   /**
    * Inserts into the main table the following parameters.<br>
    * This function is used if categorical path was directly passed 
    * to SystemTrain instead of using multiple PreprocessFile
    * @param systemType
    * @param categoricalType
    * @param noiseLevel
    * @param dataset
    * @param extractionTool
    * @throws SQLException 
    */
   public void insertMainTable(
      String systemType, String categoricalType, Float noiseLevel, 
      String dataset, String extractionTool) throws SQLException;

   public void insertToEvaluationTable(ClassifierHolder ch, CustomEvaluation eval)
           throws SQLException, Exception;

   public void insertToFeatureSelectionTable(FeatureSelection fs) throws SQLException;

   /**
    * @param instances This is passed to know which attributes to look for
    * @throws NoSuchElementException
    * @throws SQLException
    */
   public void insertToFeatureTable(Instances instances) throws NoSuchElementException,
                                                         SQLException;
}
