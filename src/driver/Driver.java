package driver;

import classifier.ClassifierHolder;
import driver.mode.Single;
import constants.ArffInstanceCount;
import constants.CharConstants;
import constants.DirectoryConstants;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.noiseLevel.ExtraNoise;
import driver.mode.noiseLevel.NoData;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.BiFunction;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public final class Driver {
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
   private static int[] selectedAttributes = null;

   public static void main(String[] args) throws 
         FileNotFoundException, IOException, Exception {
//      final String folderPath = "Results/FeatureSelection/hybrid to single/J48/";

      final int instanceCount = ArffInstanceCount.HALVED;
      final WrapperSubsetEval wse = new WrapperSubsetEval();
      wse.setClassifier(new J48());
      wse.setFolds(5);
      
      final NoiseLevel noiseLevel = new ExtraNoise();
      
      final BiFunction<Evaluation, ClassifierHolder, String> customEvaluation = (eval, ch)->{
         StringBuilder sb = new StringBuilder();
         sb.append(ch.getClassifierName());
         sb.append(" = ");
         sb.append(Utils.doubleToString(eval.pctCorrect(), 12, 4));
         sb.append(CharConstants.NEW_LINE);
         
         return sb.toString(); 
      };
      

      SystemTrain single = new SystemTrain(new Single(instanceCount, noiseLevel));
      single.setupTestTrainValidation();
      single.customEvaluateClassifiers(
         customEvaluation,
         DirectoryConstants.RESULTS_DIR + "collated.txt",
         "single"
      );
      System.out.println("Beginning hybrid");
      SystemTrain hybridIsAttack = new SystemTrain(new HybridIsAttack(instanceCount, noiseLevel));
      
      hybridIsAttack.setupTestTrainValidation();
      hybridIsAttack.customEvaluateClassifiers(
         customEvaluation,
         DirectoryConstants.RESULTS_DIR + "collated.txt",
         "isAttack"
      );      

//      hybrid(ArffInstanceCount.HALVED, noiseLevel, wse, new BestFirst(), folderPath);
//      single(ArffInstanceCount.HALVED, noiseLevel, wse, new BestFirst(), folderPath);
   }
   
   private static void hybrid(int instanceCount, NoiseLevel noiseLevel, ASEvaluation attributeEvaluation, ASSearch searchMethod, String folderPath)
        throws IOException, Exception {
      SystemTrain isAttack = new SystemTrain(new HybridIsAttack(instanceCount, noiseLevel));
      isAttack.setupTestTrainValidation();
      if(Driver.selectedAttributes == null){
         isAttack.applyFeatureSelection(attributeEvaluation, searchMethod);
      } else{
         isAttack.applyFeatureSelection(Driver.selectedAttributes);
      }
      isAttack.evaluateClassifiers();

      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, folderPath+"isAttack/");
      
      SystemTrain attackType = new SystemTrain(new HybridDDoSType(instanceCount, NoData.getInstance()));
      attackType.setupTestTrainValidation();
      if(Driver.selectedAttributes == null){
         attackType.applyFeatureSelection(attributeEvaluation, searchMethod);
      } else{
         attackType.applyFeatureSelection(Driver.selectedAttributes);
      }
      attackType.evaluateClassifiers();

      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, folderPath+"DDoS type/");
   }

   private static void single(int instanceCount, NoiseLevel noiseLevel, ASEvaluation attributeEvaluation, ASSearch searchMethod, String folderPath)
           throws IOException, Exception {
//
      SystemTrain single = new SystemTrain(new Single(instanceCount, noiseLevel));
      single.setupTestTrainValidation();
//      Driver.selectedAttributes =  single.applyFeatureSelection(attributeEvaluation, searchMethod);
      if (Driver.selectedAttributes == null) {
         Driver.selectedAttributes = single.applyFeatureSelection(
                 attributeEvaluation, searchMethod);
      } else {
         single.applyFeatureSelection(Driver.selectedAttributes);
      }
      single.evaluateClassifiers();

      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, folderPath+"Single/");
   }
}
