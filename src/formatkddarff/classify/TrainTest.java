package formatkddarff.classify;

import formatkddarff.ClassifierHolder;
import formatkddarff.utils.UtilsClssifiers;
import formatkddarff.utils.Utils;
import formatkddarff.utils.UtilsInstances;
import java.io.IOException;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
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

      super.classifiers.add(new ClassifierHolder(new NaiveBayes(),   this.trainSet, "NB", super.folderPath));
      super.classifiers.add(new ClassifierHolder(new IBk(),          this.trainSet, "KNN", super.folderPath));
      super.classifiers.add(new ClassifierHolder(new J48(),          this.trainSet, "J48", super.folderPath));
      super.classifiers.add(new ClassifierHolder(new SMO(),          this.trainSet, "SMO", super.folderPath));
   }

   @Override
   public void evaluateModel() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveTestEvaluationToFile(ch, this.testSet);
      }
   }
}