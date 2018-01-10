package classifier;

import weka.classifiers.Classifier;

public final class ClassifierHolder {
   private final Classifier classifier;
   private final String classifierName;

   public ClassifierHolder(
           Classifier classifier, String classifierName) 
           throws Exception {
      this.classifier     = classifier;
      this.classifierName = classifierName;
   }
   
   public Classifier getClassifier() {
      return classifier;
   }

   public String getClassifierName() {
      return classifierName;
   }
   
   public String getResultName(){
      return this.classifierName+"Result.txt";
   }
   
   public String getModelName(){
      return this.classifierName+".model";
   }
}