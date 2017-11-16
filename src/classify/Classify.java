package classify;

import constants.FileNameConstants;
import driver.ClassifierHolder;
import utils.UtilsClssifiers;
import utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import utils.UtilsInstances;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public abstract class Classify {
   protected final String fullPath;
   protected final ArrayList<ClassifierHolder> classifiers;
   
   private final Instances trainSet;
   
   protected Classify(String folderPath, String subFolderPath, String trainPath) throws IOException, Exception {
      this.fullPath = folderPath+subFolderPath;
      Utils.makeFolders(this.fullPath);
      
      this.trainSet = UtilsInstances.getInstances(trainPath);
      Utils.writeFile(
         this.fullPath + FileNameConstants.TRAIN,
         Utils.getFileContents(trainPath)
      );
      
      this.classifiers = new ArrayList<>();
   }
   
   public void buildModel() throws Exception{
      this.classifiers.add(new ClassifierHolder(new NaiveBayes(), this.trainSet, "NB",  this.fullPath));
      this.classifiers.add(new ClassifierHolder(new IBk(),        this.trainSet, "KNN", this.fullPath));
      this.classifiers.add(new ClassifierHolder(new J48(),        this.trainSet, "J48", this.fullPath));
      this.classifiers.add(new ClassifierHolder(new SMO(),        this.trainSet, "SMO", this.fullPath));
   };
   
   public void writeModel() throws Exception{
      for (ClassifierHolder ch : this.classifiers) {
         UtilsClssifiers.writeModel(ch);
      }
   };
   public abstract void evaluateModel() throws Exception;
}