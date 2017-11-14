package classify;

import formatkddarff.ClassifierHolder;
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
   protected final String folderPath;
   protected final ArrayList<ClassifierHolder> classifiers;
   
   private final Instances trainSet;
   
   protected Classify(String folderPath, String trainPath) throws IOException, Exception {
      this.folderPath = folderPath;
      Utils.makeFolders(folderPath);
      
      this.trainSet = UtilsInstances.getInstances(trainPath);
      Utils.writeFile(
         this.folderPath + "CrossValidationSet.arff",
         Utils.getFileContents(trainPath), 
         false);
      
      this.classifiers = new ArrayList<>();
   }
   
   public void buildModel() throws Exception{
      this.classifiers.add(new ClassifierHolder(new NaiveBayes(), this.trainSet, "NB",  this.folderPath));
      this.classifiers.add(new ClassifierHolder(new IBk(),        this.trainSet, "KNN", this.folderPath));
      this.classifiers.add(new ClassifierHolder(new J48(),        this.trainSet, "J48", this.folderPath));
      this.classifiers.add(new ClassifierHolder(new SMO(),        this.trainSet, "SMO", this.folderPath));
   };
   
   public void writeModel() throws Exception{
      for (ClassifierHolder ch : this.classifiers) {
         UtilsClssifiers.writeModel(ch);
      }
   };
   public abstract void evaluateModel() throws Exception;
}