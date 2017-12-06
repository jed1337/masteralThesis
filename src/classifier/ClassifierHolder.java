package classifier;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class ClassifierHolder {
   private final Classifier classifier;
//   private final Instances instances;
   private final String classifierName;
   private final String folderPath;

   public ClassifierHolder(Classifier classifier,
//                           Instances trainSet,
                           String classifierName) throws Exception {
      this(
         classifier, 
//         trainSet, 
         classifierName, 
         ""
      );
   }

   public ClassifierHolder(Classifier classifier,
//                           Instances trainSet,
                           String classifierName,
                           String folderPath) throws Exception {
      this.classifier     = classifier;
      
      // The classifier isn't built yet
//      this.classifier.buildClassifier(trainSet);
      
//      this.instances      = trainSet;
      this.classifierName = classifierName;
      this.folderPath = folderPath;
   }
   
   public Classifier getClassifier() {
      return classifier;
   }

//   public Instances getInstances() {
//      return instances;
//   }

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