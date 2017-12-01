package evaluate;

import classifier.ClassifierHolder;
import utils.UtilsClssifiers;
import utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import utils.UtilsInstances;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public abstract class Evaluate {
   protected final String fullFolderPath;
   protected final ArrayList<ClassifierHolder> classifierHolders;
   protected final HashMap<String, Instances> instancesHM;
   
   private final String trainPath;
   
   protected Evaluate(String folderPath, String subFolderPath, String trainPath) throws IOException, Exception {
      this.fullFolderPath = folderPath+subFolderPath;
      this.instancesHM = new HashMap<>();
      this.trainPath = trainPath;
      addInstance(trainPath);

      this.classifierHolders = new ArrayList<>();
   }

   public String getFullFolderPath() {
      return this.fullFolderPath;
   }
   
   protected final void addInstance(String path) throws IOException {
      Instances instances = UtilsInstances.getInstances(path);
      Utils.addToMap(instancesHM, path, instances);
   }
   
   public final void buildModel() throws Exception{
      Instances instances = this.instancesHM.get(this.trainPath);
      this.classifierHolders.add(new ClassifierHolder(new J48(),           instances, "J48", this.fullFolderPath));
      this.classifierHolders.add(new ClassifierHolder(new IBk(),           instances, "KNN", this.fullFolderPath));
      this.classifierHolders.add(new ClassifierHolder(new NaiveBayes(),    instances, "NB",  this.fullFolderPath));
      this.classifierHolders.add(new ClassifierHolder(new RandomForest(),  instances, "RF", this.fullFolderPath));
      this.classifierHolders.add(new ClassifierHolder(new SMO(),           instances, "SMO", this.fullFolderPath));
   }
   
   public final void writeModel() throws Exception{
      for (ClassifierHolder ch : this.classifierHolders) {
         UtilsClssifiers.writeModel(ch);
      }
   };
   
   public abstract void evaluateModel() throws Exception;
}