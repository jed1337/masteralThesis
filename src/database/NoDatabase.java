package database;

import constants.NoiseDatasetNames;
import customWeka.CustomEvaluation;
import driver.SystemParameters;
import featureSelection.FeatureSelection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import weka.core.Attribute;

/**
 * Null object implementation of Database.<br>
 * It's implemented as a singleton since it's ok for many objects to have
 * reference to this since this class does nothing
 */
public class NoDatabase implements Database{
   private static NoDatabase instance;
   private NoDatabase(){}

   public static NoDatabase getInstance(){
      if(NoDatabase.instance == null){
         NoDatabase.instance = new NoDatabase();
      }
      System.err.println("Warning, getting NoDatabase");
      return NoDatabase.instance;
   }

   @Override
   public void insertMainTable(
         String systemType, String categoricalType, 
         NoiseDatasetNames noiseDataset, float noiseToAttackRatio, 
         String dataset, String extractionTool, 
         int randomSeed)
      
      throws SQLException {
      System.err.println("Not doing inserting to insertMainTable");
   }

   @Override
   public void insertMainTable(SystemParameters sp, int randomSeed) throws SQLException {
      System.err.println("Not doing inserting to insertMainTable");
   }

   @Override
   public void insertToEvaluationTable(String classifyType, String classifierName, CustomEvaluation eval)
           throws SQLException, Exception {
      System.err.println("Not doing inserting to insertToEvaluationTable");
   }

   @Override
   public void insertToFeatureSelectionTable(FeatureSelection fs) throws SQLException {
      System.err.println("Not doing inserting to insertToFeatureSelectionTable");
   }

   @Override
   public void insertToFeatureTable(Enumeration<Attribute> attributes) throws NoSuchElementException, SQLException {
      System.err.println("Not doing inserting to insertToFeatureTable");
   }
}