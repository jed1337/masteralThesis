package classifier;

import weka.classifiers.Classifier;

public final class ClassifierHolder {
   private final Classifier classifier;
   private final String classifierName;
   private final String folderPath;

   public ClassifierHolder(
           Classifier classifier, String classifierName) 
           throws Exception {
      this(classifier, classifierName, "");
   }

   public ClassifierHolder(
           Classifier classifier, String classifierName, String folderPath)
           throws Exception {
      this.classifier     = classifier;
      this.classifierName = classifierName;
      this.folderPath = folderPath;
   }
   
   public Classifier getClassifier() {
      return classifier;
   }

   public String getClassifierName() {
      return classifierName;
   }
   
   public String getResultName(){
      return this.folderPath+classifierName+"Result.txt";
   }
   
   public String getModelName(){
      return this.folderPath+classifierName+".model";
   }
   
   public String getFolderPath() {
      return folderPath;
   }
}