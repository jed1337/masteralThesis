package driver;

import classify.Classify;
import classify.TempHolder;
import classify.TrainTestValidation;
import constants.FileNameConstants;
import constants.PathConstants;
import train.Train;
import train.TrainLowrate;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import train.TrainHighrate;
import train.TrainNoise;
import train.TrainNormal;
import utils.Utils;

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
     ArrayList<Train> trainList = new ArrayList<>();
//     trainList.add(new TrainNoise());
//     trainList.add(new TrainNormal());
     trainList.add(new TrainHighrate());
     trainList.add(new TrainLowrate());

      for (Train train : trainList) {
         train.setup();
         train.writeFile();
         train.replaceAllStrings(
            getHashMap("highrate", "tcpFlood", "udpFlood", "httpFlood"),
            getHashMap("lowrate", "slowBody", "slowHeaders", "slowRead"),
//            getHashMap("attack", "highrate", "lowrate"),

            replaceAttribute("isAttack", "highrate", "lowrate")
//            replaceAttribute("isAttack", "normal", "attack")
//            replaceAttribute("isAttack", "neptune", "lowrate", "normal")
         );
      }

      Utils.createArff(
         PathConstants.FORMATTED_DIR+FileNameConstants.COMBINED,
         trainList.stream().
            map(tl->tl.getFaa().getSavePath())
            .collect(Collectors.toList())
      );

      TempHolder th = new TempHolder(PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN);
      th.setTrainTestValidation(
         PathConstants.FORMATTED_DIR+FileNameConstants.COMBINED,
         PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN,
         PathConstants.FORMATTED_DIR+FileNameConstants.TEST,
         PathConstants.FORMATTED_DIR+FileNameConstants.VALIDATION
      );

      Classify classify = new TrainTestValidation(
         "2 Binary/HighrateLowrate/",
         "allahmodified/",
         PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN,
         PathConstants.FORMATTED_DIR+FileNameConstants.TEST,
         PathConstants.FORMATTED_DIR+FileNameConstants.VALIDATION
      );
      classify.buildModel();
      classify.writeModel();
      classify.evaluateModel();
   }
}
