package database;

import classifier.ClassifierHolder;
import customWeka.CustomEvaluation;
import driver.SystemParameters;
import featureSelection.FeatureSelection;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import weka.core.Instances;

public interface DBInterface {
   void insertMainTable(SystemParameters sp) throws SQLException;

   void insertToEvaluationTable(ClassifierHolder ch, CustomEvaluation eval)
           throws SQLException, Exception;

   void insertToFeatureSelectionTable(FeatureSelection fs) throws SQLException;

   /**
    * @param instances This is passed to know which attributes to look for
    * @throws NoSuchElementException
    * @throws SQLException
    */
   void insertToFeatureTable(Instances instances) throws NoSuchElementException,
                                                         SQLException;
}
