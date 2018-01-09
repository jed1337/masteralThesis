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
import globalParameters.GlobalFeatureExtraction;

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

      final FeatureSelection[] featureSelections = new FeatureSelection[]{
         NoFeatureSelection.getInstance()
      };
      final CategoricalType[] categoricalTypes = new CategoricalType[]{
         new GeneralAttackType(),
         new SpecificAttackType()
      };
      final NoiseLevel[] noiseLevels = new NoiseLevel[]{
         NoNoise.getInstance(), 
         new NoiseNormal()
      };

      for (FeatureSelection fs : featureSelections) {
         for (CategoricalType categoricalType : categoricalTypes) {
            for (NoiseLevel noiseLevel : noiseLevels) {
               systemTrain(fs, new Single (instanceCount, noiseLevel, categoricalType));
            }
         }
         for (NoiseLevel noiseLevel : noiseLevels) {
            systemTrain(fs, new HybridIsAttack(instanceCount, noiseLevel));
         }
         for (CategoricalType categoricalType : categoricalTypes) {
            systemTrain(fs, new HybridDDoSType(instanceCount, categoricalType));
         }
      }
   }

   private static void systemTrain(FeatureSelection fs, Mode mode)
           throws IOException, Exception {
      String fullFolderPath = String.join("/",
         "Results",
         "Dry run",
         "Edited mode",
         mode.getCategoricalType().name(),
         mode.getNoiseLevelString(),
         fs.getFSMethodName(),
         mode.getSystemType()
      )+"/";

      new SystemTrain(mode, fs);

      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, fullFolderPath);
   }
}
