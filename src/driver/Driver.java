package driver;

import constants.ArffInstanceCount;
import constants.CategoricalTypeConstants;
import constants.PreprocessFileName;
import database.Mysql;
import driver.categoricalType.CategoricalType;
import driver.categoricalType.CustomInstanceDistributionCategoricalType;
import driver.mode.NormalVersusSpecificAttack;
import driver.mode.Single;
import driver.mode.noiseLevel.MultiNoise;
import evaluation.Evaluation;
import evaluation.TrainTest;
import featureExtraction.BiFlowExtraction;
import featureExtraction.Decorator.FinalDatabase;
import featureSelection.FeatureSelection;
import featureSelection.NoFeatureSelection;
import globalParameters.GlobalFeatureExtraction;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.EnumMap;
import utils.Utils;
import driver.mode.noiseLevel.NoiseDataset;

public final class Driver {
   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      GlobalFeatureExtraction.setInstance(
         new FinalDatabase(new BiFlowExtraction())
      );

//<editor-fold defaultstate="collapsed" desc="live system">
//      String[] params = {
//         "hybrid",
////         "Data/RawFiles/Final/Bi flow output/normal(Modified)(test).arff",
//         "Data/RawFiles/Final/Bi flow output/normal(Modified).arff",
//         "Results/BiFlow/Train-Test/GENERAL/No noise/No feature selection/Hybrid isAttack/RF .model",
//         "Results/BiFlow/Train-Test/SPECIFIC/No noise/No feature selection/Hybrid DDoS Type/nb .model"
//      };
//
//      LiveSystemController.getInstance().execute(args);
//</editor-fold>

      final int instanceCount = ArffInstanceCount.EIGHTEEN_K;

      final FeatureSelection[] featureSelections = new FeatureSelection[]{
         NoFeatureSelection.getInstance()
//         new InfoGainFS(),
//         new CorrelationFS(),
//         new NBWrapper(),
//         new J48Wrapper()
      };

      final NoiseDataset[] noiseLevels = new NoiseDataset[]{
         new MultiNoise()
//         new HalfNoise()
//         NoNoise.getInstance()
      };

//      final CategoricalType[] categoricalTypes = new CategoricalType[]{
//         // new GeneralAttackType(),
//         // new SpecificAttackType()
//        new CustomInstanceDistributionCategoricalType ("", CategoricalTypeConstants.SPECIFIC, fileDistributions)
//      };

      final PreprocessFileName[] noiseNames = {
         PreprocessFileName.NOISE_NORMAL,
         PreprocessFileName.NOISE_TCP_FLOOD,
         PreprocessFileName.NOISE_UDP_FLOOD,
         PreprocessFileName.NOISE_HTTP_FLOOD,
         PreprocessFileName.NOISE_SLOW_HEADERS,
         PreprocessFileName.NOISE_SLOW_READ
      };

      for (FeatureSelection fs : featureSelections) {
         final double totalNormal = 1.0;
         final int slices = 10;
         
         for (int i = 1; i < slices; i++) {
            final double normalRatio      = i * (totalNormal/slices);
            final double overallNoiseNormalRatio = totalNormal-normalRatio;

            final double noiseNormalRatio = overallNoiseNormalRatio/noiseNames.length;
            final EnumMap<PreprocessFileName, Double> fileDistributions = new EnumMap<>(PreprocessFileName.class);

            Utils.addToMap(fileDistributions,PreprocessFileName.NORMAL,            normalRatio);
            for (PreprocessFileName noiseName : noiseNames) {
               Utils.addToMap(fileDistributions,noiseName, noiseNormalRatio);
            }
            
            Utils.addToMap(fileDistributions,PreprocessFileName.TCP_FLOOD,     1d);
            Utils.addToMap(fileDistributions,PreprocessFileName.UDP_FLOOD,     1d);
            Utils.addToMap(fileDistributions,PreprocessFileName.HTTP_FLOOD,    1d);
            Utils.addToMap(fileDistributions,PreprocessFileName.SLOW_HEADERS,  1d);
            Utils.addToMap(fileDistributions,PreprocessFileName.SLOW_READ,     1d);

            CustomInstanceDistributionCategoricalType c = new CustomInstanceDistributionCategoricalType ("", CategoricalTypeConstants.SPECIFIC, fileDistributions);
            systemTrain(new CategoricalType[]{c}, noiseLevels, fs, instanceCount);
         }
         System.out.println("");



//         singleHybridTest(categoricalTypes, noiseLevels, fs, instanceCount);
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
   private static void systemTrain(final CategoricalType[] categoricalTypes, final NoiseDataset[] noiseLevels, FeatureSelection fs, final int instanceCount)
           throws Exception {
      for (CategoricalType categoricalType : categoricalTypes) {
         for (NoiseDataset noiseLevel : noiseLevels) {
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
//      for (NoiseDataset noiseLevel : noiseLevels) {
//         systemTrain(
//            fs,
//            new SystemParameters(
//               instanceCount,
//               new HybridIsAttack(noiseLevel)
//            )
//         );
//      }
//     for (CategoricalType categoricalType : categoricalTypes) {
//        systemTrain(
//            fs,
//            new SystemParameters(
//               instanceCount,
//               new HybridDDoSType(categoricalType)
//            )
//        );
//     }
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
