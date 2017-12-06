package driver;

import driver.mode.Single;
import constants.ArffInstanceCount;
import constants.PathConstants;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import java.io.FileNotFoundException;
import java.io.IOException;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class Driver {
//<editor-fold defaultstate="collapsed" desc="System">
   public static void system() throws IOException, Exception {
      Instances validation = UtilsInstances.getInstances(
              "Results/TestTrainValidation/NormalOrAttack/Validation.arff");
      Instances hlValidation = UtilsInstances.getInstances(
              "Results/TestTrainValidation/HL/Validation.arff");
      Classifier normalClassifier = UtilsClssifiers.readModel(
              "Results/TestTrainValidation/NormalOrAttack/KNN.model");
      Classifier hlClassifier = UtilsClssifiers.readModel(
              "Results/TestTrainValidation/HL/KNN.model");

      for (int i = 0; i < validation.classAttribute().numValues(); i++) {
         System.out.println("Value " + i + " = " + validation.classAttribute()
                 .value(i));
      }
      for (int i = 0; i < validation.size(); i++) {

         String predictedValue = validation.classAttribute().value(
                 (int) normalClassifier.classifyInstance(validation.get(i)));
         String actualValue = validation.get(i).stringValue(26);

         if (predictedValue.equalsIgnoreCase("attack")) {
            predictedValue = hlValidation.classAttribute().value(
                    (int) hlClassifier.classifyInstance(validation.get(i)));
         }

         System.out.println(
                 i + ")\tPredictedValue: " + predictedValue
                 + "\t\tActualValue:\t" + actualValue
         );
      }
   }
//</editor-fold>
   private static int[] selectedAttributes;

   public static void main(String[] args) throws FileNotFoundException,
                                                 IOException, Exception {
//            new Hybrid(ArffInstanceCount.HALVED, new ExtraNoise(), new NaiveBayes())
//         .setupTestTrainValidation(folderPath);
//      new Single(ArffInstanceCount.HALVED, new ExtraNoise(), new NaiveBayes())

//      final String folderPath = "Results/FeatureSelected (NB)/Halved/ExtraNoise (Allah)/";
      final String folderPath = "Results/Test/";

      WrapperSubsetEval wse = new WrapperSubsetEval();
      wse.setClassifier(new NaiveBayes());
      wse.setFolds(5);

      hybrid(ArffInstanceCount.HALVED, wse, new BestFirst(), folderPath);
      single(ArffInstanceCount.HALVED, wse, new BestFirst(), folderPath);
   }
   
   private static void hybrid(int count, ASEvaluation attributeEvaluation, ASSearch searchMethod, String folderPath)
        throws IOException, Exception {
      SystemTrain isAttack = new SystemTrain(new HybridIsAttack(count));
      isAttack.setupTestTrainValidation();
      Driver.selectedAttributes =  isAttack.applyFeatureSelection(attributeEvaluation, searchMethod);
      isAttack.testClassifier();

      Utils.duplicateDirectory(PathConstants.FORMATTED_DIR, folderPath+"isAttack/");
      
      SystemTrain attackType = new SystemTrain(new HybridDDoSType(count));
      attackType.setupTestTrainValidation();
      Driver.selectedAttributes = attackType.applyFeatureSelection(
              attributeEvaluation, searchMethod);
      attackType.testClassifier();

      Utils.duplicateDirectory(PathConstants.FORMATTED_DIR, folderPath+"DDoS type/");
   }

   private static void single(int count, ASEvaluation attributeEvaluation, ASSearch searchMethod, String folderPath)
           throws IOException, Exception {
//
      SystemTrain single = new SystemTrain(new Single(count));
      single.setupTestTrainValidation();
//      Driver.selectedAttributes =  single.applyFeatureSelection(attributeEvaluation, searchMethod);
      single.applyFeatureSelection(selectedAttributes);
      single.testClassifier();

      Utils.duplicateDirectory(PathConstants.FORMATTED_DIR, folderPath+"Single/");
   }
}
