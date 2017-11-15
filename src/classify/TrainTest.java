package classify;

import constants.FileNameConstants;
import formatkddarff.ClassifierHolder;
import utils.UtilsClssifiers;
import utils.Utils;
import utils.UtilsInstances;
import java.io.IOException;
import weka.core.Instances;

public class TrainTest extends Classify{
   protected final Instances testSet;

   public TrainTest(String subFolderPath, String trainPath, String testPath) throws IOException, Exception {
      this("results/TestTrain/", subFolderPath, trainPath, testPath);
   }
   
   public TrainTest(String folderPath, String subFolderPath, String trainPath, String testPath) throws IOException, Exception {
      super(folderPath, subFolderPath, trainPath);

      this.testSet =  UtilsInstances.getInstances(testPath);
      Utils.writeFile(
         super.fullPath+FileNameConstants.TEST,
         Utils.getFileContents(testPath)
      );
   }

   @Override
   public void evaluateModel() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveTestEvaluationToFile(ch, this.testSet);
      }
   }
}