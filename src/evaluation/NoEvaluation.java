package evaluation;

import classifier.ClassifierHolder;
import customWeka.CustomEvaluation;
import featureSelection.FeatureSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Vector;
import weka.core.Attribute;
import weka.core.WekaEnumeration;

/**
 * Null object implementation of Evaluation.<br>
 * It's implemented as a singleton since it's ok for many objects to have
 * reference to this since this class does nothing
 */
public class NoEvaluation implements Evaluation{
   private static NoEvaluation instance;

   private NoEvaluation(){}

   public static NoEvaluation getInstance(){
      if(NoEvaluation.instance == null){
         NoEvaluation.instance = new NoEvaluation();
      }
      System.err.println("Warning, using NoEvaluation");
      return NoEvaluation.instance;
   }

   @Override
   public void setupEvaluationSets(String combinedPath) throws IOException,
                                                               Exception {
      System.err.println("Warning, setupEvaluationSets not performed!");
   }

   /**
    * {@inheritDoc}
    * @param fs
    * @return new WekaEnumeration<>(new Vector<>()); //Since Enumeration is abstract
    * @throws IOException
    * @throws NoSuchElementException
    * @throws Exception 
    */
   @Override
   public Enumeration<Attribute> applyFeatureSelection(FeatureSelection fs) throws IOException,
                                                                 NoSuchElementException,
                                                                 Exception {
      System.err.println("Warning, applyFeatureSelection not performed!");
      return new WekaEnumeration<>(new Vector<>());
   }

   /**
    * Returns an empty HashMap<>()
    * @param classifierHolders
    * @return
    * @throws Exception 
    */
   @Override
   public HashMap<String, CustomEvaluation> evaluateClassifiers(ArrayList<ClassifierHolder> classifierHolders)
           throws Exception {
      System.err.println("Warning, evaluateClassifiers not performed!");
      return new HashMap<>();
   }
}