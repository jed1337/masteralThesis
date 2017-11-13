package classify;

import formatkddarff.ClassifierHolder;
import utils.UtilsClssifiers;
import utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public abstract class Classify {
   protected final String folderPath;
   protected final ArrayList<ClassifierHolder> classifiers;
   
   protected Classify(String folderPath) throws IOException, Exception {
      this.folderPath = folderPath;
      Utils.makeFolders(folderPath);
      
      classifiers = new ArrayList<>();
   }
   
   public abstract void buildModel() throws Exception;
   
   protected void buildModel(Instances instances) throws Exception{
      this.classifiers.add(new ClassifierHolder(new NaiveBayes(), instances, "NB",  this.folderPath));
      this.classifiers.add(new ClassifierHolder(new IBk(),        instances, "KNN", this.folderPath));
      this.classifiers.add(new ClassifierHolder(new J48(),        instances, "J48", this.folderPath));
      this.classifiers.add(new ClassifierHolder(new SMO(),        instances, "SMO", this.folderPath));
   };
   
   public void writeModel() throws Exception{
      for (ClassifierHolder ch : this.classifiers) {
         UtilsClssifiers.writeModel(ch);
      }
   };
   public abstract void evaluateModel() throws Exception;
}