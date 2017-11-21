package classify;

import constants.FileNameConstants;
import driver.ClassifierHolder;
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
import weka.core.Instances;

public abstract class Classify {
   protected final String fullFolderPath;
   protected final ArrayList<ClassifierHolder> classifiers;
   
   protected final HashMap<String, Instances> instancesHM;
   
//   private final Instances trainSet;
   private final String trainPath;
   
   protected Classify(String folderPath, String subFolderPath, String trainPath) throws IOException, Exception {
      this.fullFolderPath = folderPath+subFolderPath;
      Utils.makeFolders(this.fullFolderPath);
      
      this.instancesHM = new HashMap<>();
      this.trainPath = trainPath;
      addInstance(trainPath);

//Put this function outside      
      Utils.duplicateFile(trainPath, this.fullFolderPath+FileNameConstants.TRAIN);
      
//      this.trainSet = UtilsInstances.getInstances(trainPath);
//      Utils.duplicateFile(
//         this.fullFolderPath + FileNameConstants.TRAIN,
//         Utils.getFileContents(trainPath)
//      );
      
      this.classifiers = new ArrayList<>();
   }

   public String getFullFolderPath() {
      return this.fullFolderPath;
   }
   
   protected final void addInstance(String path) throws IOException {
      Instances instances = UtilsInstances.getInstances(path);
      Utils.addToMap(instancesHM, path, instances);
   }
   
   public final void buildModel() throws Exception{
      buildModel(this.trainPath);
   };
   
   protected final void buildModel(String key) throws Exception{
      this.classifiers.add(new ClassifierHolder(new NaiveBayes(), this.instancesHM.get(key), "NB",  this.fullFolderPath));
      this.classifiers.add(new ClassifierHolder(new IBk(),        this.instancesHM.get(key), "KNN", this.fullFolderPath));
      this.classifiers.add(new ClassifierHolder(new J48(),        this.instancesHM.get(key), "J48", this.fullFolderPath));
      this.classifiers.add(new ClassifierHolder(new SMO(),        this.instancesHM.get(key), "SMO", this.fullFolderPath));
   }
   
   public final void writeModel() throws Exception{
      for (ClassifierHolder ch : this.classifiers) {
         UtilsClssifiers.writeModel(ch);
      }
   };
   
   public abstract void evaluateModel() throws Exception;
}