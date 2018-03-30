package driver;

import database.NoDatabase;
import driver.categoricalType.CategoricalType;
import driver.mode.HybridDDoSType;
import driver.mode.HybridIsAttack;
import driver.mode.NormalVersusSpecificAttack;
import driver.mode.Single;
import driver.mode.noiseLevel.NoiseLevel;
import driver.modelTest.ConnectModelTest;
import driver.modelTest.ModelTest;
import evaluation.Classify;
import evaluation.TrainTest;
import featureExtraction.BiFlowExtraction;
import featureExtraction.Decorator.FinalDatabase;
import featureSelection.FeatureSelection;
import globalParameters.GlobalFeatureExtraction;
import java.io.FileNotFoundException;
import java.io.IOException;
import preprocessFiles.preprocessAs.FormatAsText;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public final class Driver {
   public static void system(String[] args) throws IOException, IllegalArgumentException, Exception{
      String rawPath = "";
      String modelPath = "";

//    This switch statement is here so that we don't repeatedly comment and uncomment stuff depending on
//    if we're testing some configurations, or running the jar file as is through the CMD
      switch (args.length) {
         case 2:
            rawPath   = args[0];
            modelPath = args[1];
            break;
         case 0:
            rawPath = "Data/RawFiles/Final/Bi flow output/normal(Modified).arff";
            modelPath = "Results/BiFlow/Train-Test/SPECIFIC/No noise/No feature selection/Single/RF .model";
            break;
         default:
            throw new IllegalArgumentException("Only valid arguments: 2 (CMD run), or 0 (Test run)");
      }

      ModelTest smt = new ModelTest(
         UtilsInstances.getInstances(rawPath), 
         UtilsClssifiers.readModel(modelPath)
      );
      smt.printClassAttribute();
      smt.classify();
   }
   
   public static void systemHybrid() throws IOException, Exception{
      final String origArffPath = 
         "Data/RawFiles/Final/Bi flow output/normal(Modified).arff";
      
      final Classifier isAttackClassifier = UtilsClssifiers.readModel(
         "Results/BiFlow/Train-Test/GENERAL/No noise/No feature selection/Hybrid isAttack/RF .model");
      final Classifier ddosTypeClassifier = UtilsClssifiers.readModel(
         "Results/BiFlow/Train-Test/SPECIFIC/No noise/No feature selection/Hybrid DDoS Type/smo.model");
      
      final String modelTestPath = "modelTest/";
      final Instances tsIsAttackFat = getFormattedInstances(
         origArffPath, 
         modelTestPath+"isAttackTest.arff", 
         "normal", "attack"
      );
      
      final Instances tsDDoSType = getFormattedInstances(
         origArffPath, 
         modelTestPath+"ddosTypeTest.arff", 
         "tcpFlood", "httpFlood", "slowRead", "slowHeaders", "udpFlood"
      );
      
      ModelTest hia = new ModelTest(tsIsAttackFat,isAttackClassifier);
      
		ModelTest hdt = new ModelTest(tsDDoSType, ddosTypeClassifier);

      hia.addConnectModel(new ConnectModelTest(hdt, "attack"));
      hia.printClassAttribute();
      hia.classify();
   }

   private static Instances getFormattedInstances(
      final String sourcePath, final String modificationPath, final String... attributeReplacements)
      throws IllegalArgumentException, IOException {
      
      final FormatAsText fat = new FormatAsText(modificationPath);
      
      Utils.writeStringFile(modificationPath, "");
      fat.addInstances(sourcePath);
      fat.replaceAllStrings(
         Utils.replaceAttribute(
            UtilsInstances.getClassAttributeName(modificationPath), attributeReplacements
         )
      );
      return UtilsInstances.getInstances(fat.getPATH());
   }

//<editor-fold defaultstate="collapsed" desc="OldSystem">
   public static void oldSystem() throws IOException, Exception {
      /** Is Attack Validation Instances*/
      Instances IAVInstances = UtilsInstances.getInstances(
         "Results/BiFlow/Train-Test/GENERAL/No noise/No feature selection/Hybrid isAttack/ValidationQuestion.arff");
      
      /** DDoS Type Instances*/
      Instances DTVInstances = UtilsInstances.getInstances(
         "Results/BiFlow/Train-Test/SPECIFIC/No noise/No feature selection/Hybrid DDoS Type/ValidationQuestion.arff");

      // Instances incompatible = UtilsInstances.getInstances(
              // "Results/The one in the DB/NOMINAL/No noise/J48/DDoS type/Validation.arff");

      Classifier isAttackClassifier = UtilsClssifiers.readModel(
              "Results/BiFlow/Train-Test/GENERAL/No noise/No feature selection/Hybrid isAttack/RF .model");
      Classifier ddosTypeClassifier = UtilsClssifiers.readModel(
              "Results/BiFlow/Train-Test/SPECIFIC/No noise/No feature selection/Hybrid DDoS Type/smo.model");
//              "Results/BiFlow/Train-Test/SPECIFIC/No noise/No feature selection/Hybrid DDoS Type/RF .model");

      for (int i = 0; i < IAVInstances.classAttribute().numValues(); i++) {
         System.out.println(String.format("Value: %d = %s", i, IAVInstances.classAttribute().value(i)));
//         System.out.println("Value " + i + " = " + isAttackValidation.classAttribute().value(i));
      }
      
//      final int classAttributeIndex   = IAVInstances.classAttribute().index();
//      for (int i = 0; i < IAVInstances.size(); i++) {
//         final Instance IAVInstance = IAVInstances.get(i);
//         String actualValue = IAVInstance.stringValue(classAttributeIndex);
//         String predictedValue = IAVInstances.classAttribute().value((int) isAttackClassifier.classifyInstance(IAVInstance));
//
//         if (predictedValue.equalsIgnoreCase("attack")) {
//            predictedValue = DTVInstances.classAttribute().value((int) ddosTypeClassifier.classifyInstance(IAVInstance));
//         }
//         
//         System.out.println(
//            String.format(
//               "%d\tPredicted value: %s, \t Actual value: %s",
//               i,
//               predictedValue,
//               actualValue
//            )
//         );
//      }
      classifyAllInstances(isAttackClassifier, IAVInstances);
      System.out.println("asdf");
      classifyAllInstances(ddosTypeClassifier, DTVInstances);
   }
   
   private static void classifyAllInstances(Classifier model, Instances instances) throws Exception{
      final int classAttributeIndex   = instances.classAttribute().index();
      for (int i = 0; i < instances.size(); i++) {
         final Instance instance = instances.get(i);
         String actualValue = instance.stringValue(classAttributeIndex);
         String predictedValue = instances.classAttribute().value((int) model.classifyInstance(instance));

         System.out.println(
            String.format(
               "%d\tPredicted value: %s, \t Actual value: %s",
               i,
               predictedValue,
               actualValue
            )
         );
      }
   }
   
   private static String getPredictedClass(Attribute classAttributes, Classifier classifier, Instance toClassify) throws Exception{
      return classAttributes.value((int) classifier.classifyInstance(toClassify));
   }
//</editor-fold>

   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      GlobalFeatureExtraction.setInstance(
         new FinalDatabase(new BiFlowExtraction())
      );
//      system(args);
      systemHybrid();

//
//      final int instanceCount = ArffInstanceCount.EIGHTEEN_K;
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
////         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs http flood", new PreprocessHTTPFlood()));
////         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow read", new PreprocessSlowRead()));
////         normalVSOther(fs, instanceCount, new NormalVersusSpecificAttack("Normal vs slow headers", new PreprocessSlowHeaders()));
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
      Classify classify = new TrainTest();
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
