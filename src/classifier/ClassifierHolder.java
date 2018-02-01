package classifier;

import weka.classifiers.Classifier;

/**
 * This class was created so that a classifier can have other other fields like
 * a user-friendly name 
 * <br>
 * This class also states the naming convention to be used when saving the
 * model and evaluation result to a file 
 * (Note that this class doesn't write to files, 
 * that job's left to other classes)
 */
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