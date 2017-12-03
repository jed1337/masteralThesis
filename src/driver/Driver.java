package driver;

import constants.ArffInstanceCount;
import driver.systemConfiguration.ExtraNoise;
import driver.systemConfiguration.Hybrid;
import driver.systemConfiguration.Single;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import setupFiles.SetupExtraNoise;
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
      final String folderPath = "FeatureSelected (J48)/Halved/HL ExtraNoise Binary (test)/";
      new Hybrid(new ExtraNoise(), ArffInstanceCount.HALVED).execute(folderPath);
      new Single(new ExtraNoise(), ArffInstanceCount.HALVED).execute(folderPath);
      
//      system();
   }

   private static void hybridMethod(String folderPath) throws IOException, Exception{
      ArrayList<SetupFile> setupFiles;

      setupFiles= new ArrayList<>();
      setupFiles.add(new SetupNoise(1000));
      setupFiles.add(new SetupNormal(1000));
      setupFiles.add(new SetupExtraNoise(1000));
      setupFiles.add(new SetupHighrate(1500));
      setupFiles.add(new SetupLowrate(1500));

      new SystemTrain(
         folderPath+"NormalOrAttack/",
         setupFiles,
         "isAttack",
         "tcpFlood:attack, udpFlood:attack, httpFlood:attack, slowBody:attack, slowHeaders:attack, slowRead:attack"
     );

     setupFiles = new ArrayList<>();
     setupFiles.add(new SetupHighrate(1500));
     setupFiles.add(new SetupLowrate(1500));

     new SystemTrain(
         folderPath+"HL/",
         setupFiles,
         "isAttack",
//             ""
         "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate"
     );
   }

   public static void singleMethod(String folderPath) throws IOException, Exception{
      ArrayList<SetupFile> setupFiles = new ArrayList<>();
      setupFiles.add(new SetupNoise(1000));
      setupFiles.add(new SetupNormal(1000));
      setupFiles.add(new SetupExtraNoise(1000));
      setupFiles.add(new SetupHighrate(1500));
      setupFiles.add(new SetupLowrate(1500));

      new SystemTrain(
         folderPath + "Single/",
         setupFiles,
         "isAttack",
//              ""
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
