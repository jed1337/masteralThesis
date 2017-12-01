package evaluate;

import classifier.ClassifierHolder;
import java.io.IOException;
import utils.UtilsClssifiers;

public class TrainTestValidation extends TrainTest{

   private final String validationPath;
   public TrainTestValidation(String subFolderPath, String trainPath, String testPath, String validationPath) throws IOException, Exception {
      this("results/TestTrainValidation/", subFolderPath, trainPath, testPath, validationPath);
   }

   public TrainTestValidation(String folderPath, String subFolderPath, String trainPath, String testPath, String validationPath) throws IOException, Exception {
      super(folderPath, subFolderPath, trainPath, testPath);
      this.validationPath = validationPath;
      super.addInstance(this.validationPath);
   }

   public void evaluateValidation() throws Exception{
      for (ClassifierHolder ch : super.classifierHolders) {
         UtilsClssifiers.saveTestEvaluationToFile(ch, super.instancesHM.get(validationPath));
      }
   }
}