package driver.systemConfiguration;

import constants.AttributeTypeConstants;
import driver.SystemTrain;
import java.io.IOException;

public class Single extends SystemConfiguration {
   public Single(NoiseLevel nl, int count) throws IOException {
      super(nl, count);
   }
   
   @Override
   public void execute(String folderPath) throws IOException, Exception{
      new SystemTrain(
         folderPath + "Single/",
         super.setupFiles,
         AttributeTypeConstants.ATTRIBUTE_CLASS,
         "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate"
      );
   }

}
