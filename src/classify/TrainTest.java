package classify;

import formatkddarff.ClassifierHolder;
import utils.UtilsClssifiers;
import utils.Utils;
import utils.UtilsInstances;
import java.io.IOException;
import weka.core.Instances;

public class TrainTest extends Classify{
   private final Instances trainSet;
   private final Instances testSet;

   public TrainTest(String subFolderPath, String trainPath, String testPath) throws IOException, Exception {
      super("results/TestTrain/"+subFolderPath);

      this.trainSet = UtilsInstances.getInstances(trainPath);
      Utils.writeFile(super.folderPath+"trainSet.arff",Utils.getFileContents(trainPath), false);

      this.testSet =  UtilsInstances.getInstances(testPath);
      Utils.writeFile(super.folderPath+"testSet.arff", Utils.getFileContents(testPath), false);
   }

   @Override
   public void buildModel() throws Exception{
      super.buildModel(this.trainSet);
   }

   @Override
   public void evaluateModel() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveTestEvaluationToFile(ch, this.testSet);
      }
   }
}