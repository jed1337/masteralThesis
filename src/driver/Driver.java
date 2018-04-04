package driver;

import constants.CategoricalTypeConstants;
import constants.PreprocessFileName;
import database.Mysql;
import driver.categoricalType.CategoricalType;
import driver.categoricalType.CustomInstanceDistributionCategoricalType;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.NormalVersusSpecificAttack;
import driver.mode.Single;
import driver.mode.noiseLevel.NoiseLevel;
import evaluation.TrainTest;
import featureExtraction.BiFlowExtraction;
import featureExtraction.Decorator.FinalDatabase;
import featureSelection.FeatureSelection;
import globalParameters.GlobalFeatureExtraction;
import java.io.FileNotFoundException;
import java.io.IOException;
import evaluation.Evaluation;
import java.util.ArrayList;
import java.util.EnumMap;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessTCPFlood;
import preprocessFiles.PreprocessUDPFlood;
import utils.Utils;

public final class Driver {
   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      GlobalFeatureExtraction.setInstance(
         new FinalDatabase(new BiFlowExtraction())
      );
      
      ArrayList<PreprocessFile> pfAl = new ArrayList<>();
      pfAl.add(new PreprocessTCPFlood());
      pfAl.add(new PreprocessUDPFlood());
      
      EnumMap<PreprocessFileName, Double> distributions = new EnumMap<>(PreprocessFileName.class);
      Utils.addToMap(distributions, PreprocessFileName.TCP_FLOOD, 1.0);
      Utils.addToMap(distributions, PreprocessFileName.UDP_FLOOD, 3.0);
      
      CustomInstanceDistributionCategoricalType cid = new CustomInstanceDistributionCategoricalType(
         "",
         CategoricalTypeConstants.SPECIFIC,
         distributions
      );
      
      cid.setPreprocessFileCount(pfAl, 1000);
      System.out.println("");
////      String[] params = {
////         "hybrid",
//////         "Data/RawFiles/Final/Bi flow output/normal(Modified)(test).arff",
////         "Data/RawFiles/Final/Bi flow output/normal(Modified).arff",
////         "Results/BiFlow/Train-Test/GENERAL/No noise/No feature selection/Hybrid isAttack/RF .model",
////         "Results/BiFlow/Train-Test/SPECIFIC/No noise/No feature selection/Hybrid DDoS Type/nb .model"
////      };
////
////      LiveSystemController.getInstance().execute(args);
//
////      final int instanceCount = ArffInstanceCount.EIGHTEEN_K;
//      final int instanceCount = ArffInstanceCount.SIX_K;
//
//      final FeatureSelection[] featureSelections = new FeatureSelection[]{
//         NoFeatureSelection.getInstance(),
//         new InfoGainFS(),
//         new CorrelationFS(),
//         new NBWrapper(),
//         new J48Wrapper()
//      };
//      final CategoricalType[] categoricalTypes = new CategoricalType[]{
//         new GeneralAttackType(),
//         new SpecificAttackType()
//      };
//      final NoiseLevel[] noiseLevels = new NoiseLevel[]{
//         new HalfNoise()
////         new MultiNoise(),
////         NoNoise.getInstance()
//      };
//
//      for (FeatureSelection fs : featureSelections) {
////         singleHybridTest(categoricalTypes, noiseLevels, fs, instanceCount);
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs syn flood", new PreprocessTCPFlood()));
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs udp flood", new PreprocessUDPFlood()));
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs http flood", new PreprocessHTTPFlood()));
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow read", new PreprocessSlowRead()));
//         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow headers", new PreprocessSlowHeaders()));
//      }
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

//      Classify classify = new TrainValidation();
      Evaluation classify = new TrainTest();
//      Classify classify = new CrossValidation();

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
