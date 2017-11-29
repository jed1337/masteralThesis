package evaluate;

import classifier.ClassifierHolder;
import java.io.IOException;
import utils.UtilsClssifiers;

public class TrainTest extends Evaluate{
   private final String testPath;
   public TrainTest(String subFolderPath, String trainPath, String testPath) throws IOException, Exception {
      this("results/TestTrain/", subFolderPath, trainPath, testPath);
   }
   
   public TrainTest(String folderPath, String subFolderPath, String trainPath, String testPath) throws IOException, Exception {
      super(folderPath, subFolderPath, trainPath);
      this.testPath = testPath;

      super.addInstance(this.testPath);
   }
   
   @Override
   public void evaluateModel() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveTestEvaluationToFile(ch, super.instancesHM.get(this.testPath));
      }
   }
}