package classify;

import formatkddarff.ClassifierHolder;
import utils.UtilsClssifiers;
import java.io.IOException;

public class CrossValidation extends Classify{
   public CrossValidation(String subFolderName, String CrossValidationPath) throws IOException, Exception {
      super("results/CrossValidation/"+subFolderName, CrossValidationPath);
      
//      this.CrossValidationSet = UtilsInstances.getInstances(CrossValidationPath);
//      Utils.writeFile(super.folderPath + "CrossValidationSet.arff", Utils.getFileContents(CrossValidationPath), false);
   }

   @Override
   public void evaluateModel() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveCrossValidationToFile(ch, 10);
      }
   }
}