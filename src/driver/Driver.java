package driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import train.Train;
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
      ArrayList<Train> trainNormalOrAttack = new ArrayList<>();
      trainNormalOrAttack.add(new TrainNoise());
      trainNormalOrAttack.add(new TrainNormal());
      trainNormalOrAttack.add(new TrainHighrate());
      trainNormalOrAttack.add(new TrainLowrate());
     
      new SystemTrain(
         "Allah/NormalOrAttack/", 
         trainNormalOrAttack,
         getHashMap("highrate", "tcpFlood", "udpFlood", "httpFlood"),
         getHashMap("lowrate", "slowBody", "slowHeaders", "slowRead"),
//         getHashMap("attack", "highrate", "lowrate"),

//         replaceAttribute("isAttack", "normal", "attack")
         replaceAttribute("isAttack", "normal", "attack", "lowrate", "highrate")
     );
      
//     ArrayList<Train> trainHL = new ArrayList<>();
//     trainHL.add(new TrainHighrate());
//     trainHL.add(new TrainLowrate());
//     
//     new SystemTrain(
//         "Allah/HL/", 
//         trainHL,
//         getHashMap("highrate", "tcpFlood", "udpFlood", "httpFlood"),
//         getHashMap("lowrate", "slowBody", "slowHeaders", "slowRead"),
//
//         replaceAttribute("isAttack", "highrate", "lowrate")
//     );
//      system();
   }
   public static void system() throws IOException, Exception{
      Instances validation = UtilsInstances.getInstances("Results/TestTrainValidation/NormalOrAttack/Validation.arff");
      Instances hlValidation = UtilsInstances.getInstances("Results/TestTrainValidation/HL/Validation.arff");
      Classifier normalClassifier = UtilsClssifiers.readModel("Results/TestTrainValidation/NormalOrAttack/KNN.model");
      Classifier hlClassifier     = UtilsClssifiers.readModel("Results/TestTrainValidation/HL/KNN.model");
      
      for(int i=0; i< validation.classAttribute().numValues(); i++){
         System.out.println("Value "+i+" = "+validation.classAttribute().value(i));
      }
      for (int i = 0; i < validation.size(); i++) {
//         double label = classifier.classifyInstance(train.instance(i));
//         train.instance(i).setClassValue(label);
//
//         System.out.println(train.instance(i).stringValue(26));

         String predictedValue = validation.classAttribute().value((int)normalClassifier.classifyInstance(validation.get(i)));
//         String predictedValue = validation.classAttribute().value((int)hlClassifier.classifyInstance(validation.get(i)));
         String actualValue = validation.get(i).stringValue(26);
         
         if(predictedValue.equalsIgnoreCase("attack")){
            predictedValue = hlValidation.classAttribute().value((int)hlClassifier.classifyInstance(validation.get(i)));   
         }
         
         System.out.println(
            i+")\tPredictedValue: "+predictedValue
//            normalValidation.classAttribute().value((int)hlClassifier.classifyInstance(normalValidation.get(i)))+
            +"\t\tActualValue:\t"+actualValue
         );
      }
      
   }
}
