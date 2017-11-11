package formatkddarff;

import classify.Classify;
import classify.CrossValidation;
import constants.PathConstants;
import format.FormatAsText;
import train.Train;
import train.TrainLowrate;
import train.TrainNoise;
import train.TrainNormal;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Thesis{
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

   private static FormatAsText fat;
   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
     ArrayList<Train> trainList = new ArrayList<>();
     trainList.add(new TrainNoise());
     trainList.add(new TrainNormal());
//     trainList.add(new TrainHighrate());
     trainList.add(new TrainLowrate());
     
      for (Train train : trainList) {
         train.setup();
         train.writeFile();
         train.replaceAllStrings(
            getHashMap("neptune", "tcpFlood", "udpFlood", "httpFlood"),
            getHashMap("lowrate", "slowBody", "slowHeaders", "slowRead"),

            replaceAttribute("isAttack", "neptune", "lowrate", "normal")
         );
      }

     String header = trainList.get(0).getFat().getArffHeader();

      fat = new FormatAsText(PathConstants.FORMATTED_DIR+"Train.arff");
      fat.clearFile();
      fat.insertString(header, 0);
      for (Train train : trainList) {
         fat.addInstances(train.getFaa().getSaveFileFullPath());
      }
      
      fat.addClassCount("isAttack");

      Classify classify = new CrossValidation("allahModified/"
              ,PathConstants.FORMATTED_DIR.toString()+"Train.arff"
//              ,PathConstants.FORMATTED_DIR.toString()+FileNameConstants.KDD_TEST_MINUS_21
      );
      classify.buildModel();
      classify.evaluateModel();
   }
}
