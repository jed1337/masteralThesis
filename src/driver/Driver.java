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
import weka.classifiers.Classifier;
import weka.core.Instances;
import driver.categoricalType.CategoricalType;
import driver.categoricalType.SpecificAttackType;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.noiseLevel.NoNoise;
import driver.mode.noiseLevel.NoiseNormal;
import featureExtraction.KDDExtraction;
import featureSelection.FeatureSelection;
import featureSelection.NoFeatureSelection;
import globalClasses.GlobalFeatureExtraction;

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
      GlobalFeatureExtraction.setInstance(new KDDExtraction());
      final int instanceCount = ArffInstanceCount.HALVED;

//      final WrapperSubsetEval asEval = null;
//      final WrapperSubsetEval asEval = new WrapperSubsetEval();
//      asEval.setClassifier(new NaiveBayes());
//      asEval.setFolds(5);
//      final String fs = "No feature selection/";
      final FeatureSelection fs = NoFeatureSelection.getInstance();
      final CategoricalType[] categoricalTypes = new CategoricalType[]{new GeneralAttackType(),new SpecificAttackType()};
      final NoiseLevel[] noiseLevels = new NoiseLevel[]{NoNoise.getInstance(), new NoiseNormal()};

      for (CategoricalType categoricalType : categoricalTypes) {
         for (NoiseLevel noiseLevel : noiseLevels) {

            systemTrain(fs, "single/",    new Single        (instanceCount, noiseLevel, categoricalType));
            systemTrain(fs, "isAttack/",  new HybridIsAttack(instanceCount, noiseLevel));
            systemTrain(fs, "DDoS type/", new HybridDDoSType(instanceCount, categoricalType));
         }
      }
   }

   private static int[] systemTrain(FeatureSelection fs, String folderPath, Mode mode)
           throws IOException, Exception {
      String fullFolderPath = String.join("/",
         "Results",
         "Dry run",
         "Edited mode",
         mode.getCategoricalType().name(),
         mode.getNoiseLevelString(),
         fs.getFSMethodName(),
         folderPath
      );

      SystemTrain st = new SystemTrain(mode);
      st.setupTestTrainValidation();
//      int[] result = st.applyFeatureSelection(attributeEvaluator);
      st.evaluateClassifiers();

      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, fullFolderPath);
      return null;
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
