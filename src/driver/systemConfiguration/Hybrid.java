package driver.systemConfiguration;

import constants.AttributeTypeConstants;
import driver.SystemTrain;
import java.io.IOException;
import weka.classifiers.Classifier;

public class Hybrid extends SystemConfiguration {
   public Hybrid(int count, NoiseLevel nl, Classifier featureSelector) throws IOException {
      super(count, nl, featureSelector);
   }

   @Override
   public void execute(String folderPath) throws IOException, Exception {
      new SystemTrain(
         folderPath + "NormalOrAttack/",
         super.setupFiles,
         AttributeTypeConstants.ATTRIBUTE_CLASS,
         "tcpFlood:attack, udpFlood:attack, httpFlood:attack, slowBody:attack, slowHeaders:attack, slowRead:attack",
         super.featureSelector
      );

      new SystemTrain(
         folderPath + "HL/",
         super.getHL(),
         AttributeTypeConstants.ATTRIBUTE_CLASS,
         "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate",
         super.featureSelector
      );
   }
}
