package driver;

import constants.ArffInstanceCount;
import constants.CategoricalTypeConstants;
import constants.PreprocessFileName;
import database.Mysql;
import driver.categoricalType.CategoricalType;
import driver.categoricalType.CustomInstanceDistributionCategoricalType;
import driver.mode.NormalVersusSpecificAttack;
import driver.mode.Single;
import driver.mode.noiseLevel.HalfNoise;
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
import java.util.EnumMap;
import utils.Utils;

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
         NoFeatureSelection.getInstance(),
         new InfoGainFS(),
         new CorrelationFS(),
         new NBWrapper(),
         new J48Wrapper()
      };

      final NoiseLevel[] noiseLevels = new NoiseLevel[]{
         new HalfNoise()
//         NoNoise.getInstance()
//         new MultiNoise(),
      };

//      final CategoricalType[] categoricalTypes = new CategoricalType[]{
//         // new GeneralAttackType(),
//         // new SpecificAttackType()
//        new CustomInstanceDistributionCategoricalType ("", CategoricalTypeConstants.SPECIFIC, fileDistributions)
//      };

      for (FeatureSelection fs : featureSelections) {
         final double totalNormal = 1.0;
         final int slices = 10;
         
         for (int i = 1; i < slices; i++) {
            double normalRatio      = i * (totalNormal/slices);
            double noiseNormalRatio = totalNormal-normalRatio;
            
            EnumMap<PreprocessFileName, Double> fileDistributions = new EnumMap<>(PreprocessFileName.class);

            Utils.addToMap(fileDistributions,PreprocessFileName.NORMAL,        0.5d);
            Utils.addToMap(fileDistributions,PreprocessFileName.NOISE_NORMAL,  0.5d);
            Utils.addToMap(fileDistributions,PreprocessFileName.TCP_FLOOD,     1d);
            Utils.addToMap(fileDistributions,PreprocessFileName.UDP_FLOOD,     1d);
            Utils.addToMap(fileDistributions,PreprocessFileName.HTTP_FLOOD,    1d);
            Utils.addToMap(fileDistributions,PreprocessFileName.SLOW_HEADERS,  1d);
            Utils.addToMap(fileDistributions,PreprocessFileName.SLOW_READ,     1d);

            CustomInstanceDistributionCategoricalType c = new CustomInstanceDistributionCategoricalType ("", CategoricalTypeConstants.SPECIFIC, fileDistributions);
            singleHybridTest(new CategoricalType[]{c}, noiseLevels, fs, instanceCount);
            System.out.println("");
         }
         
         
         
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
//      for (NoiseLevel noiseLevel : noiseLevels) {
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
