package database;

import customWeka.CustomEvaluation;
import driver.SystemParameters;
import featureSelection.FeatureSelection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import weka.core.Attribute;

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

   public void insertToEvaluationTable(String classifyType, String classifierName, CustomEvaluation eval)
           throws SQLException, Exception;

   /**
    * Only inserts the MainID and the FeatureSelection name
    * @param fs
    * @throws SQLException 
    */
   public void insertToFeatureSelectionTable(FeatureSelection fs) throws SQLException;

   /**
    * Inserts the features found in the instances to the DB
    * @param attributes This is passed to know the names of the attributes
    * @throws NoSuchElementException
    * @throws SQLException
    */
   public void insertToFeatureTable(Enumeration<Attribute> attributes) throws NoSuchElementException,
                                                         SQLException;
}
