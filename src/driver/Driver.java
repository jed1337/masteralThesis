package driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import setupFiles.SetupFile;
import setupFiles.SetupHighrate;
import setupFiles.SetupLowrate;
import setupFiles.SetupNoise;
import setupFiles.SetupNormal;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.core.Instances;

public class Driver{
   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      final String folderPath = "FeatureSelected (J48)/HLNormNoiseBinary/";
//      hybridMethod(folderPath);
      singleClassifier(folderPath);
//      system();
   }
   private static void hybridMethod(String folderPath) throws IOException, Exception{
      ArrayList<SetupFile> setupFiles;

      setupFiles= new ArrayList<>();
      setupFiles.add(new SetupNoise(3000));
      setupFiles.add(new SetupNormal(3000));
//      setupFiles.add(new SetupExtraNoise(2000));
      setupFiles.add(new SetupHighrate(3000));
      setupFiles.add(new SetupLowrate(3000));

      new SystemTrain(
         folderPath+"NormalOrAttack/",
         setupFiles,
         "isAttack",
         "tcpFlood:attack, udpFlood:attack, httpFlood:attack, slowBody:attack, slowHeaders:attack, slowRead:attack"
     );

     setupFiles = new ArrayList<>();
     setupFiles.add(new SetupHighrate(3000));
     setupFiles.add(new SetupLowrate(3000));

     new SystemTrain(
         folderPath+"HL/",
         setupFiles,
         "isAttack",
         "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate"
     );
   }

   public static void singleClassifier(String folderPath) throws IOException, Exception{
      ArrayList<SetupFile> setups = new ArrayList<>();
      setups.add(new SetupNoise(2000));
      setups.add(new SetupNormal(2000));
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
