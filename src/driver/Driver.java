package driver;

import constants.ArffInstanceCount;
import constants.CategoricalTypeConstants;
import constants.DirectoryConstants;
import constants.PreprocessFileName;
import database.Mysql;
import driver.categoricalType.CategoricalType;
import driver.categoricalType.CustomInstanceDistributionCategoricalType;
import driver.categoricalType.GeneralAttackType;
import driver.categoricalType.SpecificAttackType;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.Single;
import driver.mode.noiseLevel.MultiNoise;
import driver.mode.noiseLevel.Noise1;
import driver.mode.noiseLevel.NoiseDataset;
import driver.modeAdapter.NormalVersusSpecificAttack;
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
import preprocessFiles.PreprocessFile;
import utils.Utils;

public final class Driver {
   public static void main(String[] args) throws FileNotFoundException,IOException, Exception {
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
      };

      final CategoricalType[] categoricalTypes = new CategoricalType[]{
         new GeneralAttackType(),
         new SpecificAttackType()
      };

      final NoiseDataset[] noiseLevels = new NoiseDataset[]{
//         NoNoise.getInstance(),
         new Noise1(),
         new MultiNoise()
      };
      
//      for (FeatureSelection fs : featureSelections) {
//         singleHybridTest(categoricalTypes, noiseLevels, fs, instanceCount);
////<editor-fold defaultstate="collapsed" desc="NormalVsOther">
////         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs syn flood", new PreprocessTCPFlood()));
////         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs udp flood", new PreprocessUDPFlood()));
////         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs http flood", new PreprocessHTTPFlood()));
////         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow read", new PreprocessSlowRead()));
////         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow headers", new PreprocessSlowHeaders()));
////</editor-fold>
//      }

      noiseRatioTest(featureSelections, noiseLevels, 1.0 ,instanceCount);
   }
//<editor-fold defaultstate="collapsed" desc="Noise ratio and normalVsOther">

   private static void noiseRatioTest(
      final FeatureSelection[] featureSelections, final NoiseDataset[] noiseLevels, final double notNormalRatio, final int instanceCount)
      throws Exception {

      final double totalNormal = 1.0;
      final int slices = 10;

      for (NoiseDataset noiseLevel : noiseLevels) {
         //Create a PreprocessFileName[] from the files inside the passed noiseLevel
         //There's no guarantee that the files here are all noise since it depends the value in noiseLevel
         final PreprocessFileName[] extractedNoiseNames = noiseLevel.getPreprocessFiles().stream()
            .map(nl->nl.getPreprocessFileName())
            .toArray(PreprocessFileName[]::new);
         
         for (FeatureSelection fs : featureSelections) {
            for (int i = 1; i < slices; i++) {
               final double normalRatio = i * (totalNormal / slices);
               final double overallNoiseNormalRatio = totalNormal - normalRatio;

               final double noiseNormalRatio = overallNoiseNormalRatio / extractedNoiseNames.length;
               
               final EnumMap<PreprocessFileName, Double> fileDistributions = new EnumMap<>(PreprocessFileName.class);
               Utils.addToMap(fileDistributions, PreprocessFileName.TCP_FLOOD, notNormalRatio);
               Utils.addToMap(fileDistributions, PreprocessFileName.UDP_FLOOD, notNormalRatio);
               Utils.addToMap(fileDistributions, PreprocessFileName.HTTP_FLOOD, notNormalRatio);
               Utils.addToMap(fileDistributions, PreprocessFileName.SLOW_HEADERS, notNormalRatio);
               Utils.addToMap(fileDistributions, PreprocessFileName.SLOW_READ, notNormalRatio);

               Utils.addToMap(fileDistributions, PreprocessFileName.NORMAL, normalRatio);
               for (PreprocessFileName noiseName : extractedNoiseNames) {
                  Utils.addToMap(fileDistributions, noiseName, noiseNormalRatio);
               }

//               CustomInstanceDistributionCategoricalType customInstanceDistribution = new CustomInstanceDistributionCategoricalType(
//                  "normal:normal, normal:normal, tcpFlood:attack, udpFlood:attack, httpFlood:attack, slowHeaders:attack, slowRead:attack",
//                  CategoricalTypeConstants.GENERAL,
//                  fileDistributions
//               );

               CustomInstanceDistributionCategoricalType customInstanceDistribution = new CustomInstanceDistributionCategoricalType(
                  "",
                  CategoricalTypeConstants.SPECIFIC,
                  fileDistributions
               );

               systemTrain(
                  fs,
                  new SystemParameters(
                     instanceCount,
//                     new Single(noiseLevel, customInstanceDistribution)
                     new HybridIsAttack(noiseLevel, customInstanceDistribution)
                  )
               );
               System.out.println("");
            }
         }
      }
   }

   private static void normalVSOther(FeatureSelection fs, final int instanceCount, final NormalVersusSpecificAttack mode)
      throws Exception {
      systemTrain(
         fs,
         new SystemParameters(instanceCount, mode)
      );
   }
//</editor-fold>

   /**
    * This function separates the training in the code.
    * This makes it easier to comment out
    *
    * @param categoricalTypes
    * @param noiseLevels
    * @param fs
    * @param instanceCount
    *
    * @throws Exception
    */
   private static void singleHybridTest(final CategoricalType[] categoricalTypes, final NoiseDataset[] noiseLevels, FeatureSelection fs, final int instanceCount)
      throws Exception {
      for (CategoricalType categoricalType : categoricalTypes) {
         for (NoiseDataset noiseLevel : noiseLevels) {
            systemTrain(
               fs,
               new SystemParameters(
                  instanceCount,
                  new Single(noiseLevel, categoricalType)
               // new HybridIsAttack(noiseLevel, categoricalType)
               )
            );
            System.out.println("");
         }
      }
      for (NoiseDataset noiseLevel : noiseLevels) {
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
//      .database(NoDatabase.getInstance())
      .featureSelection(fs)
      .evaluation(classify)
      .build(systemParameters);

//      writeToFile(classify, systemParameters, fs);
   }

   private static void writeToFile(Evaluation classify, SystemParameters systemParameters, FeatureSelection fs)
      throws IOException {

      String fullFolderPath = String.join("/",
         "Results",
         GlobalFeatureExtraction.getInstance().getDatasetName(),
         GlobalFeatureExtraction.getInstance().getName(),
         (PreprocessFile.RANDOM_SEED+""),
         classify.getType(),
         systemParameters.getCategoricalType().name(),
         systemParameters.getNoiseDatasetName().toString(),
         fs.getFSMethodName(),
         systemParameters.getSystemType()
      )+"/";
      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, fullFolderPath);
   }
}
