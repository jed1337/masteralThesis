package driver.systemConfiguration;

import constants.AttributeTypeConstants;
import driver.SystemTrain;
import java.io.IOException;

public class Hybrid extends SystemConfiguration {
   public Hybrid(NoiseLevel nl, int count) throws IOException {
      super(nl, count);
   }

   @Override
   public void execute(String folderPath) throws IOException, Exception {
      new SystemTrain(
         folderPath + "NormalOrAttack/",
         super.setupFiles,
         AttributeTypeConstants.ATTRIBUTE_CLASS,
         "tcpFlood:attack, udpFlood:attack, httpFlood:attack, slowBody:attack, slowHeaders:attack, slowRead:attack"
      );

      new SystemTrain(
         folderPath + "HL/",
         super.getHL(),
         AttributeTypeConstants.ATTRIBUTE_CLASS,
         "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate"
      );
   }
}
