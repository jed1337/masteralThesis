package driver;

import constants.ArffInstanceCount;
import driver.systemConfiguration.ExtraNoise;
import driver.systemConfiguration.Hybrid;
import driver.systemConfiguration.Single;
import java.io.FileNotFoundException;
import java.io.IOException;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;

public class Driver{
   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      final String folderPath = "FeatureSelected (LR)/Halved/ExtraNoise/";
      new Hybrid(ArffInstanceCount.HALVED, new ExtraNoise(), new Logistic())
         .execute(folderPath);
      new Single(ArffInstanceCount.HALVED, new ExtraNoise(), new Logistic())
         .execute(folderPath);
      
//      system();
   }

   public static void system() throws IOException, Exception{
      Instances validation   = UtilsInstances.getInstances("Results/TestTrainValidation/NormalOrAttack/Validation.arff");
      Instances hlValidation = UtilsInstances.getInstances("Results/TestTrainValidation/HL/Validation.arff");
      Classifier normalClassifier = UtilsClssifiers.readModel("Results/TestTrainValidation/NormalOrAttack/KNN.model");
      Classifier hlClassifier     = UtilsClssifiers.readModel("Results/TestTrainValidation/HL/KNN.model");

      for(int i=0; i< validation.classAttribute().numValues(); i++){
         System.out.println("Value "+i+" = "+validation.classAttribute().value(i));
      }
      for (int i = 0; i < validation.size(); i++) {

         String predictedValue = validation.classAttribute().value((int)normalClassifier.classifyInstance(validation.get(i)));
         String actualValue = validation.get(i).stringValue(26);

         if(predictedValue.equalsIgnoreCase("attack")){
            predictedValue = hlValidation.classAttribute().value((int)hlClassifier.classifyInstance(validation.get(i)));
         }

         System.out.println(
            i+")\tPredictedValue: "+predictedValue
            +"\t\tActualValue:\t"+actualValue
         );
      }
   }
}
