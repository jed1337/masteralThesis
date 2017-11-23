package driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import train.Train;
import train.TrainExtraNoise;
import train.TrainHighrate;
import train.TrainLowrate;
import train.TrainNoise;
import train.TrainNormal;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.core.Instances;

public class Driver{
//<editor-fold defaultstate="collapsed" desc="Functions">
   private static HashMap<String, String> replaceAttribute(String attribute, String... toReplace){
      return replaceAttribute(attribute, String.join(",",toReplace));
   }

   private static HashMap<String, String> replaceAttribute(String attribute, String toReplace){
      final HashMap<String, String> OTHER_REPLACEMENTS = new HashMap<>();
      OTHER_REPLACEMENTS.put(
              "(?m)^@attribute "+attribute+".*",
         "@attribute "+attribute+" {"+toReplace+"}");

      return OTHER_REPLACEMENTS;
   }

   private static HashMap<String, String> getHashMap(String value, String... keys) {
      return getHashMap(value, Arrays.asList(keys));
   }

   private static HashMap<String, String> getHashMap(String value, List<String> keys) {
      HashMap<String, String> hm = new HashMap();
      keys.forEach((key)->{
         hm.put(key, value);
      });
      return hm;
   }
//</editor-fold>

   public static void main(String[] a1rgs) throws FileNotFoundException, IOException, Exception {
      final String folderPath = "ExtraNoise/HLNormNoiseNominal/";
      hybridMethod(folderPath);
      singleClassifier(folderPath);
//      system();
   }
   private static void hybridMethod(String folderPath) throws IOException, Exception{
      ArrayList<Train> trainNormalOrAttack = new ArrayList<>();
      trainNormalOrAttack.add(new TrainNoise(2000));
      trainNormalOrAttack.add(new TrainNormal(2000));
      trainNormalOrAttack.add(new TrainExtraNoise(2000));
      
      trainNormalOrAttack.add(new TrainHighrate(3000));
      trainNormalOrAttack.add(new TrainLowrate(3000));
     
      new SystemTrain(
         folderPath+"NormalOrAttack/", 
         trainNormalOrAttack,
         getHashMap("highrate", "tcpFlood", "udpFlood", "httpFlood"),
         getHashMap("lowrate", "slowBody", "slowHeaders", "slowRead"),
         getHashMap("attack", "highrate", "lowrate"),

         replaceAttribute("isAttack", "normal", "attack")
     );
      
     ArrayList<Train> trainHL = new ArrayList<>();
     trainHL.add(new TrainHighrate(3000));
     trainHL.add(new TrainLowrate(3000));
     
     new SystemTrain(
         folderPath+"HL/", 
         trainHL,
//         getHashMap("highrate", "tcpFlood", "udpFlood", "httpFlood"),
//         getHashMap("lowrate", "slowBody", "slowHeaders", "slowRead"),

//         replaceAttribute("isAttack", "highrate", "lowrate")
         replaceAttribute(
            "isAttack",
            "tcpFlood", "udpFlood", "httpFlood",
            "slowBody", "slowHeaders", "slowRead")
     );
   }

   public static void singleClassifier(String folderPath) throws IOException, Exception{
      ArrayList<Train> train = new ArrayList<>();
      train.add(new TrainNoise(1000));
      train.add(new TrainNormal(1000));
      train.add(new TrainExtraNoise(1000));
      train.add(new TrainHighrate(3000));
      train.add(new TrainLowrate(3000));

      new SystemTrain(
         folderPath + "Single/",
         train,
//         getHashMap("highrate", "tcpFlood", "udpFlood", "httpFlood"),
//         getHashMap("lowrate", "slowBody", "slowHeaders", "slowRead"),
//         replaceAttribute("isAttack", "normal", "highrate", "lowrate")
         replaceAttribute(
            "isAttack", 
            "normal", 
            "tcpFlood", "udpFlood", "httpFlood", 
            "slowBody", "slowHeaders", "slowRead")
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
