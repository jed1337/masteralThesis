package driver;

import constants.ArffInstanceCount;
import constants.DirectoryConstants;
import driver.categoricalType.GeneralAttackType;
import driver.mode.Mode;
import driver.mode.Single;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.FileNotFoundException;
import java.io.IOException;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import driver.categoricalType.CategoricalType;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.noiseLevel.NoiseNormal;

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
   
   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      final int instanceCount = ArffInstanceCount.HALVED;

      final NoiseLevel noiseLevel = new NoiseNormal();
      
      final WrapperSubsetEval asEval = new WrapperSubsetEval();
      asEval.setClassifier(new J48());
      asEval.setFolds(5);
      
      String fs = "J48/";
      
      final CategoricalType categoricalType = new GeneralAttackType();
      
      systemTrain(new Single        (instanceCount, noiseLevel, categoricalType), asEval, fs+"single/");
      systemTrain(new HybridIsAttack(instanceCount, new NoiseNormal()), asEval, fs+"isAttack/");
      systemTrain(new HybridDDoSType(instanceCount, categoricalType), asEval, fs+"DDoS type/");
      
      System.out.println("");
   }

   private static int[] systemTrain(final Mode mode, final ASEvaluation attributeEvaluator, final String folderPath)
           throws IOException, Exception {
      String fullFolderPath = 
         String.join("/", 
            "Results",
            mode.getCategoricalType().name(),
            mode.getNoiseLevelString(),
            folderPath
         );
      
      SystemTrain st = new SystemTrain(mode);
      st.setupTestTrainValidation();
      int[] result = st.applyFeatureSelection(attributeEvaluator);
      st.evaluateClassifiers();
      
      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, fullFolderPath);
      return result;
   }
   
   private static void systemTrain(final Mode mode, final int[] selectedAttribtues, final String folderPath)
           throws IOException, Exception {
      SystemTrain st = new SystemTrain(mode);
      st.setupTestTrainValidation();
      st.applyFeatureSelection(selectedAttribtues);
      st.evaluateClassifiers();
      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, folderPath);
   }
}
