package classify;

import driver.ClassifierHolder;
import java.io.IOException;
import utils.UtilsClssifiers;

public class TrainTestValidation extends TrainTest{
//   protected final Instances validationSet;

   private final String validationPath;
   public TrainTestValidation(String subFolderPath, String trainPath, String testPath, String validationPath) throws IOException, Exception {
      this("results/TestTrainValidation/", subFolderPath, trainPath, testPath, validationPath);
   }

   public TrainTestValidation(String folderPath, String subFolderPath, String trainPath, String testPath, String validationPath) throws IOException, Exception {
      super(folderPath, subFolderPath, trainPath, testPath);
      this.validationPath = validationPath;
      super.addInstance(this.validationPath);
//      this.validationSet = UtilsInstances.getInstances(validationPath);
//      Utils.writeFile(
//         super.fullFolderPath + FileNameConstants.VALIDATION,
//         Utils.getFileContents(validationPath)
//      );
   }

//   @Override
//   protected String getPath(){
//      return this.validationPath;
//   }

   public void evaluateValidation() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveTestEvaluationToFile(ch, super.instancesHM.get(validationPath));
//         UtilsClssifiers.saveTestEvaluationToFile(ch, this.validationSet);
      }
   }
}