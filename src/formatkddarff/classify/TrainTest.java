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

   public TrainTest(String folderPath, String trainPath, String testPath) throws IOException, Exception {
      super(folderPath);

      this.trainSet = UtilsInstances.getInstances(trainPath);
      Utils.writeFile(folderPath+"trainSet.arff", this.trainSet.toString(), false);

      this.testSet =  UtilsInstances.getInstances(testPath);
      Utils.writeFile(folderPath+"testSet.arff", this.trainSet.toString(), false);

      super.classifiers.add(new ClassifierHolder(new NaiveBayes(),   this.trainSet, "NB", folderPath));
      super.classifiers.add(new ClassifierHolder(new IBk(),          this.trainSet, "KNN", folderPath));
      super.classifiers.add(new ClassifierHolder(new J48(),          this.trainSet, "J48", folderPath));
      super.classifiers.add(new ClassifierHolder(new SMO(),          this.trainSet, "SMO", folderPath));
   }

   @Override
   public void evaluateModel() throws Exception{
      for (ClassifierHolder ch : super.classifiers) {
         UtilsClssifiers.saveTestEvaluationToFile(ch, this.testSet);
      }
   }
}