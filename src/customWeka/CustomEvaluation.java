package customWeka;

import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;

/**
 * This class was created since Jed wanted to get m_ClassNames
 * but it's protected in Evaluation
 */
public class CustomEvaluation extends Evaluation{
   public CustomEvaluation(Instances data) throws Exception {
      super(data);
   }
   
   /**
    * The super class doesn't have a getter for this variable
    * @return super.m_ClassNames;
    */
   public String[] getClassNames(){
      return super.m_ClassNames;
   }
}