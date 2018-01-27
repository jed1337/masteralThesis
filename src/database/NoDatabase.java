package database;

import classifier.ClassifierHolder;
import customWeka.CustomEvaluation;
import driver.SystemParameters;
import featureSelection.FeatureSelection;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import weka.core.Instances;

public class NoDatabase implements DBInterface{
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
   public void insertMainTable(SystemParameters sp) throws SQLException {
        System.err.println("Not doing inserting to insertMainTable");
   }

   @Override
   public void insertToEvaluationTable(ClassifierHolder ch, CustomEvaluation eval)
           throws SQLException, Exception {
        System.err.println("Not doing inserting to insertToEvaluationTable");
   }

   @Override
   public void insertToFeatureSelectionTable(FeatureSelection fs) throws
                                                                         SQLException {
        System.err.println("Not doing inserting to insertToFeatureSelectionTable");
   }

   @Override
   public void insertToFeatureTable(Instances instances) throws
                                                                NoSuchElementException,
                                                                SQLException {
        System.err.println("Not doing inserting to insertToFeatureTable");
   }

}