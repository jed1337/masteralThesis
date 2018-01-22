package customWeka;

import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;

/**
 * This class was created since I wanted to get m_ClassNames
 * but it's protected in Evaluation
 */
public class CustomEvaluation extends Evaluation{
   public CustomEvaluation(Instances data) throws Exception {
      super(data);
   }
   
   public String[] getClassNames(){
      return super.m_ClassNames;
   }
}