package driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import setupFiles.SetupFile;
import setupFiles.SetupExtraNoise;
import setupFiles.SetupHighrate;
import setupFiles.SetupLowrate;
import setupFiles.SetupNoise;
import setupFiles.SetupNormal;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.core.Instances;

public class Driver{
   public static void main(String[] a1rgs) throws FileNotFoundException, IOException, Exception {
//      Instances reduced = FeatureSelection.wrapperSelection(
//         UtilsInstances.getInstances(
//            PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN
//         )
//      );
//
//      System.out.println("");

         final String folderPath = "FeatureSelected/HLNormNoiseBinary (Allah)/";
//      hybridMethod(folderPath);
      singleClassifier(folderPath);
//      system();
   }
//   private static void hybridMethod(String folderPath) throws IOException, Exception{
//      ArrayList<SetupFile> setupNormalOrAttack = new ArrayList<>();
////      setupNormalOrAttack.add(new SetupNoise(2000));
////      setupNormalOrAttack.add(new SetupNormal(2000));
////      setupNormalOrAttack.add(new SetupExtraNoise(2000));
//
//      setupNormalOrAttack.add(new SetupHighrate(3000));
//      setupNormalOrAttack.add(new SetupLowrate(3000));
//
//      new SystemTrain(
//         folderPath+"NormalOrAttack/",
//         setupNormalOrAttack,
//         getHashMap("highrate", "tcpFlood", "udpFlood", "httpFlood"),
//         getHashMap("lowrate", "slowBody", "slowHeaders", "slowRead"),
//         getHashMap("attack", "highrate", "lowrate"),
//
//         replaceAttribute("isAttack", "normal", "attack")
//     );
//
//     ArrayList<SetupFile> setupHL = new ArrayList<>();
//     setupHL.add(new SetupHighrate(3000));
//     setupHL.add(new SetupLowrate(3000));
//
//     new SystemTrain(
//         folderPath+"HL/",
//         setupHL,
//         getHashMap("highrate", "tcpFlood", "udpFlood", "httpFlood"),
//         getHashMap("lowrate", "slowBody", "slowHeaders", "slowRead"),
//
//         replaceAttribute("isAttack", "highrate", "lowrate")
////         replaceAttribute(
////            "isAttack",
////            "tcpFlood", "udpFlood", "httpFlood",
////            "slowBody", "slowHeaders", "slowRead")
//     );
//   }

   public static void singleClassifier(String folderPath) throws IOException, Exception{
      ArrayList<SetupFile> setups = new ArrayList<>();
      setups.add(new SetupNoise(1000));
      setups.add(new SetupNormal(1000));
      setups.add(new SetupExtraNoise(1000));
      setups.add(new SetupHighrate(3000));
      setups.add(new SetupLowrate(3000));
      
      new SystemTrain(
         folderPath + "Single/",
         setups,
         "isAttack",
         "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate"
      );
   }

   public static void system() throws IOException, Exception{
      Instances validation   = UtilsInstances.getInstances("Results/TestTrainValidation/NormalOrAttack/Validation.arff");
      Instances hlValidation = UtilsInstances.getInstances("Results/TestTrainValidation/HL/Validation.arff");
      Classifier normalClassifier = UtilsClssifiers.readModel("Results/TestTrainValidation/NormalOrAttack/KNN.model");
      Classifier hlClassifier     = UtilsClssifiers.readModel("Results/TestTrainValidation/HL/KNN.model");

      for(int i=0; i< validation.classAttribute().numValues(); i++){
         System.out.println("Value "+i+" = "+validation.classAttribute().value(i));
      }
      for (int i = 0; i < validation.size(); i++) {

         String predictedValue = validation.classAttribute().value((int)normalClassifier.classifyInstance(validation.get(i)));
         String actualValue = validation.get(i).stringValue(26);

         if(predictedValue.equalsIgnoreCase("attack")){
            predictedValue = hlValidation.classAttribute().value((int)hlClassifier.classifyInstance(validation.get(i)));
         }

         System.out.println(
            i+")\tPredictedValue: "+predictedValue
            +"\t\tActualValue:\t"+actualValue
         );
      }
   }
}
