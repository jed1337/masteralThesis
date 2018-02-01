package driver;

import constants.ArffInstanceCount;
import constants.DirectoryConstants;
import database.Mysql;
import driver.categoricalType.CategoricalType;
import driver.categoricalType.GeneralAttackType;
import driver.categoricalType.SpecificAttackType;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.Single;
import driver.mode.noiseLevel.NoNoise;
import driver.mode.noiseLevel.NoiseLevel;
import featureExtraction.Decorator.JanCNISDatabase;
import featureExtraction.NetmateExtraction;
import featureSelection.FeatureSelection;
import featureSelection.NoFeatureSelection;
import globalParameters.GlobalFeatureExtraction;
import java.io.FileNotFoundException;
import java.io.IOException;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.core.Instances;

public final class Driver {
//<editor-fold defaultstate="collapsed" desc="System">
   public static void system() throws IOException, Exception {
      Instances isAttackValidation = UtilsInstances.getInstances(
              "Results/The one in the DB/BINARY/No noise/J48/isAttack/Validation.arff");
      Instances ddosTypeValidation = UtilsInstances.getInstances(
              "Results/The one in the DB/BINARY/No noise/J48/DDoS type/Validation.arff");
      
      Instances incompatible = UtilsInstances.getInstances(
              "Results/The one in the DB/NOMINAL/No noise/J48/DDoS type/Validation.arff");
      
      Classifier isAttackClassifier = UtilsClssifiers.readModel(
              "Results/The one in the DB/BINARY/No noise/J48/isAttack/RF .model");
      Classifier ddosTypeClassifier = UtilsClssifiers.readModel(
              "Results/The one in the DB/BINARY/No noise/J48/DDoS type/RF .model");

      for (int i = 0; i < isAttackValidation.classAttribute().numValues(); i++) {
         System.out.println("Value " + i + " = " + isAttackValidation.classAttribute()
                 .value(i));
      }
      for (int i = 0; i < isAttackValidation.size(); i++) {

         String predictedValue = isAttackValidation.classAttribute().value(
                 (int) isAttackClassifier.classifyInstance(isAttackValidation.get(i)));
         String actualValue = isAttackValidation.get(i).stringValue(26);

         if (predictedValue.equalsIgnoreCase("attack")) {
            predictedValue = ddosTypeValidation.classAttribute().value(
                    (int) ddosTypeClassifier.classifyInstance(isAttackValidation.get(i)));
         }

         System.out.println(
                 i + ")\tPredictedValue: " + predictedValue
                 + "\t\tActualValue:\t" + actualValue
         );
      }
   }
//</editor-fold>

   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
//      system();
      GlobalFeatureExtraction.setInstance(
         new JanCNISDatabase(new NetmateExtraction())
//         new JanCNISDatabase(new NetmateTempExtraction())
      );
      final int instanceCount = ArffInstanceCount.HALVED;

      final FeatureSelection[] featureSelections = new FeatureSelection[]{
         NoFeatureSelection.getInstance(),
////         new InfoGainFS(),
//         new NBWrapper(),
//         new J48Wrapper()
      };
      final CategoricalType[] categoricalTypes = new CategoricalType[]{
         new GeneralAttackType(),
         new SpecificAttackType()
      };
      final NoiseLevel[] noiseLevels = new NoiseLevel[]{
//         new Noise(),
         NoNoise.getInstance()
      };

      for (FeatureSelection fs : featureSelections) {
         for (CategoricalType categoricalType : categoricalTypes) {
            for (NoiseLevel noiseLevel : noiseLevels) {
               systemTrain(
                  fs,
                  new SystemParameters.Builder(
                     instanceCount,
                     new Single (noiseLevel, categoricalType)
                  ).build()
               );
            }
         }
         for (NoiseLevel noiseLevel : noiseLevels) {
            systemTrain(
              fs,
              new SystemParameters.Builder(
                instanceCount,
                new HybridIsAttack(noiseLevel)
              ).build()
            );
         }
         for (CategoricalType categoricalType : categoricalTypes) {
            systemTrain(
               fs,
               new SystemParameters.Builder(
                  instanceCount,
                  new HybridDDoSType(categoricalType)
               ).build()
            );
         }
      }
   }

   private static void systemTrain(FeatureSelection fs, SystemParameters systemParameters)
           throws IOException, Exception {
      
      new SystemTrain.Buidler()
         .database(new Mysql())
         .featureSelection(fs)
         .build(systemParameters);

//      String fullFolderPath = String.join("/",
//         "Results",
//         "Jan dataset(Temp)",
//         GlobalFeatureExtraction.getInstance().getName(),
//         systemParameters.getCategoricalType().name(),
//         systemParameters.getNoiseLevelString(),
//         fs.getFSMethodName(),
//         systemParameters.getSystemType()
//      )+"/";
//      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, fullFolderPath);
   }
}
