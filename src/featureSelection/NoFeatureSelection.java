package featureSelection;

import java.util.ArrayList;
import preprocessFiles.preprocessEvaluationSet.EvaluationSet;
import weka.core.Instances;

/**
 * Null object implementation of FeatureSelection.<br>
 * It's implemented as a singleton since it's ok for many objects to have
 * reference to this since this class does nothing
 */
public class NoFeatureSelection implements FeatureSelection{
   private static NoFeatureSelection instance = null;

   private NoFeatureSelection() {}

   public static NoFeatureSelection getInstance(){
      if (NoFeatureSelection.instance == null) {
         NoFeatureSelection.instance = new NoFeatureSelection();
      }
      return NoFeatureSelection.instance;
   }

   /**
    * Does nothing. It takes parameters because the interface it's implementing
    * has parameters
    * @param trainSet (Not used)
    * @param evaluationSets (Not used)
    * @throws Exception (Not used)
    */
   @Override
   public void applyFeatureSelection(Instances trainSet, ArrayList<EvaluationSet> evaluationSets) throws Exception {
      System.out.println("Not doing feature selection");
   }

   @Override
   public String getFSMethodName() {
      return "No feature selection";
   }
}