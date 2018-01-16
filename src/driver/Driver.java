package driver;

import constants.ArffInstanceCount;
import constants.DirectoryConstants;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.FileNotFoundException;
import java.io.IOException;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.core.Instances;
import driver.categoricalType.CategoricalType;
import driver.categoricalType.GeneralAttackType;
import driver.categoricalType.SpecificAttackType;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.Single;
import driver.mode.noiseLevel.NoNoise;
import driver.mode.noiseLevel.Noise;
import featureExtraction.KDDExtraction;
import featureSelection.FeatureSelection;
import featureSelection.J48Wrapper;
import featureSelection.NBWrapper;
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
//      GlobalFeatureExtraction.setInstance(new NetmateExtraction());
      final int instanceCount = ArffInstanceCount.HALVED;

      final FeatureSelection[] featureSelections = new FeatureSelection[]{
         NoFeatureSelection.getInstance(),
         new NBWrapper(),
         new J48Wrapper()
      };
      final CategoricalType[] categoricalTypes = new CategoricalType[]{
         new GeneralAttackType(),
         new SpecificAttackType()
      };
      final NoiseLevel[] noiseLevels = new NoiseLevel[]{
         new Noise(),
         NoNoise.getInstance()
      };

      for (FeatureSelection fs : featureSelections) {
         for (CategoricalType categoricalType : categoricalTypes) {
            for (NoiseLevel noiseLevel : noiseLevels) {
               systemTrain(fs, new SystemParameters.Builder(
                  instanceCount,
                  new Single (noiseLevel, categoricalType)
               ).build());
            }
         }
         for (NoiseLevel noiseLevel : noiseLevels) {
            systemTrain(fs, new SystemParameters.Builder(
              instanceCount,
              new HybridIsAttack(noiseLevel)
            ).build());
         }
         for (CategoricalType categoricalType : categoricalTypes) {
            systemTrain(fs, new SystemParameters.Builder(
              instanceCount,
              new HybridDDoSType(categoricalType)
            ).build());
         }
      }
   }

   private static void systemTrain(FeatureSelection fs, SystemParameters systemParameters)
           throws IOException, Exception {
      String fullFolderPath = String.join("/",
         "Results",
         "Dry run",
         GlobalFeatureExtraction.getInstance().getName(),
         systemParameters.getCategoricalType().name(),
         systemParameters.getNoiseLevelString(),
         fs.getFSMethodName(),
         systemParameters.getSystemType()
      )+"/";

      new SystemTrain(systemParameters, fs);

      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, fullFolderPath);
   }
}
