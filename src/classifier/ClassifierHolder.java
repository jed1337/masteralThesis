package classifier;

import weka.classifiers.Classifier;
import weka.core.Instances;

public class ClassifierHolder {
   private final Classifier classifier;
   private final Instances instances;
   private final String classifierName;
   private final String resultName;
   private final String modelName;
   private final String folderPath;

   public ClassifierHolder(Classifier classifier,
                           Instances instances,
                           String classifierName) throws Exception {
      this(classifier, instances, classifierName, "");
   }

   public ClassifierHolder(Classifier classifier,
                           Instances instances,
                           String classifierName,
                           String folderPath) throws Exception {
      this.classifier     = classifier;
      
      // The classifier isn't built yet
      this.classifier.buildClassifier(instances);
      
      this.instances      = instances;
      this.classifierName = classifierName;
      
      this.folderPath = folderPath;
      this.resultName = this.folderPath+classifierName+"Result.txt";
      this.modelName  = this.folderPath+classifierName+".model";
      
      classifier.buildClassifier(instances);
   }
   
   public Classifier getClassifier() {
      return classifier;
   }

   public Instances getInstances() {
      return instances;
   }

   public String getClassifierName() {
      return classifierName;
   }

   public String getResultName() {
      return resultName;
   }

   public String getModelName() {
      return modelName;
   }

   public String getFolderPath() {
      return folderPath;
   }
}