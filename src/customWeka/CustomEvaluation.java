package customWeka;

import weka.core.Instances;
import weka.classifiers.evaluation.Evaluation;

public class CustomEvaluation extends Evaluation{
   public CustomEvaluation(Instances data) throws Exception {
      super(data);
   }
   
   public String[] getClassNames(){
      return super.m_ClassNames;
   }
}