package driver.liveSystem;

import constants.AttributeTypeConstants;
import driver.liveSystem.modelTest.ConnectModelTest;
import driver.liveSystem.modelTest.ModelTest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.core.Instances;

/**
 * Handles the logic of choosing which models to test given the arguments.
 * It manages the other live system controllers
 * It's implemented as a singleton since it just handles basic logic
 */
public final class LiveSystemController {
   private static LiveSystemController instance = null;

   private LiveSystemController() {}

   public static LiveSystemController getInstance(){
      if (instance == null){
         instance = new LiveSystemController();
      }
      return instance;
   }

   public void execute(String[] args) throws IOException, Exception{
      int aLength = args.length;

      //Default No arguments (Assumed to have ran from Netbeans)
      System.out.printf("Argument length = %d\n", aLength);
      if(aLength == 0){
         singleSystemTestDefault();
      }
      else{
         final String systemType = args[0].trim();
         final String rawPath = args[1];

         if(systemType.equalsIgnoreCase("single")){
            singleSystemTest(rawPath, args[2]);
         } else if (systemType.equalsIgnoreCase("hybrid")){
            hybridSystemTest(rawPath, args[2], args[3]);
         }
         else{
            throw new IllegalArgumentException(
               "Only valid argument lengths: 0 (Test run), 3 (Single CMD run), or 4 (Hybrid CMD run)"
            );
         }
      }
   }

   private void singleSystemTestDefault() throws Exception {
      String rawPath         = "Data/RawFiles/Final/Bi flow output/normal(Modified).arff";
      String singleModelPath = "Results/BiFlow/Train-Test/SPECIFIC/No noise/No feature selection/Single/RF .model";

      singleSystemTest(rawPath, singleModelPath);
   }

   private void singleSystemTest(String rawPath, String singleModelPath) throws Exception {
      System.out.println("Single system");

      ModelTest single = new ModelTest(
         "Single", UtilsInstances.getInstancesFromFile(rawPath),
         UtilsClssifiers.readModel(singleModelPath)
      );
      single.printClassAttribute();
      single.classifyAll();
   }

   private void hybridSystemTest(String rawPath, String isAttackClassifierPath, String ddosTypeClassifierPath) throws Exception {
      final Instances tsIsAttackFat = getFormattedInstances(
         rawPath,
         "normal", "attack"
      );

      final Instances tsDDoSType = getFormattedInstances(
         rawPath,
         "tcpFlood", "httpFlood", "slowRead", "slowHeaders", "udpFlood"
      );

      ModelTest hia = new ModelTest("Hybrid isAttack", tsIsAttackFat, UtilsClssifiers.readModel(isAttackClassifierPath));
      ModelTest hdt = new ModelTest("Hybrid DDoS type", tsDDoSType,    UtilsClssifiers.readModel(ddosTypeClassifierPath));

      hia.addConnectModel(new ConnectModelTest(hdt, "attack"));
      hia.printClassAttribute();
      hia.classifyAll();
      hia.classifyAll();
      hia.classifyAll();
      hia.classifyAll();
      hia.classifyAll();
      hia.printTotalClassDistribution();
   }

   private Instances getFormattedInstances(
      final String sourcePath, final String... attributeReplacements)
      throws IllegalArgumentException, IOException {

      String contents = Utils.getFileContents(sourcePath);
      final HashMap<String, String> toReplace = Utils.replaceAttribute(
         AttributeTypeConstants.ATTRIBUTE_CLASS, attributeReplacements);
      for (Map.Entry<String, String> entry : toReplace.entrySet()) {
         contents = contents.replaceAll(entry.getKey(), entry.getValue());
      }

      return UtilsInstances.getInstancesFromString(contents);
   }
}