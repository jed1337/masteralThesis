package driver;

import constants.ArffInstanceCount;
import database.NoDatabase;
import driver.categoricalType.CategoricalType;
import driver.categoricalType.GeneralAttackType;
import driver.categoricalType.SpecificAttackType;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.Single;
import driver.mode.noiseLevel.HalfNoise;
import driver.mode.noiseLevel.NoiseLevel;
import driver.modeAdapter.NormalVersusSpecificAttack;
import evaluation.Classify;
import evaluation.TrainValidation;
import featureExtraction.BiFlowExtraction;
import featureExtraction.Decorator.FinalDatabase;
import featureSelection.FeatureSelection;
import featureSelection.NoFeatureSelection;
import featureSelection.filters.CorrelationFS;
import featureSelection.filters.InfoGainFS;
import featureSelection.wrappers.J48Wrapper;
import featureSelection.wrappers.NBWrapper;
import globalParameters.GlobalFeatureExtraction;
import java.io.FileNotFoundException;
import java.io.IOException;
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
         new FinalDatabase(new BiFlowExtraction())
//         new InitialDatabase(new KDDExtraction())
//         new Feb2CNISDatabase(new NetmateExtraction())
      );

      final int instanceCount = ArffInstanceCount.EIGHTEEN_K;
//      final int instanceCount = ArffInstanceCount.SIX_K;

      final FeatureSelection[] featureSelections = new FeatureSelection[]{
         NoFeatureSelection.getInstance(),
         new InfoGainFS(),
         new CorrelationFS(),
         new NBWrapper(),
         new J48Wrapper()
      };
      final CategoricalType[] categoricalTypes = new CategoricalType[]{
         new GeneralAttackType(),
         new SpecificAttackType()
      };
      final NoiseLevel[] noiseLevels = new NoiseLevel[]{
         new HalfNoise()
//         new MultiNoise(),
//         NoNoise.getInstance()
      };

      for (FeatureSelection fs : featureSelections) {
         singleHybridTest(categoricalTypes, noiseLevels, fs, instanceCount);
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs syn flood", new PreprocessTCPFlood()));
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs udp flood", new PreprocessUDPFlood()));
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs http flood", new PreprocessHTTPFlood()));
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow read", new PreprocessSlowRead()));
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow headers", new PreprocessSlowHeaders()));
      }
   }

   private static void normalVSOther(FeatureSelection fs, final int instanceCount, final NormalVersusSpecificAttack mode)
           throws Exception {
      systemTrain(
         fs,
         new SystemParameters(instanceCount, mode)
      );
   }

   /**
    * This function separates the training in the code.
    * This makes it easier to comment out
    * @param categoricalTypes
    * @param noiseLevels
    * @param fs
    * @param instanceCount
    * @throws Exception
    */
   private static void singleHybridTest(final CategoricalType[] categoricalTypes, final NoiseLevel[] noiseLevels, FeatureSelection fs, final int instanceCount)
           throws Exception {
      for (CategoricalType categoricalType : categoricalTypes) {
         for (NoiseLevel noiseLevel : noiseLevels) {
            systemTrain(
               fs,
               new SystemParameters(
                  instanceCount,
                  new Single (noiseLevel, categoricalType)
               )
            );
            System.out.println("");
         }
      }
      for (NoiseLevel noiseLevel : noiseLevels) {
         systemTrain(
            fs,
            new SystemParameters(
               instanceCount,
               new HybridIsAttack(noiseLevel)
            )
         );
      }
     for (CategoricalType categoricalType : categoricalTypes) {
        systemTrain(
            fs,
            new SystemParameters(
               instanceCount,
               new HybridDDoSType(categoricalType)
            )
        );
     }
   }

   private static void systemTrain(FeatureSelection fs, SystemParameters systemParameters)
         throws IOException, Exception {

      Classify classify = new TrainValidation();
//      Classify classify = new TrainTest();
//      Classify classify = new CrossValidation();

      new SystemTrain.Buidler()
//         .database(new Mysql())
         .database(NoDatabase.getInstance())
         .featureSelection(fs)
         .evaluation(classify)
         .build(systemParameters);

//      String fullFolderPath = String.join("/",
//         "Results",
//         GlobalFeatureExtraction.getInstance().getDatasetName(),
//         GlobalFeatureExtraction.getInstance().getName(),
//         classify.getType(),
//         systemParameters.getCategoricalType().name(),
//         systemParameters.getNoiseLevelString(),
//         fs.getFSMethodName(),
//         systemParameters.getSystemType()
//      )+"/";

//      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, fullFolderPath);
   }
}
