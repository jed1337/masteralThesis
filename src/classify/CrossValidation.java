package classify;

import formatkddarff.ClassifierHolder;
import utils.UtilsClssifiers;
import utils.Utils;
import utils.UtilsInstances;
import java.io.IOException;
import weka.core.Instances;

public class CrossValidation extends Classify{
   private final Instances CrossValidationSet;

   public CrossValidation(String subFolderName, String CrossValidationPath) throws IOException, Exception {
      super("results/CrossValidation/"+subFolderName);
      
      this.CrossValidationSet = UtilsInstances.getInstances(CrossValidationPath);
      Utils.writeFile(super.folderPath + "CrossValidationSet.arff", Utils.getFileContents(CrossValidationPath), false);
   }
   
   @Override
   public void buildModel() throws Exception{
      super.buildModel(this.CrossValidationSet);
   }

   @Override
   public void evaluateModel() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveCrossValidationToFile(ch, 10);
      }
   }
}