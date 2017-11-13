package formatkddarff;

import classify.Classify;
import classify.CrossValidation;
import constants.PathConstants;
import format.FormatAsText;
import train.Train;
import train.TrainLowrate;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import train.TrainHighrate;
import train.TrainNoise;
import train.TrainNormal;
import utils.UtilsInstances;
import weka.core.Instances;

public class Thesis{
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
   
   private static FormatAsText fat;
   public static void main(String[] a1rgs) throws FileNotFoundException, IOException, Exception {
     ArrayList<Train> trainList = new ArrayList<>();
     trainList.add(new TrainNoise());
     trainList.add(new TrainNormal());
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

      String header = trainList.get(0).getFat().getArffHeader();
      fat = new FormatAsText(PathConstants.FORMATTED_DIR+"Train.arff");
      fat.clearFile();
      fat.insertString(header, 0);
      for (Train train : trainList) {
         fat.addInstances(train.getFaa().getSaveFileFullPath());
      }
      fat.addClassCount("isAttack");

//      open instances file at fat location
      Instances toSplit = UtilsInstances.getInstances(PathConstants.FORMATTED_DIR+"Train.arff");

//Put under TestTrainValidation(String folder, String path);
   //Gather all normal, highrate, lowrate into sepate Instances file
//         HashMap<FormatAsArff, String> singleClass = new HashMap<>();
//   //      singleClass.put(new FormatAsArff(new Instances(toSplit)), "Normal");
//         singleClass.put(new FormatAsArff(new Instances(toSplit)), "HIGHRATE");
//         singleClass.put(new FormatAsArff(new Instances(toSplit)), "Lowrate");
//
//         singleClass.forEach((faa, str)->{
//           try {
//              faa.removeNonMatchingClasses("isAttack", str);
//              Utils.writeFile("Only"+str+".arff", faa.getInstances().toString(), false);
//              HashMap<String, Float> splitParam = new HashMap<>();
//              splitParam.put("Only"+str+" Train.arff",      new Float(4.0));
//              splitParam.put("Only"+str+" Test.arff",       new Float(1.0));
//              splitParam.put("Only"+str+" Validation.arff", new Float(1.0));
//   //           faa.splitFile(6, str, 4, 1, 1);
//
//              faa.splitFile(6, splitParam);
//           } catch (IOException ex) {
//              System.err.println("eow");
//              Logger.getLogger(Thesis.class.getName()).log(Level.SEVERE, null, ex);
//           }
//         });
      

      Classify classify = new CrossValidation(
//         "2 Binary/HighrateLowrate/"
         "allahModified/"
         ,PathConstants.FORMATTED_DIR.toString()+"Train.arff"
        //PathConstants.FORMATTED_DIR.toString()+FileNameConstants.KDD_TEST_MINUS_21
      );
      classify.buildModel();
      classify.writeModel();
      classify.evaluateModel();
   }
}
