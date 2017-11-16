package driver;

import utils.Utils;
import weka.classifiers.Classifier;
import weka.core.Instances;

public class ClassifierHolder {
   private final Classifier classifier;
   private final Instances instances;
   private final String classifierName;
   private final String resultName;
   private final String modelName;

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
      this.resultName     = folderPath+classifierName+"Result.txt";
      this.modelName      = folderPath+classifierName+".model";
      
      Utils.makeFolders(folderPath);
      
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
}