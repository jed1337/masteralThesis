package formatkddarff.classify;

import formatkddarff.ClassifierHolder;
import formatkddarff.utils.UtilsClssifiers;
import formatkddarff.utils.Utils;
import formatkddarff.utils.UtilsInstances;
import java.io.IOException;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class CrossValidation extends Classify{
   private final Instances CrossValidationSet;

   public CrossValidation(String subFolderName, String CrossValidationPath) throws IOException, Exception {
      super("results/CrossValidation/"+subFolderName);
      
      this.CrossValidationSet = UtilsInstances.getInstances(CrossValidationPath);
      Utils.writeFile(super.folderPath + "CrossValidationSet.arff", Utils.getFileContents(CrossValidationPath), false);

      super.classifiers.add(new ClassifierHolder(new NaiveBayes(),   this.CrossValidationSet, "NB", super.folderPath));
      super.classifiers.add(new ClassifierHolder(new IBk(),          this.CrossValidationSet, "KNN",super.folderPath));
      super.classifiers.add(new ClassifierHolder(new J48(),          this.CrossValidationSet, "J48",super.folderPath));
      super.classifiers.add(new ClassifierHolder(new SMO(),          this.CrossValidationSet, "SMO",super.folderPath));

   }

   @Override
   public void evaluateModel() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveCrossValidationToFile(ch, 10);
      }
   }
}