package classify;

import constants.FileNameConstants;
import driver.ClassifierHolder;
import java.io.IOException;
import utils.Utils;
import utils.UtilsClssifiers;

public class TrainTest extends Classify{
//   protected final Instances testSet;
   
   private final String testPath;
   public TrainTest(String subFolderPath, String trainPath, String testPath) throws IOException, Exception {
      this("results/TestTrain/", subFolderPath, trainPath, testPath);
   }
   
   public TrainTest(String folderPath, String subFolderPath, String trainPath, String testPath) throws IOException, Exception {
      super(folderPath, subFolderPath, trainPath);
      this.testPath = testPath;

      super.addInstance(this.testPath);
//      this.testSet =  UtilsInstances.getInstances(testPath);
//      Utils.writeFile(
//         super.fullFolderPath+FileNameConstants.TEST,
//         Utils.getFileContents(testPath)
//      );
   }
   
//   @Override
//   protected String getPath(){
//      return this.testPath;
//   }

   @Override
   public void evaluateModel() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveTestEvaluationToFile(ch, super.instancesHM.get(this.testPath));
//         UtilsClssifiers.saveTestEvaluationToFile(ch, this.testSet);
      }
   }
}