package classify;

import constants.FileNameConstants;
import driver.ClassifierHolder;
import java.io.IOException;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.core.Instances;

public class TrainTestValidation extends TrainTest{
   protected final Instances validationSet;

   public TrainTestValidation(String subFolderPath, String trainPath, String testPath, String validationPath) throws IOException, Exception {
      this("results/TestTrainValidation/", subFolderPath, trainPath, testPath, validationPath);
   }

   public TrainTestValidation(String folderPath, String subFolderPath, String trainPath, String testPath, String validationPath) throws IOException, Exception {
      super(folderPath, subFolderPath, trainPath, testPath);

      this.validationSet = UtilsInstances.getInstances(validationPath);
      Utils.writeFile(
         super.fullPath + FileNameConstants.VALIDATION,
         Utils.getFileContents(validationPath)
      );
   }
   
   public void evaluateValidation() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveTestEvaluationToFile(ch, this.validationSet);
      }
   }
}