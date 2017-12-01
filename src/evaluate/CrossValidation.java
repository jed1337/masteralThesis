package evaluate;

import utils.UtilsClssifiers;
import java.io.IOException;

public class CrossValidation extends Evaluate{
   public CrossValidation(String subFolderPath, String CrossValidationPath) throws IOException, Exception {
      this("results/CrossValidation/",subFolderPath, CrossValidationPath);
   }
   
   public CrossValidation(String folderPath, String subFolderPath, String CrossValidationPath) throws IOException, Exception {
      super(folderPath, subFolderPath, CrossValidationPath);
   }

   @Override
   public void evaluateModel() throws Exception{
      UtilsClssifiers.saveCrossValidationToFile(
         super.classifierHolders,
         10
      );
   }
}