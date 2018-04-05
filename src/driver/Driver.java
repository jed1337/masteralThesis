package driver;

import constants.ArffInstanceCount;
import database.Mysql;
import driver.categoricalType.CategoricalType;
import driver.categoricalType.GeneralAttackType;
import driver.categoricalType.SpecificAttackType;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.NormalVersusSpecificAttack;
import driver.mode.Single;
import driver.mode.noiseLevel.HalfNoise;
import driver.mode.noiseLevel.NoNoise;
import driver.mode.noiseLevel.NoiseLevel;
import evaluation.Evaluation;
import evaluation.TrainTest;
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
import preprocessFiles.PreprocessHTTPFlood;
import preprocessFiles.PreprocessSlowHeaders;
import preprocessFiles.PreprocessSlowRead;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;

public final class Driver {
   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      GlobalFeatureExtraction.setInstance(
         new FinalDatabase(new BiFlowExtraction())
      );
      
//      String[] params = {
//         "hybrid",
////         "Data/RawFiles/Final/Bi flow output/normal(Modified)(test).arff",
//         "Data/RawFiles/Final/Bi flow output/normal(Modified).arff",
//         "Results/BiFlow/Train-Test/GENERAL/No noise/No feature selection/Hybrid isAttack/RF .model",
//         "Results/BiFlow/Train-Test/SPECIFIC/No noise/No feature selection/Hybrid DDoS Type/nb .model"
//      };
//
//      LiveSystemController.getInstance().execute(args);

      final int instanceCount = ArffInstanceCount.EIGHTEEN_K;

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
         new HalfNoise(),
         NoNoise.getInstance()
//         new MultiNoise(),
      };

      for (FeatureSelection fs : featureSelections) {
         singleHybridTest(categoricalTypes, noiseLevels, fs, instanceCount);
         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs syn flood", new PreprocessTCPFlood()));
         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs udp flood", new PreprocessUDPFlood()));
         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs http flood", new PreprocessHTTPFlood()));
         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow read", new PreprocessSlowRead()));
         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow headers", new PreprocessSlowHeaders()));
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

      Evaluation classify = new TrainTest();

      new SystemTrain.Buidler()
         .database(new Mysql())
//         .database(NoDatabase.getInstance())
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
